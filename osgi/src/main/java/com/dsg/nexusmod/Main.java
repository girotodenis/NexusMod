package com.dsg.nexusmod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.UIManager;

import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.ui.AppUtilities;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
	
	public static void main(String[] args) {
		
		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();
		
		OsgiCore osgiCore = new OsgiCore(osgiFramework);

		var app = AppUtilities.builder()
				.lookAndFeel(FlatDarculaLaf.class)
				.size(1024, 768)
				.title("Teste OSGi")
				.build().getMain();

		
		// Iniciar o framework OSGi
		osgiCore.loadBundles("../plugins");
		
		osgiCore.registerPlugin(com.dsg.nexusmod.osgi.OsgiPlugin.class, (item) -> item.load(osgiCore) );
		osgiCore.registerPlugin(com.dsg.nexusmod.ui.ItemMenu.class, (item) -> item.addItemMenu(app) );
        
		
		// Configurar o diretório de bundles
		try {
			File plugin = new File("../configurar/target/configurar-0.0.1-SNAPSHOT.jar");
			System.out.println(plugin.isFile());
			osgiCore.installBundle(plugin.getCanonicalPath());
			
			File pluginteste = new File("../plugin-teste/target/plugin-teste-0.0.1-SNAPSHOT.jar");
			System.out.println(pluginteste.isFile());
			osgiCore.installBundle(pluginteste.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		app.getPanel().addMenuItem("Sair", UIManager.getIcon("FileView.directoryIcon"), (item) ->{ osgiCore.stop();
		System.exit(0);});
		app.getPanel().loadMenu();
		
		
	}

}
