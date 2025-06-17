package com.dsg.nexusmod.configuracao.ui;

import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.nexusmod.ui.Controller;
import com.dsg.nexusmod.ui.OnInit;

public class ControllerTeste implements Controller<Teste>, OnInit {

	Teste panel = new Teste();
	int count = 1;
	boolean menuVisible = true;
	ContextApp contextApp;
	
	@Override
	public Teste getPanel() {
		return panel;
	}

	@Override
	public void onInit(ContextApp contextApp) {
		this.contextApp = contextApp;
		
//		panel.buttonBadgeNumber.addActionListener(e -> {
//			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.badgeNumber", count);
//		});
//		
//		panel.buttonMenu.addActionListener(e -> {
//			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.visible", menuVisible= !menuVisible );
//		});
//		
//		contextApp.registerEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.badgeNumber", (data)->{
//			count+=(int)data;
//		});
		
	}

	public void stop(String menuId) {
		if(this.contextApp!=null) {
			this.contextApp.fireEvent(menuId+".visible", false);
		}
	}
	
	public void start(String menuId) {
		if(this.contextApp!=null) {
			this.contextApp.fireEvent(menuId+".visible", true);
		}
	}

}
