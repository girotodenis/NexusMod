package com.dsg.nexusmod.osgi.pf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.jar.JarFile;

import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionPoint;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;

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
    }

    @Override
    public void stop() {
        // Para todos os plugins
        pluginManager.stopPlugins();
    }
    
    @Override
    public String installBundle(String directoryPath, boolean started) {
        // Define o diretório de onde os plugins serão carregados
    	// Caminho para o arquivo JAR do plugin
			
    		Path pluginPath = Paths.get(directoryPath);
    		
    		System.out.println("recebendo: "+directoryPath);
    		// Carregar o plugin
			Properties pluginProperties = getPluginProperties(pluginPath);
			String newPluginId = pluginProperties.getProperty("plugin.id");
            String newPluginVersion = pluginProperties.getProperty("plugin.version");
            
            var existingPlugin =  pluginManager.getPlugin(newPluginId);
            if (existingPlugin != null) {
                PluginDescriptor existingDescriptor = existingPlugin.getDescriptor();
                System.out.println("Plugin Existente ID: " + existingDescriptor.getPluginId());
                System.out.println("Versão do Plugin Existente: " + existingDescriptor.getVersion());
                pluginManager.deletePlugin(newPluginId);

                // Compare as versões
//                if (newPluginVersion.compareTo(existingDescriptor.getVersion()) > 0) {
//                    System.out.println("A nova versão é mais recente. Atualizando...");
//                    // Desative e desinstale o plugin existente
//                    //pluginManager.disablePlugin(existingDescriptor.getPluginId());
////                    pluginManager.unloadPlugin(existingDescriptor.getPluginId());
//                    pluginManager.deletePlugin(newPluginId);
//                    // Carregue e ative o novo plugin
//                    //String loadedPluginId = pluginManager.loadPlugin(pluginPath);
//                   // pluginManager.enablePlugin(loadedPluginId);
//                    System.out.println("Plugin atualizado com sucesso!");
//                } else {
//                    System.out.println("O plugin existente já está atualizado ou é mais recente.");
//                }
            } 
                
    		
    		String pluginId = pluginManager.loadPlugin(pluginPath); 
    		var plugin =  pluginManager.getPlugin(pluginId);
    		if (pluginId != null) {
    			// Inicializar o plugin
    			if(started) {
    				pluginManager.startPlugin(pluginId);
    				var pluginStarted = pluginManager.getPlugin(pluginId);
    				System.out.println(String.format("plugin %s started!", pluginStarted.getPluginId()));
    			}
    			loadgetExtensions(pluginId, plugin);
    		} else {
    			System.err.println("Falha ao carregar o plugin.");
    		}
    		
    		return plugin.getPluginId();
    }

	private void loadgetExtensions(String pluginId, PluginWrapper plugin) {
		pluginPaths.forEach((classPlugin, putter) -> {
			var pluginDTO = new Plugin(plugin.getPluginId(), plugin.getDescriptor().getVersion() , plugin.getPluginState().name(), plugin.getDescriptor().getPluginDescription());
			putter.accept(null, pluginDTO);
			
			List<ExtensionPoint> listeners = pluginManager.getExtensions(classPlugin, pluginId);
			for (ExtensionPoint listener : listeners) {
				putter.accept(listener, pluginDTO);
			}
		});
	}

    @Override
    public void stopBundle(String pluginId) {
    	
        PluginState state = pluginManager.stopPlugin(pluginId);
        if (state != PluginState.STOPPED) {
            System.err.println("Falha ao parar o plugin " + pluginId);
        }
    }
    
    @Override
    public void deleteBundle(String pluginId) {
    	var plugin = pluginManager.getPlugin(pluginId);
        System.out.println(pluginId+": "+plugin.getPluginPath());
		try {
			Files.deleteIfExists(plugin.getPluginPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void restartBundle(String pluginId) {
    	
    	var plugin = pluginManager.getPlugin(pluginId);
    	PluginState state = plugin.getPluginState();
    	
    	if(state.equals(PluginState.STARTED)) {
    		pluginManager.stopPlugin(pluginId);
    	}
    	if(state.equals(PluginState.STOPPED) || state.equals(PluginState.RESOLVED)) {
    		pluginManager.startPlugin(pluginId);
    		var pluginStart = pluginManager.getPlugin(pluginId);
    		loadgetExtensions(pluginId, pluginStart);
    	}
    }

    @Override
    public void uninstallBundle(String pluginId) {
    	
        boolean success = false;
        
        var plugin = pluginManager.getPlugin(pluginId);
        
        System.out.println(pluginId+": "+plugin.getPluginState());
        
        System.out.println(
        		"uninstallBundle deletePlugin "+
        				(success = pluginManager.deletePlugin(pluginId))
        		)	;
        System.out.println(
        		"uninstallBundle unloadPlugin "+
        				(success = pluginManager.unloadPlugin(pluginId))
        		)	;

        
        if (!success) {
            System.err.println("Falha ao desinstalar o plugin " + pluginId);
        }
    }

	@Override
	public void registerPlugin(Class classPlugin, BiConsumer putter ) {
		pluginPaths.put((Class<ExtensionPoint>)classPlugin, (BiConsumer<ExtensionPoint, Plugin>) putter );
	}

	@Override
	public List<Plugin> bundles() {
		// TODO Auto-generated method stub
		return pluginManager.getPlugins()
				.stream()
				.map(e -> new Plugin(e.getPluginId(), e.getDescriptor().getVersion() , e.getPluginState().name(), e.getDescriptor().getPluginDescription()) )
				.toList();
	}

	@Override
	public void loadPlugins() {
		this.pluginManager.loadPlugins();
	}

	@Override
	public void copyInstallBundle(String canonicalPath) {
		Path sourceFile = Path.of(canonicalPath);
		Path destinationDir = Path.of(System.getProperty("app.home.plugins"));
        try {
        	// Certifique-se de que o diretório de destino existe
            File dir = destinationDir.toFile();
            if (!dir.exists()) {
                dir.mkdirs(); // Cria o diretório e subdiretórios se eles não existirem
            }

            // Caminho do arquivo dentro do diretório de destino
            Path destinationFile = destinationDir.resolve(sourceFile.getFileName());

            
            // Copiar o arquivo
            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo copiado com sucesso para: " + destinationFile);
        } catch (IOException e) {
            System.err.println("Erro ao copiar o arquivo: " + e.getMessage());
        }
		
	}
	
	public static Properties getPluginProperties(Path pluginPath)  {
		try {
			Properties properties = new Properties();
			// Abra o JAR e leia o arquivo "plugin.properties"
			try (JarFile jarFile = new JarFile(pluginPath.toFile())) {
				InputStream inputStream = jarFile.getInputStream(jarFile.getEntry("plugin.properties"));
				properties.load(inputStream);
			}
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("não é um plugin");
		}
    }
}