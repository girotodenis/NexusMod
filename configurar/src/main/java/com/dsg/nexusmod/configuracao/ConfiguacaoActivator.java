package com.dsg.nexusmod.configuracao;


import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.ui.ConfigController;
import com.dsg.nexusmod.configuracao.ui.HomeController;
import com.dsg.nexusmod.plugin.MessageListener;
import com.dsg.nexusmod.ui.ItemMenu;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;

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
		public void addItemMenu(StartLeGui app) {
			app.addItemMenu(
					new EventAdicionarItemMenu(
							"Configuração",
							"imagens/setting-configure.png", 
							"imagens/setting-configure-px.png",
							false,
							(controllerPai)->new ConfigController(controllerPai),
							false
							)
			);
		}

    }
    
    @Extension
    public static class ItemMenu2Impl implements ItemMenu {
    	
    	@Override
    	public void addItemMenu(StartLeGui app) {
    		app.addItemMenu(
    				new EventAdicionarItemMenu(
    						"Home",
    						"imagens/home_house_10811.png", 
    						null,
    						false,
    						(controllerPai)->new HomeController(controllerPai),
    						true
    						)
    				.addCOntrollerMenuSubMenuFlutuante(
    						"Sub Item 1", 
    						"imagens/setting-configure.png",
    						(controllerPai)->new ConfigController(controllerPai)
    						)
    				.addActionMenuSubMenuFlutuante(
    						"Sub Item 2", 
    						"imagens/icons8-exit-sign-64.png",
    						(c)->System.out.println("Sair")
    						)
    				);
    	}
    	
    }
    
   

}



