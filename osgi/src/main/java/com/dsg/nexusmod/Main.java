package com.dsg.nexusmod;

import com.dsg.nexusmod.events.EventsBundleActivator;
import com.dsg.nexusmod.log.LogBundleActivator;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.felix.FelixOSGiAdapter;

public class Main {
	
	public static void main(String[] args) {
		
		OSGiFramework osgiFramework = new FelixOSGiAdapter();
		
		OsgiCore osgiCore = new OsgiCore(osgiFramework);

        // Iniciar o framework OSGi

        // Configurar o diretório de bundles
		osgiCore.setBundleDirectory("../bundles/");

        // Listar bundles instalados
		//osgiCore.listBundles();

        // Instalar um bundle manualmente (exemplo)
//		osgiCore.installBundle("file:example-bundle.jar");
		
		 // Instala o bundle usando a classe do LogBundleActivator
		
//		osgiCore.installBundle(new LogBundleActivator());
//		osgiCore.installBundle(new EventsBundleActivator());
		osgiCore.start();
		
		osgiCore.stop();
		
		
	}

}
