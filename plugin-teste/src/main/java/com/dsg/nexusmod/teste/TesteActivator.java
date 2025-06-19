package com.dsg.nexusmod.teste;

import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.ui.ItemMenu;
import com.dsg.nexusmod.ui.MenuPlugin;

public class TesteActivator extends Plugin {

	private static OSGiFramework osgiService;
	
	private static TesteController testeController;
	
    public TesteActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	System.out.println("start "+this.getClass().getName());
    	if (testeController == null) {
    		testeController = new TesteController();
    	}
    	testeController.start();
    }

    public void stop() {
    	System.out.println("stop "+this.getClass().getName());
    	if (testeController != null) {
			testeController.stop();
		}
    }

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(MenuPlugin menu, com.dsg.nexusmod.osgi.Plugin plugin) {
			
			if(testeController == null) {
				testeController = new TesteController();
			}
			
			System.out.println("addItemMenu "+this.getClass().getName());
			menu.addMenuItem("Tela de Teste", UIManager.getIcon("FileChooser.homeFolderIcon"), testeController);
			testeController.alertaConfiguracao(plugin);
			
		}
    }
    

}