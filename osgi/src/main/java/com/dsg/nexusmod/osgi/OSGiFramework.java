package com.dsg.nexusmod.osgi;

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
     * Define um diretório onde o framework deve buscar JARs para instalar automaticamente.
     *
     * @param directoryPath Caminho do diretório.
     */
    void setBundleDirectory(String directoryPath);

    /**
     * Instala e inicia um bundle fornecendo a classe principal do bundle.
     *
     * @param bundleClassName Nome da classe principal do bundle.
     */
    void installBundle(Object bundleClassName);

//    /**
//     * Lista todos os bundles instalados no framework.
//     *
//     * @return Lista de bundles instalados.
//     */
//    List<Bundle> listBundles();

    /**
     * Para um bundle específico.
     *
     * @param bundleId ID do bundle a ser parado.
     */
    void stopBundle(long bundleId);

    /**
     * Reinicia um bundle específico.
     *
     * @param bundleId ID do bundle a ser reiniciado.
     */
    void restartBundle(long bundleId);

    /**
     * Desinstala um bundle específico.
     *
     * @param bundleId ID do bundle a ser desinstalado.
     */
    void uninstallBundle(long bundleId);

}
