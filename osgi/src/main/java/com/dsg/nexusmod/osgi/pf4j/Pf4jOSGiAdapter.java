package com.dsg.nexusmod.osgi.pf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionPoint;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;

import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.Plugin;

public class Pf4jOSGiAdapter implements OSGiFramework {

	private final PluginManager pluginManager;
	
	Map<Class<ExtensionPoint>, BiConsumer<ExtensionPoint, Plugin>> pluginPaths = new LinkedHashMap<>();
    
	public Pf4jOSGiAdapter() {
        // Inicializa o gerenciador de plugins do PF4J
        this.pluginManager = new DefaultPluginManager();
    }

    @Override
    public void start() {
        // Carrega e inicia todos os plugins
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        System.out.println("PF4J Framework iniciado. Todos os plugins foram carregados e iniciados.");
    }

    @Override
    public void stop() {
        // Para todos os plugins
        pluginManager.stopPlugins();
        System.out.println("PF4J Framework parado. Todos os plugins foram desativados.");
    }
    
//    public void loadBundles(String directoryPath) {
//    
//    	try {
//    		File dir = new File(directoryPath);
//    		if(dir.exists()) {
//    			dir.mkdirs();
//    		}
//    		PLUGIN_DIRECTORY = dir.getCanonicalPath();
//    		
//    		new PluginLoader(this).startMonitoring(PLUGIN_DIRECTORY);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

    @Override
    public void installBundle(String directoryPath) {
        // Define o diretório de onde os plugins serão carregados
    	// Caminho para o arquivo JAR do plugin
        Path pluginPath = Paths.get(directoryPath);

        // Carregar o plugin
        String pluginId = pluginManager.loadPlugin(pluginPath); 

        if (pluginId != null) {
            // Inicializar o plugin
            pluginManager.startPlugin(pluginId);
            System.out.println("Plugin " + pluginId + " carregado e iniciado com sucesso!");
        } else {
            System.out.println("Falha ao carregar o plugin.");
        }

//        // Opcional: listar plugins carregados
        pluginManager.getPlugins().forEach(plugin -> {
        	if(plugin!=null)
        		System.out.println("Plugin carregado: " + plugin.getPluginId());
        });
        
        var e = pluginManager.getPlugin(pluginId);
        var plugin = new Plugin(e.getPluginId(), e.getDescriptor().getVersion() , e.getPluginState().name(), e.getDescriptor().getPluginDescription());
        
        pluginPaths.forEach((classPlugin, putter) -> {
        	System.out.println("Registrando plugin: " + classPlugin.getName());
			List<ExtensionPoint> listeners = pluginManager.getExtensions(classPlugin, pluginId);
			
			for (ExtensionPoint listener : listeners) {
				putter.accept(listener, plugin);
			}
		});
       
    }

    @Override
    public void stopBundle(String pluginId) {
    	System.out.println("Plugin " + pluginId + " parando.");
        PluginState state = pluginManager.stopPlugin(pluginId);
        if (state == PluginState.STOPPED) {
            System.out.println("Plugin " + pluginId + " parado com sucesso.");
        } else {
            System.out.println("Falha ao parar o plugin " + pluginId);
        }
        pluginManager.getPlugins()
				.forEach(plugin -> System.out.println("Plugin atual: " + plugin.getPluginId() + " - Estado: " + plugin.getPluginState()));
    }

    @Override
    public void restartBundle(String pluginId) {
        PluginState state = pluginManager.stopPlugin(pluginId);
        System.out.println("Plugin " + pluginId + " iniciando.");
        if (state != PluginState.STARTED && state != PluginState.DISABLED) {
            pluginManager.startPlugin(pluginId);
            
            pluginManager.loadPlugins();
            System.out.println("Plugin " + pluginId + " reiniciado com sucesso.");
        } else {
            System.out.println("Falha ao reiniciar o plugin " + pluginId);
        }
    }

    @Override
    public void uninstallBundle(String pluginId) {
        boolean success = pluginManager.unloadPlugin(pluginId);
        if (success) {
            System.out.println("Plugin " + pluginId + " desinstalado com sucesso.");
        } else {
            System.out.println("Falha ao desinstalar o plugin " + pluginId);
        }
    }

	@Override
	public <T,P> void registerPlugin(Class<T> classPlugin, BiConsumer<T, P> putter ) {
		pluginPaths.put((Class<ExtensionPoint>)classPlugin, (BiConsumer<ExtensionPoint, Plugin>)putter);
//		List<T> listeners = pluginManager.getExtensions(classPlugin);
//        for (T listener : listeners) {
//            putter.accept(listener);
//        }
		
	}

	@Override
	public List<Plugin> bundles() {
		// TODO Auto-generated method stub
		return pluginManager.getPlugins()
				.stream()
				.map(e -> new Plugin(e.getPluginId(), e.getDescriptor().getVersion() , e.getPluginState().name(), e.getDescriptor().getPluginDescription()) )
				.toList();
	}
}