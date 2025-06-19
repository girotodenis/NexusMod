package com.dsg.nexusmod.configuracao;


import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.controller.ConfiguracaoController;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.ItemMenu;
import com.dsg.nexusmod.ui.MenuPlugin;
import com.dsg.nexusmod.ui.OnInit;

public class ConfiguacaoActivator extends org.pf4j.Plugin {

	private static OSGiFramework osgiService;
	private static ConfiguracaoController configuracaoController;
	
    public ConfiguacaoActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	System.out.println("start "+this.getClass().getName());
    	if (configuracaoController != null && configuracaoController instanceof OnInit) {
    		configuracaoController.start();
		}
    }

    public void stop() {
    	System.out.println("stop "+this.getClass().getName());
    	if (configuracaoController != null) {
			configuracaoController.stop();
		}
    }

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(MenuPlugin menu, Plugin plugin) {
			
			if(configuracaoController == null) {
				configuracaoController = new ConfiguracaoController(osgiService);
			}
			
			System.out.println("addItemMenu "+this.getClass().getName());
			javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/imagem/setting-configure.png"));
	        icon = new javax.swing.ImageIcon(icon.getImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH));
			menu.addMenuItem("Configuração", icon, configuracaoController);
			
		}
    }
    
    @Extension
    public static class OsgiPluginImpl implements OsgiPlugin {
		@Override
		public void load(OSGiFramework osgi) {
			osgiService = osgi;
			configuracaoController = new ConfiguracaoController(osgiService);
		}
    }
   

}



