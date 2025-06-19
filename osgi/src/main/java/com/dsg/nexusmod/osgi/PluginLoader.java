package com.dsg.nexusmod.osgi;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.dsg.nexusmod.osgi.OSGiFramework;

public class PluginLoader {

	private final OSGiFramework pluginManager;
	private final Set<String> loadedPlugins = new HashSet<>(); // Para rastrear quais plugins já foram carregados

	public PluginLoader(OSGiFramework pluginManager) {
		this.pluginManager = pluginManager;
	}

	public void startMonitoring(String directoryPath, Consumer<String> consumer) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			//System.out.println("Verificando novos plugins na pasta: " + directoryPath);
			File pluginsDir = new File(directoryPath);

			if (pluginsDir.exists() && pluginsDir.isDirectory()) {
				File[] files = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));

				if (files != null) {
					for (File file : files) {
						String canonicalPath;
						try {
							canonicalPath = file.getCanonicalPath();
						} catch (Exception e) {
							System.err.println("Erro ao obter o caminho do arquivo: " + file.getName());
							e.printStackTrace();
							continue;
						}

						// Verifica se este plugin já foi carregado
						if (!loadedPlugins.contains(canonicalPath)) {
							System.out.println("Novo plugin encontrado: " + file.getName());
							pluginManager.installBundle(canonicalPath);
							loadedPlugins.add(canonicalPath); // Marca o plugin como carregado
							consumer.accept(canonicalPath); // Notifica o consumidor, se fornecido
						}
					}
				}
			} else {
				System.out.println("Diretório de plugins não encontrado: " + directoryPath);
			}
		};

		// Agendar a execução do monitoramento a cada 10 segundos
		scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
	}

	public void startMonitoring(String pLUGIN_DIRECTORY, Object object) {
		// TODO Auto-generated method stub
		
	}
}
