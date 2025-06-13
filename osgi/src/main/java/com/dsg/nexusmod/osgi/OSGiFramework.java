package com.dsg.nexusmod.osgi;

import java.util.function.Consumer;

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
    void installBundle(String directoryPath);
  
    /**
     * Registra os plugins que implementam ClassT
     * @param <T>
     * @param classPlugin
     * @param putter
     */
    <T> void registerPlugin(Class<T> classPlugin, Consumer<T> putter );

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
     * Desinstala um bundle específico.
     *
     * @param bundleId ID do bundle a ser desinstalado.
     */
    void uninstallBundle(String bundleId);

}
