package com.dsg.nexusmod.configuracao.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dsg.nexusmod.controller.ContextApp;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.OnInit;
import com.dsg.nexusmod.osgi.OSGiFramework;

public class ControllerTeste implements Controller<ConfiguracaoPanel>, OnInit {

	ConfiguracaoPanel panel;
	ContextApp contextApp;
	int count = 0;
	boolean visible = true;
	OSGiFramework osgiService;
	
	
	@Override
	public ConfiguracaoPanel getPanel() {
		return panel;
	}

	public ControllerTeste(OSGiFramework osgiService) {
		this.osgiService = osgiService;
		this.panel = new ConfiguracaoPanel(this.osgiService.bundles(),(plugin)->{
			if("STARTED".equals(plugin.getState())) {
				this.osgiService.stopBundle(plugin.getPluginId());
			}else {
				this.osgiService.restartBundle(plugin.getPluginId());
			}
			this.panel.load(this.osgiService.bundles());
		});
		
//		this.osgiService.bundles().forEach(e->{
//			System.out.println(e);
//		});
	}

	@Override
	public void onInit(ContextApp contextApp) {
		
		
		this.contextApp = contextApp;
		
//		panel.button.addActionListener(e -> {
//			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.badgeNumber", ++count);
//		});
//		
//		panel.button2.addActionListener(e -> {
//			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.visible", visible = !visible);
//		});
//		
	}

}
