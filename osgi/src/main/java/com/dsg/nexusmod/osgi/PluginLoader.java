package com.dsg.nexusmod.osgi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PluginLoader {

	private final OSGiFramework pluginManager;
	private final Map<String, String> loadedPlugins = new LinkedHashMap<>(); // Para rastrear quais plugins já foram carregados
	private static boolean started = true;
	public PluginLoader(OSGiFramework pluginManager) {
		this.pluginManager = pluginManager;
	}

	public void startMonitoring(String directoryPath, Consumer<String> consumer) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			File pluginsDir = new File(directoryPath);

			if (pluginsDir.exists() && pluginsDir.isDirectory()) {
				
				try {
					System.setProperty("app.home.plugins", pluginsDir.getCanonicalPath());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				File[] files = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));
				Set<String> findJar = new HashSet<String>();
				
				if (files != null) {
					for (File file : files) {
						String canonicalPath;
						try {
							canonicalPath = file.getCanonicalPath();
							findJar.add(canonicalPath);
						} catch (Exception e) {
							System.err.println("Erro ao obter o caminho do arquivo: " + file.getName());
							e.printStackTrace();
							continue;
						}

						// Verifica se este plugin já foi carregado
						if (!loadedPlugins.keySet().contains(canonicalPath)) {
							System.out.println("inatall"+canonicalPath);
							var id = pluginManager.installBundle(canonicalPath , started);
							loadedPlugins.put(canonicalPath, id); // Marca o plugin como carregado
							consumer.accept(canonicalPath); // Notifica o consumidor, se fornecido
						}
					}
				}
				
				removePlugins(findJar);
				
			} 
			
			started = false;
		};

		// Agendar a execução do monitoramento a cada 10 segundos
		scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
	}

	private void removePlugins(Set<String> findJar) {
		List<String> rm = new ArrayList<String>();
		loadedPlugins.forEach((path, pluginId) -> {
			if(!findJar.contains(path)) {
				if(new File(path).isFile()) {
					System.out.println("remover "+pluginId);
					pluginManager.uninstallBundle(pluginId);
				}
				rm.add(path);
			}
		});
		rm.forEach(p->{
			System.out.println("remover "+p);
			System.out.println(loadedPlugins.remove(p));
		});
	}

}
