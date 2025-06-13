package com.dsg.nexusmod;

import java.io.File;

import com.dsg.nexusmod.nessagebus.MessageBus;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.nexusmod.plugin.MessageListener;

public class Main {
	
	public static void main(String[] args) {
		
		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();
		
		OsgiCore osgiCore = new OsgiCore(osgiFramework);

        // Iniciar o framework OSGi

        // Configurar o diretório de bundles
		File plugin = new File("../log-bundle/target/log-bundle-0.0.1-SNAPSHOT.jar");
		System.out.println(plugin.isFile());
		osgiCore.installBundle("/opt/dpf/git/girotodenis/NexusMod/log-bundle/target/log-bundle-0.0.1-SNAPSHOT.jar");
		
		MessageBus messageBus = new MessageBus();
		
		osgiCore.registerPlugin(MessageListener.class, (listener) -> messageBus.registerListener(listener) );
		
		messageBus.sendMessage("Main", "Olá, todos os plugins!");


		
		
		osgiCore.stop();
		
		
	}

}
