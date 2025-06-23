package com.dsg.nexusmod.osgi;

import java.util.List;
import java.util.function.BiConsumer;

public interface OSGiFramework {
	
	 /**
     * Inicializa e inicia o framework OSGi.
     */
    void start();

    /**
     * Para o framework OSGi.
     */
    void stop();

    /**
     *  instalar um plugin  automaticamente.
     *
     * @param directoryPath Caminho do diretório.
     */
    String installBundle(String directoryPath, boolean started);
  
    /**
     * Registra os plugins que implementam ClassT
     * @param <T>
     * @param classPlugin
     * @param putter
     */
    void registerPlugin(Class classPlugin, BiConsumer putter );

    /**
     * Para um bundle específico.
     *
     * @param bundleId ID do bundle a ser parado.
     */
    void stopBundle(String bundleId);

    /**
     * Reinicia um bundle específico.
     *
     * @param bundleId ID do bundle a ser reiniciado.
     */
    void restartBundle(String bundleId);
    
    /**
     * deletar da pasta de plugins um bundle específico.
     * @param pluginId
     */
    void deleteBundle(String pluginId);

    /**
     * Desinstala um bundle específico.
     *
     * @param bundleId ID do bundle a ser desinstalado.
     */
    void uninstallBundle(String bundleId);
    
    /**
     * @return
     */
    List<Plugin> bundles();

	void loadPlugins();

	void copyInstallBundle(String canonicalPath);

//    /**
//     * Carrega todos os bundles de um diretório específico.
//     * @param directoryPath
//     */
//    void loadBundles(String directoryPath);

}
