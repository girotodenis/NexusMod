package com.dsg.nexusmod.teste;

import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.ui.ItemMenu;

public class TesteActivator extends Plugin {

	private static final Logger log = LoggerFactory.getLogger(TesteActivator.class);
	
	private static TesteActivator testePlugin;
	private static TesteController testeController;
	
    public TesteActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	if (testeController == null) {
    		testeController = new TesteController();
    	}
    	
    	testeController.start();
    }

    public void stop() {
    	if (testeController != null) {
			testeController.stop();
		}
    }
    
    public void delete() {
    	log.trace("delete");
    	if (testeController != null) {
    		testeController.stop();
    		testeController = null;
    	}
    }

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(ControllerRoot root) {
			
			if(testeController == null) {
				testeController = new TesteController();
			}
			
			root.addMenuItem(MenuItem.builder()
										.text("Tela de Teste")
										.group("Teste")
										.icon(UIManager.getIcon("FileChooser.detailsViewIcon"))
										.controller(testeController)
									.build()
			);
			
			
		}
    }
    

}