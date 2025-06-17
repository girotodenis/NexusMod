package com.dsg.nexusmod;

import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.nexusmod.pf4j.OSGiFramework;
import com.dsg.ui.AppUtilities;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
	
	public static void main(String[] args) {
		
		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();
		
		OsgiCore osgiCore = new OsgiCore(osgiFramework);

        // Iniciar o framework OSGi

        // Configurar o diretório de bundles
		try {
			File plugin = new File("../configurar/target/configurar-0.0.1-SNAPSHOT.jar");
			System.out.println(plugin.isFile());
			osgiCore.installBundle(plugin.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		var app = AppUtilities.builder()
				.lookAndFeel(FlatDarculaLaf.class)
				.size(1024, 768)
				.title("Teste OSGi")
				.build().getMain();
		
		
		osgiCore.registerPlugin(com.dsg.nexusmod.plugin.ItemMenu.class, (item) -> item.addItemMenu(app) );
		
		app.getPanel().addMenuItem("Sair", UIManager.getIcon("FileView.directoryIcon"), (item) -> System.exit(0));
		app.getPanel().loadMenu();
		osgiCore.stop();
		
		
	}

}
