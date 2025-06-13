package com.dsg.nexusmod;

import java.io.File;

import com.dsg.nexusmod.nessagebus.MessageBus;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.nexusmod.plugin.MessageListener;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;

public class Main {
	
	public static void main(String[] args) {
		
		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();
		
		OsgiCore osgiCore = new OsgiCore(osgiFramework);

        // Iniciar o framework OSGi

        // Configurar o diretório de bundles
		File plugin = new File("../log-bundle/target/log-bundle-0.0.1-SNAPSHOT.jar");
		System.out.println(plugin.isFile());
		
		osgiCore.installBundle("/opt/dpf/git/girotodenis/NexusMod/log-bundle/target/log-bundle-0.0.1-SNAPSHOT.jar");
		osgiCore.installBundle("/opt/dpf/git/girotodenis/NexusMod/configurar/target/configurar-0.0.1-SNAPSHOT.jar");
		
		
		MessageBus messageBus = new MessageBus();
		
		osgiCore.registerPlugin(MessageListener.class, (listener) -> messageBus.registerListener(listener) );
		
		messageBus.sendMessage("Main", "Olá, todos os plugins!");

		
		var app = StartLeGui.get(800, 600, "App test")
				.abrirFecharMenuPadrao();
		
		
		osgiCore.registerPlugin(com.dsg.nexusmod.ui.ItemMenu.class, (item) -> app.addItemMenu(
				new EventAdicionarItemMenu(
						item.nome(),
						item.imageA(),
						item.imageB(),
						item.imageHorizontalAlignRIGHT(),
						item.desabilitarSelecaoMenu(),
						item.action()
				)
		) );
		
		app.addItemMenu(
				new EventAdicionarItemMenu(
						"Sair",
						"imagens/icons8-exit-sign-64.png",
						null,
						false,
						true,
						(c) -> c.setAppFinalizado(true) 
				)
		);
		
		app.start();
		
		osgiCore.stop();
		
		
	}

}
