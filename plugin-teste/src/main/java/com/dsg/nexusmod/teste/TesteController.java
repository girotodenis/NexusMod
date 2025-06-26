package com.dsg.nexusmod.teste;

import javax.swing.JButton;

import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.teste2.TesteColorController;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.nexusmod.ui.TaskNotificationType;

public class TesteController implements ControllerContent<TesteView>, OnInit, OnChange {

	TesteView panel;
	ControllerRoot contextApp;
	int count = 0;
	boolean visible = true;
	
	
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
		visible = true;
		count=0;
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

	@Override
	public void onChage(ControllerRoot contextApp) {
		createView(contextApp);
	}

	private void createView(ControllerRoot contextApp) {
		
		
		
		this.panel = new TesteView();
		
		panel.button.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.menuEvent("Tela_de_Teste", "badgeNumber", ++count);
		});
		
		panel.button2.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.menuEvent("Tela_de_Teste", "visible", visible = !visible);
		});
		
		panel.button3.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.showContent(new TesteColorController(this, 25));
		});
		
		panel.button4.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.addNotification("Esta é uma mensagem muito longa que precisa quebrar a linha e ajustar o tamanho do card para baixo.", TaskNotificationType.INFO);
		});
		
		panel.button5.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.addNotification("Isso é uma notificação de informação", TaskNotificationType.WARN);
		});
		
		panel.button6.addActionListener(e -> {
			System.out.println(((JButton)e.getSource()).getText()+" click");
			contextApp.addNotification("Isso é uma notificação de Erro", TaskNotificationType.ERROR);
		});
	}




}
