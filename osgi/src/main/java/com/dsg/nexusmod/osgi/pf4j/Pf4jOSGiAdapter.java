package com.dsg.nexusmod.osgi.pf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;

import com.dsg.nexusmod.osgi.OSGiFramework;

public class Pf4jOSGiAdapter implements OSGiFramework {

	private final PluginManager pluginManager;

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

        // Opcional: listar plugins carregados
        pluginManager.getPlugins().forEach(plugin -> {
            System.out.println("Plugin carregado: " + plugin.getPluginId());
        });
    }

    @Override
    public void stopBundle(String pluginId) {
        PluginState state = pluginManager.stopPlugin(pluginId);
        if (state == PluginState.STOPPED) {
            System.out.println("Plugin " + pluginId + " parado com sucesso.");
        } else {
            System.out.println("Falha ao parar o plugin " + pluginId);
        }
    }

    @Override
    public void restartBundle(String pluginId) {
        PluginState state = pluginManager.stopPlugin(pluginId);
        if (state == PluginState.STOPPED) {
            pluginManager.startPlugin(pluginId);
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
	public <T> void registerPlugin(Class<T> classPlugin, Consumer<T> putter ) {
		List<T> listeners = pluginManager.getExtensions(classPlugin);
        for (T listener : listeners) {
            putter.accept(listener);
        }
		
	}
}