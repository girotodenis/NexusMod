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
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginLoader {
	
	private static final Logger log = LoggerFactory.getLogger(PluginLoader.class);
	
	private final OSGiFramework pluginManager;
	private final Map<String, String> loadedPlugins = new LinkedHashMap<>(); // Para rastrear quais plugins já foram carregados
	String atualizacao = null;
	public PluginLoader(OSGiFramework pluginManager) {
		this.pluginManager = pluginManager;
	}

	public void startMonitoring(String directoryPath, BiConsumer<String,Boolean> consumer) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
		findJars(directoryPath, true, null);
		consumer.accept(System.getProperty("app.home.plugins"), true);
		
		Runnable task = () -> {
			findJars(directoryPath, false, consumer);
		};

		// Agendar a execução do monitoramento a cada 2 segundos
		scheduler.scheduleAtFixedRate(task, 5, 2, TimeUnit.SECONDS);
	}

	private void findJars(String directoryPath, Boolean started, BiConsumer<String, Boolean> consumer) {
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
						log.error("Erro ao obter o caminho do arquivo: {}" , file.getName());
						e.printStackTrace();
						continue;
					}

					// Verifica se este plugin já foi carregado
					if (!loadedPlugins.keySet().contains(canonicalPath)) {
						log.info("install: {}", canonicalPath);
						var id = pluginManager.installBundle(canonicalPath , started);
						
						
						loadedPlugins.forEach((k,v)->{
							if(v.equals(id)) {
								atualizacao = k;
							}
						});
						
						if(atualizacao!=null) {
							loadedPlugins.remove(atualizacao);
							atualizacao = null;
						}
						
						loadedPlugins.put(canonicalPath, id); // Marca o plugin como carregado
						if(consumer!=null)
							consumer.accept(canonicalPath, started); // Notifica o consumidor, se fornecido
					}
				}
			}
			//System.out.println("removePlugins? "+loadedPlugins.size());
			removePlugins(findJar,consumer);
			
		} 
	}

	private void removePlugins(Set<String> findJar,BiConsumer<String,Boolean> consumer) {
		List<String> rm = new ArrayList<String>();
		loadedPlugins.forEach((path, pluginId) -> {
			if(!findJar.contains(path)) {
				if(new File(path).isFile()) {
				}
				rm.add(path);
				log.info("remover: {}", pluginId);
				pluginManager.uninstallBundle(pluginId);
				consumer.accept(System.getProperty("app.home.plugins"), false);
			}
		});
		rm.forEach(p->{
			log.info("remover: {}", p);
			log.info(loadedPlugins.remove(p));
		});
		if(rm.size()>0) {
			loadedPlugins.keySet().forEach( p ->log.info("mantidos: {}", p));
		}
	}

}
