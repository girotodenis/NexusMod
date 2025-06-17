package com.dsg.nexusmod.configuracao.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Teste extends JPanel {
	
	public JButton buttonBadgeNumber = new JButton("Teste badgeNumber");
	public JButton buttonMenu = new JButton("Teste Menu visible");
	
	public Teste() {
		//setLayout(new BorderLayout()); // Usar BorderLayout para adicionar título e tabela
		
        // Adicionar título
        JLabel titleLabel = new JLabel("Tela de teste");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);
        add(buttonBadgeNumber, BorderLayout.WEST);
        add(buttonMenu, BorderLayout.CENTER);

        // Criar um painel para os ícones
//        JPanel iconPanel = new JPanel();
//        iconPanel.setLayout(new GridLayout(0, 3)); // 0 linhas e 5 colunas
//        iconPanel.add(buttonBadgeNumber);
//        iconPanel.add(buttonMenu);

//        // Adicionar ícones ao painel
//        Enumeration<Object> keys = UIManager.getDefaults().keys();
//        while (keys.hasMoreElements()) {
//            Object valor = keys.nextElement();
//            try {
//            	if (valor instanceof String) {
//            		String key = (String) valor;
//            		Icon icon = UIManager.getIcon(key);
//            		if (icon != null) {
//            			JLabel iconLabel = new JLabel(key, icon, JLabel.CENTER);
//            			iconLabel.setForeground(Color.WHITE);
//            			iconLabel.setHorizontalAlignment(JLabel.CENTER);
//            			iconPanel.add(iconLabel);
//            		}
//            	}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//        }

        // Adicionar o painel de ícones ao painel principal
//        add(iconPanel, BorderLayout.CENTER);
	}

}
