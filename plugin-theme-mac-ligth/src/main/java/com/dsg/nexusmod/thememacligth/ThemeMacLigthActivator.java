package com.dsg.nexusmod.thememacligth;

import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.ControllerPlugin;
import com.dsg.nexusmod.ui.OnInit;

public class ThemeMacLigthActivator extends org.pf4j.Plugin {

	private static ControllerTheme controllerTheme;
	
    public ThemeMacLigthActivator(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    public void start() {
    	if (controllerTheme != null && controllerTheme instanceof OnInit) {
    		controllerTheme.start();
		}
    }

    public void stop() {
    	if (controllerTheme != null) {
    		controllerTheme.stop();
		}
    }

    @Extension
    public static class ControllerPluginImpl implements ControllerPlugin {

		@Override
		public Controller newController(ControllerRoot menu, Plugin plugin) {
			
			controllerTheme = new ControllerTheme();
			return controllerTheme;
			
		}
    }

}