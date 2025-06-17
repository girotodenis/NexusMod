package com.dsg.nexusmod.configuracao.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.nexusmod.ui.Controller;
import com.dsg.nexusmod.ui.OnInit;

public class ControllerTeste implements Controller<Teste>, OnInit {

	int count = 1;
	boolean menuVisible = true;
	ContextApp contextApp;
	
	public ControllerTeste() {
	}
	

	@Override
	public void onInit(ContextApp contextApp, JPanel content) {
		this.contextApp = contextApp;
		
		JLabel titleLabel = new JLabel("Tela de teste");
		JButton buttonBadgeNumber = new JButton("Teste badgeNumber");
		JButton buttonMenu = new JButton("Teste Menu visible");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(buttonBadgeNumber, BorderLayout.WEST);
        content.add(buttonMenu, BorderLayout.CENTER);
		
		buttonBadgeNumber.addActionListener(e -> {
			System.out.println("Badge Number: " + count);
			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.badgeNumber", count);
		});
		
		buttonMenu.addActionListener(e -> {
			System.out.println("visible: " + !menuVisible);
			contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.visible", menuVisible= !menuVisible );
		});
		
		contextApp.registerEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.badgeNumber", (data)->{
			System.out.println("Badge Number: " + count);
			count+=(int)data;
		});
		
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
