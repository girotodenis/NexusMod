package com.dsg.nexusmod.configuracao;


import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.ui.ControllerTeste;
import com.dsg.nexusmod.controller.MenuPlugin;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.ui.ItemMenu;

public class ConfiguacaoActivator extends Plugin {

	private static OSGiFramework osgiService;
	
    public ConfiguacaoActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	System.out.println("start "+this.getClass().getName());
    }

    public void stop() {
    	System.out.println("stop "+this.getClass().getName());
    }

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(MenuPlugin menu) {
			System.out.println("addItemMenu "+this.getClass().getName());
			menu.addMenuItem("Configuração", UIManager.getIcon("FileView.directoryIcon"), new ControllerTeste(osgiService));
		}
    }
    
    @Extension
    public static class OsgiPluginImpl implements OsgiPlugin {
		@Override
		public void load(OSGiFramework osgi) {
			osgiService = osgi;
		}
    }
   

}



