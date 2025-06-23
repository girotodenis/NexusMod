package com.dsg.nexusmod.configuracao;


import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.controller.ConfiguracaoController;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.ItemMenu;
import com.dsg.nexusmod.ui.OnInit;

public class ConfiguacaoActivator extends org.pf4j.Plugin {

	private static OSGiFramework osgiService;
	private static ConfiguracaoController configuracaoController;
	
    public ConfiguacaoActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	if (configuracaoController != null && configuracaoController instanceof OnInit) {
    		configuracaoController.start();
		}
    }

    public void stop() {
    	if (configuracaoController != null) {
			configuracaoController.stop();
		}
    }

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(ControllerRoot menu, Plugin plugin) {
			
			if(configuracaoController == null) {
				configuracaoController = new ConfiguracaoController(osgiService);
			}
			
			javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/imagem/setting-configure.png"));
	        icon = new javax.swing.ImageIcon(icon.getImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH));
			
	        menu.addMenuItem(MenuItem.builder()
							.text("Configuração")
							.icon(icon)
							.controller(configuracaoController)
					.build() );
			
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



