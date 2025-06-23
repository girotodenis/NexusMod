package com.dsg.nexusmod;

import java.io.File;

import javax.swing.UIManager;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.osgi.LoadPlugin;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.osgi.PluginLoader;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.nexusmod.ui.ControllerPlugin;
import com.dsg.nexusmod.ui.ItemMenu;
import com.dsg.ui.AppController;
import com.dsg.ui.AppUtilities;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {

	private static  String PLUGIN_DIRECTORY = "./plugins"; // Diretório padrão para plugins
	
	public static void main(String[] args) {

		OSGiFramework osgiFramework = new Pf4jOSGiAdapter();

		OsgiCore osgiCore = new OsgiCore(osgiFramework);

		var app = AppUtilities.builder().lookAndFeel(FlatDarculaLaf.class).size(1024, 768).title("Teste OSGi").build()
				.getMain();

		PLUGIN_DIRECTORY = "../dist/target/NexusMod-app/plugins";
		
		
		registerPluginOrder(osgiCore, app);
		
		app.getPanel().addMenuItem("Sair", UIManager.getIcon("FileView.directoryIcon"), (item) -> {
			osgiCore.stop();
			System.exit(0);
		});
		
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
		osgiCore.registerPlugin(LoadPlugin.class, (item, plugin) ->  {
			Plugin pluginDTO = (Plugin) plugin;
			if(!"STARTED".equals(pluginDTO.getState())) {
				app.fireEvent("ConfiguracaoController.notificacao", pluginDTO.getPluginId());
			}
		});
		osgiCore.registerPlugin(OsgiPlugin.class, (extensionPoint, plugin) -> { 
			if(extensionPoint!= null) {
				((OsgiPlugin)extensionPoint).load(osgiCore);
			}
		});
		osgiCore.registerPlugin(ItemMenu.class, (extensionPoint, plugin) -> {
			if(extensionPoint!= null) {
				Plugin pluginDTO = (Plugin) plugin;
				((ItemMenu)extensionPoint).addItemMenu(app, pluginDTO);
			}
		});
		osgiCore.registerPlugin(ControllerPlugin.class, (controllerPlugin, plugin) -> {
			if(controllerPlugin!= null) {
				Plugin pluginDTO = (Plugin) plugin;
				Controller newController = ((ControllerPlugin)controllerPlugin).newController(app, pluginDTO);
				app.addController(newController);
			}
		});
	}

}
