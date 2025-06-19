package com.dsg.nexusmod.teste;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.OnInit;

public class TesteController implements Controller<Teste>, OnInit {

	Teste panel;
	ControllerRoot contextApp;
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
	public void onInit(ControllerRoot contextApp) {
		
		System.out.println("TesteController onInit"+visible);
		this.contextApp = contextApp;
		this.panel = new Teste();
		
		contextApp.menuEvent("Tela_de_Teste", "visible", visible = true);
		
		
		panel.button.addActionListener(e -> {
			contextApp.menuEvent("Tela_de_Teste", "badgeNumber", ++count);
		});
		
		panel.button2.addActionListener(e -> {
			contextApp.menuEvent("Tela_de_Teste", "visible", visible = !visible);
		});
		
	}

	public void stop() {
		System.out.println("TesteController stop");
		
		if(contextApp != null) {
			contextApp.menuEvent("Tela_de_Teste", "visible", visible = false);
			contextApp.menuEvent("Tela_de_Teste", "badgeNumber", count = 0);
		}
		
		panel.removeAll();
	}

	public void start() {
		if(contextApp != null) {
			System.out.println("TesteController start");
			onInit(contextApp);
		}
	}

	public void alertaConfiguracao(Plugin plugin) {
		if(!"STARTED".equals(plugin.getState())) {
			contextApp.fireEvent("ConfiguracaoController.notificacao", plugin.getPluginId());
		}
		
	}



}
