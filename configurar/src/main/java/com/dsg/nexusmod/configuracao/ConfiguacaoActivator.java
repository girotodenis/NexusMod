package com.dsg.nexusmod.configuracao;


import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.ui.ControllerTeste;
import com.dsg.nexusmod.plugin.ItemMenu;
import com.dsg.nexusmod.ui.MenuPlugin;

public class ConfiguacaoActivator extends Plugin {

	public static String PLUGIN_MENU_ID;
	public static ControllerTeste controller = new ControllerTeste();
	
    public ConfiguacaoActivator(PluginWrapper wrapper) {
        super(wrapper);
    }

    // Executado quando o plugin é iniciado
    @Override
    public void start() {
        System.out.println("Plugin " + getWrapper().getPluginId() + " está sendo iniciado!");
    }

    // Executado quando o plugin é parado
    @Override
    public void stop() {
        System.out.println("Plugin " + getWrapper().getPluginId() + " está sendo parado!");
        controller.stop(PLUGIN_MENU_ID);
    }
    
    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(MenuPlugin menu) {
			PLUGIN_MENU_ID = menu.addMenuItem("Configuração", UIManager.getIcon("FileView.directoryIcon"), controller );
		}


    }
    
   
   

}



