package com.dsg.nexusmod;

import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.osgi.PluginLoader;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.ui.AppController;
import com.dsg.ui.AppUtilities;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {

	private static  String PLUGIN_DIRECTORY = "../plugins"; // Diretório padrão para plugins
	
	public static void main(String[] args) {

		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();

		OsgiCore osgiCore = new OsgiCore(osgiFramework);

		var app = AppUtilities.builder().lookAndFeel(FlatDarculaLaf.class).size(1024, 768).title("Teste OSGi").build()
				.getMain();

		
		registerPluginOrder(osgiCore, app);
		
		app.getPanel().addMenuItem("Sair", UIManager.getIcon("FileView.directoryIcon"), (item) -> {
			osgiCore.stop();
			System.exit(0);
		});
		
		// Configurar o diretório de bundles
		try {
			File plugin = new File("../configurar/target/configurar-0.0.1.jar");
			System.out.println(plugin.isFile());
			osgiCore.installBundle(plugin.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadPlugin(osgiCore, app);
		
		
		app.getPanel().loadMenu();

	}

	private static void loadPlugin(OsgiCore osgiCore, AppController app) {
		try {
    		File dir = new File(PLUGIN_DIRECTORY);
    		if(dir.exists()) {
    			dir.mkdirs();
    		}
    		PLUGIN_DIRECTORY = dir.getCanonicalPath();
    		new PluginLoader(osgiCore).startMonitoring(PLUGIN_DIRECTORY, (pathJar)->{
    			app.getPanel().loadMenu();
    		});
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void registerPluginOrder(OsgiCore osgiCore, AppController app) {
		osgiCore.registerPlugin(com.dsg.nexusmod.osgi.OsgiPlugin.class, (item, plugin) -> item.load(osgiCore));
		osgiCore.registerPlugin(com.dsg.nexusmod.ui.ItemMenu.class, (item, plugin) -> {
			item.addItemMenu(app, (Plugin) plugin);
			
		});
	}

}
