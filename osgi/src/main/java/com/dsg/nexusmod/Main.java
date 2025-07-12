package com.dsg.nexusmod;

import java.io.File;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.AbstractEventListener;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.database.DatabaseManager;
import com.dsg.nexusmod.database.DatabaseSession;
import com.dsg.nexusmod.model.Progress;
import com.dsg.nexusmod.osgi.CoreResourses;
import com.dsg.nexusmod.osgi.LoadPlugin;
import com.dsg.nexusmod.osgi.OsgiCore;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.osgi.PluginLoader;
import com.dsg.nexusmod.osgi.pf4j.Pf4jOSGiAdapter;
import com.dsg.nexusmod.ui.ControllerPlugin;
import com.dsg.nexusmod.ui.ItemMenu;
import com.dsg.ui.AppController;
import com.dsg.ui.AppUtilities;

public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	private static  DatabaseSession SESSION ;
	private static  String PLUGIN_DIRECTORY = "./plugins"; // Diretório padrão para plugins
	private static  String DB_DIRECTORY = "./db"; // Diretório padrão banco
	
	static AbstractEventListener<Void> loadMenuEvent;
	
	public static void main(String[] args) {
		OsgiCore osgiCore = new OsgiCore(new Pf4jOSGiAdapter());

		PLUGIN_DIRECTORY = "../dist/target/NexusMod-app/plugins";
		DB_DIRECTORY = "../dist/target/NexusMod-app/db";
		
		creatSessionDB();
		
		var app = AppUtilities.builder()
				//.lookAndFeel(FlatDarculaLaf.class)
				.size(1024, 768).title("Teste OSGi").build()
				.getMain();
		
		

		registerPluginOrder(osgiCore, app);
		
		app.getPanel().addMenuItem("Sair", UIManager.getIcon("FileView.directoryIcon"), (item) -> {
			try {
				osgiCore.stop();
			} catch (Exception e) {
				System.err.println("Erro ao parar o OSGi: " + e.getMessage());
			}finally {
				System.exit(0);
			}
		});
		
		loadPlugin(osgiCore, app);
		
	}

	private static void creatSessionDB() {
		try {
			File dir = new File(DB_DIRECTORY);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			DB_DIRECTORY = dir.getCanonicalPath();
			String dbURL = String.format("jdbc:sqlite:%s/%s", DB_DIRECTORY, "app.db");
			DatabaseManager.initialize(dbURL);
			SESSION = new DatabaseSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadPlugin(OsgiCore osgiCore, AppController app) {
		try {
    		File dir = new File(PLUGIN_DIRECTORY);
    		if(dir.exists()) {
    			dir.mkdirs();
    		}
    		PLUGIN_DIRECTORY = dir.getCanonicalPath();
    		new PluginLoader(osgiCore).startMonitoring(PLUGIN_DIRECTORY, (pathJar, started)->{
    			log.trace("loadMenu started: {}", started);
				app.getPanel().loadMenu();
    		});
    		
    		try {
    			Thread.sleep(50);
    			app.fireEvent(new Progress(0,"Bem vindo! ") );
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void registerPluginOrder(OsgiCore osgiCore, AppController app) {
		
		loadMenuEvent = (Void)->{
			app.getPanel().loadMenu();
		};
		
		app.registerEvent("app.loadMenu",loadMenuEvent);
		
		osgiCore.registerPlugin(LoadPlugin.class, (item, plugin) ->  {
			Plugin pluginDTO = (Plugin) plugin;
			if(!"STARTED".equals(pluginDTO.getState())) {
				app.fireEvent("ConfiguracaoController.notificacao", pluginDTO.getPluginId());
			}
		});
		
		osgiCore.registerPlugin(OsgiPlugin.class, (extensionPoint, plugin) -> { 
			if(extensionPoint!= null) {
				((OsgiPlugin)extensionPoint).load(new CoreResourses(SESSION, osgiCore, (Plugin)plugin) );
			}
		});
		
		osgiCore.registerPlugin(ItemMenu.class, (extensionPoint, plugin) -> {
			if(extensionPoint!= null) {
				((ItemMenu)extensionPoint).addItemMenu(app);
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
