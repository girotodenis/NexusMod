package com.dsg.nexusmod.teste;

import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.teste2.TesteColorController;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.nexusmod.ui.TaskNotificationType;

public class TesteController implements ControllerContent<TesteView>, OnInit {

	TesteView panel;
	ControllerRoot contextApp;
	int count = 0;
	boolean visible = true;
	
	TesteColorController proximaTela = new TesteColorController(this, 25);
	
	@Override
	public TesteView getPanel() {
		return panel;
	}

	public TesteController() {
		this.panel = new TesteView();
	}

	@Override
	public void onInit(ControllerRoot contextApp) {
		
		this.contextApp = contextApp;
		this.panel = new TesteView();
		
		contextApp.menuEvent("Tela_de_Teste", "visible", visible = true);
		
		panel.button.addActionListener(e -> {
			contextApp.menuEvent("Tela_de_Teste", "badgeNumber", ++count);
		});
		
		panel.button2.addActionListener(e -> {
			contextApp.menuEvent("Tela_de_Teste", "visible", visible = !visible);
		});
		
		panel.button3.addActionListener(e -> {
			
			contextApp.showContent(proximaTela);
		});
		
		panel.button4.addActionListener(e -> {
			
			contextApp.addNotification("Esta é uma mensagem muito longa que precisa quebrar a linha e ajustar o tamanho do card para baixo.", TaskNotificationType.INFO);
		});
		panel.button5.addActionListener(e -> {
			
			contextApp.addNotification("Isso é uma notificação de informação", TaskNotificationType.WARN);
		});
		panel.button6.addActionListener(e -> {
			
			contextApp.addNotification("Isso é uma notificação de Erro", TaskNotificationType.ERROR);
		});
		
	}

	public void stop() {
		visible = false;
		if(contextApp != null) {
			contextApp.fireEvent("ConfiguracaoController.notificacao","x");
			contextApp.menuEvent("Tela_de_Teste", "visible", visible);
			contextApp.menuEvent("Tela_de_Teste", "badgeNumber", count = 0);
			contextApp.removeMenu("Tela_de_Teste");
		}
		
		if(panel!=null) {
			panel.removeAll();
			panel = null;
		}
	}
	
	

	public void start() {
		if(contextApp != null) {
			onInit(contextApp);
		}
	}




}
