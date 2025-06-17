package com.dsg.nexusmod.configuracao.ui;

import com.dsg.nexusmod.controller.ContextApp;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.OnInit;

public class ControllerTeste implements Controller<Teste>, OnInit {

	Teste panel = new Teste();
	ContextApp contextApp;
	int count = 0;
	@Override
	public Teste getPanel() {
		return panel;
	}

	@Override
	public void onInit(ContextApp contextApp) {
		this.contextApp = contextApp;
		
		panel.button.addActionListener(e -> {
			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração", ++count);
		});
		
	}

}
