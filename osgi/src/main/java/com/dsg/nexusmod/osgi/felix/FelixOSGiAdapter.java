package com.dsg.nexusmod.osgi.felix;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import com.dsg.nexusmod.osgi.OSGiFramework;

public class FelixOSGiAdapter implements OSGiFramework {

    private Framework framework;
    private Map<String, String> config = new HashMap<>();
    
    private BundleContext bundleContext;
    
    public FelixOSGiAdapter() {
		// Configurações iniciais do Apache Felix, se necessário
		config.put(FelixConstants.LOG_LEVEL_PROP, "1"); // Nível de log (1 = erro, 2 = aviso, 3 = info, 4 = debug)
		
		init();
	}
    
    private void init() {
    	try {
    		// Configuração do framework Apache Felix
    		FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
    		framework = frameworkFactory.newFramework(config);
    		framework.init();
    		bundleContext = framework.getBundleContext();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public FelixOSGiAdapter putConfig(String key, String value) {
    	config.put(key, value);
		return this;
	}

	@Override
    public void start() {

        // Inicializa o framework Felix
//        felix = new Felix(config);
//        try {
//            felix.start();
//            bundleContext = felix.getBundleContext();
//            System.out.println("Apache Felix iniciado com sucesso!");
//        } catch (Exception e) {
//            System.err.println("Erro ao iniciar o Apache Felix: " + e.getMessage());
//            e.printStackTrace();
//        }
		
		try {
			framework.start();
			System.out.println("Apache Felix iniciado!");
	        // Manter o programa rodando até que o usuário pressione Enter
	        System.out.println("Pressione Enter para sair...");
	        System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void stop() {
        try {
//            if (felix != null) {
//                felix.stop();
//                System.out.println("Apache Felix parado com sucesso!");
//            }
        	framework.stop();
            framework.waitForStop(0);
            
            listBundles().forEach(bundle -> {
				try {
					bundle.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
        } catch (Exception e) {
            System.err.println("Erro ao parar o Apache Felix: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setBundleDirectory(String directoryPath) {
        // Mantemos o método existente para carregar JARs de um diretório
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Diretório inválido: " + directoryPath);
            return;
        }

        File[] jarFiles = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null || jarFiles.length == 0) {
            System.out.println("Nenhum JAR encontrado no diretório: " + directoryPath);
            return;
        }

        for (File jar : jarFiles) {
            try (InputStream fis = new FileInputStream(jar)) {
                Bundle bundle = bundleContext.installBundle(jar.toURI().toString(), fis);
                bundle.start();
                System.out.println("Bundle instalado e iniciado: " + bundle.getSymbolicName());
            } catch (Exception e) {
                System.err.println("Erro ao instalar o bundle: " + jar.getName());
                e.printStackTrace();
            }
        }
    }

 
    
    public void installBundle(Object bundle) {
    	try {
    		if(bundle instanceof BundleActivator)
    			((BundleActivator)bundle).start(bundleContext);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
 

    public List<Bundle> listBundles() {
        List<Bundle> bundles = new ArrayList<>();
        for (Bundle bundle : bundleContext.getBundles()) {
            bundles.add(bundle);
            System.out.println("Bundle: " + bundle.getBundleId() + " - " + bundle.getSymbolicName());
        }
        return bundles;
    }

    public void stopBundle(long bundleId) {
        try {
            Bundle bundle = bundleContext.getBundle(bundleId);
            if (bundle != null) {
                bundle.stop();
                System.out.println("Bundle parado: " + bundle.getSymbolicName());
            } else {
                System.err.println("Bundle com ID " + bundleId + " não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao parar o bundle com ID " + bundleId);
            e.printStackTrace();
        }
    }

    public void restartBundle(long bundleId) {
        try {
            Bundle bundle = bundleContext.getBundle(bundleId);
            if (bundle != null) {
                bundle.stop();
                bundle.start();
                System.out.println("Bundle reiniciado: " + bundle.getSymbolicName());
            } else {
                System.err.println("Bundle com ID " + bundleId + " não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao reiniciar o bundle com ID " + bundleId);
            e.printStackTrace();
        }
    }

    public void uninstallBundle(long bundleId) {
        try {
            Bundle bundle = bundleContext.getBundle(bundleId);
            if (bundle != null) {
                bundle.uninstall();
                System.out.println("Bundle desinstalado: " + bundle.getSymbolicName());
            } else {
                System.err.println("Bundle com ID " + bundleId + " não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao desinstalar o bundle com ID " + bundleId);
            e.printStackTrace();
        }
    }
}