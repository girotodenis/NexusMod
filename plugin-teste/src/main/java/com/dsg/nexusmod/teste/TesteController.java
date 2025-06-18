package com.dsg.nexusmod.teste;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.nexusmod.ui.OnInit;

public class TesteController implements Controller<Teste>, OnInit {

	Teste panel;
	ContextApp contextApp;
	int count = 0;
	boolean visible = true;
	
	
	@Override
	public Teste getPanel() {
		return panel;
	}

	public TesteController() {
		this.panel = new Teste();
	}

	@Override
	public void onInit(ContextApp contextApp) {
		
		this.contextApp = contextApp;
		
		this.panel = new Teste();
		
		contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Tela_de_Teste.visible", visible = true);
		
		panel.button.addActionListener(e -> {
			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Tela_de_Teste.badgeNumber", ++count);
		});
		
		panel.button2.addActionListener(e -> {
			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Tela_de_Teste.visible", visible = !visible);
		});
		
	}

	public void stop() {
		System.out.println("TesteController stop");
		contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Tela_de_Teste.visible", visible = false);
		panel.removeAll();
		count = 0;
		contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Tela_de_Teste.badgeNumber", count);
	}

	public void start() {
		if(contextApp != null) {
			System.out.println("TesteController start");
			onInit(contextApp);
		}
	}



}
