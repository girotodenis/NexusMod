package com.dsg.nexusmod.configuracao;


import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.ui.ControllerTeste;
import com.dsg.nexusmod.controller.MenuPlugin;
import com.dsg.nexusmod.plugin.MessageListener;
import com.dsg.nexusmod.ui.ItemMenu;

public class ConfiguacaoActivator extends Plugin {

    public ConfiguacaoActivator(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class MessageListenerImpl implements MessageListener {

    	@Override
    	public void onMessage(String senderPluginId, String message) {
    		System.out.println(String.format("Received message from plugin %s: %s",  senderPluginId, message) );
    	}

    }
    
    
    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(MenuPlugin menu) {
			menu.addMenuItem("Configuração", UIManager.getIcon("FileView.directoryIcon"), new ControllerTeste());
		}


    }
    
   
   

}



