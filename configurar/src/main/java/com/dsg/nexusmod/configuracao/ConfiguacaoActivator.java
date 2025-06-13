package com.dsg.nexusmod.configuracao;


import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.configuracao.ui.ConfigController;
import com.dsg.nexusmod.configuracao.ui.HomeController;
import com.dsg.nexusmod.plugin.MessageListener;
import com.dsg.nexusmod.ui.ItemMenu;

import br.com.dsg.legui.controller.ActionMenu;
import br.com.dsg.legui.controller.LeGuiController;

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
		public String nome() {
			// TODO Auto-generated method stub
			return "Configuração";
		}

		@Override
		public String imageA() {
			// TODO Auto-generated method stub
			return "imagens/setting-configure.png";
		}

		@Override
		public String imageB() {
			return null;
		}

		@Override
		public boolean imageHorizontalAlignRIGHT() {
			return false;
		}

		@Override
		public boolean desabilitarSelecaoMenu() {
			return false;
		}

		@Override
		public ActionMenu<LeGuiController> action() {
			// TODO Auto-generated method stub
			return (controllerPai)->new ConfigController(controllerPai);
		}

		@Override
		public boolean configSubMenuButtonMouse2() {
			return false;
		}

    }
    
    @Extension
    public static class ItemMenu2Impl implements ItemMenu {
    	
    	@Override
    	public String nome() {
    		// TODO Auto-generated method stub
    		return "Configuração2";
    	}
    	
    	@Override
    	public String imageA() {
    		// TODO Auto-generated method stub
    		return "imagens/setting-configure.png";
    	}
    	
    	@Override
    	public String imageB() {
    		return null;
    	}
    	
    	@Override
    	public boolean imageHorizontalAlignRIGHT() {
    		return false;
    	}
    	
    	@Override
    	public boolean desabilitarSelecaoMenu() {
    		return false;
    	}
    	
    	@Override
    	public ActionMenu<LeGuiController> action() {
    		// TODO Auto-generated method stub
    		return (controllerPai)->new HomeController(controllerPai);
    	}
    	
    	@Override
    	public boolean configSubMenuButtonMouse2() {
    		return false;
    	}
    	
    }

}



