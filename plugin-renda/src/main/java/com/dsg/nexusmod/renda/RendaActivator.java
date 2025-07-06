package com.dsg.nexusmod.renda;


import javax.swing.UIManager;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.osgi.CoreResourses;
import com.dsg.nexusmod.osgi.OsgiPlugin;
import com.dsg.nexusmod.renda.controller.CarteiraController;
import com.dsg.nexusmod.ui.ItemMenu;

public class RendaActivator extends Plugin {

	private static final Logger log = LoggerFactory.getLogger(RendaActivator.class);
	
	private static CoreResourses resourses;
	
    public RendaActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Extension
    public static class OsgiPluginImpl implements OsgiPlugin {
		@Override
		public void load(CoreResourses resourses) {
			RendaActivator.resourses = resourses;
		}
    }
   
 

    @Extension
    public static class ItemMenuImpl implements ItemMenu {

		@Override
		public void addItemMenu(ControllerRoot root) {
			
			root.addMenuItem(MenuItem.builder()
										.text("Carteira")
										.group("Meu Dinenhiro")
										.icon(UIManager.getIcon("FileChooser.detailsViewIcon"))
										.controller( ()-> new CarteiraController())
									.build()
			);
			
			
		}
    }
    

}