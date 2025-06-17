package com.dsg.nexusmod.configuracao.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Teste extends JPanel {
	
	public JButton button = new JButton("Teste");
	
	public Teste() {
		setLayout(new BorderLayout()); // Usar BorderLayout para adicionar título e tabela
		
        // Adicionar título
        JLabel titleLabel = new JLabel("Tela de teste");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Criar um painel para os ícones
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridLayout(0, 3)); // 0 linhas e 5 colunas
        iconPanel.add(button);

        // Adicionar ícones ao painel
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object valor = keys.nextElement();
            if (valor instanceof String) {
                String key = (String) valor;
                Icon icon = UIManager.getIcon(key);
                if (icon != null) {
                    JLabel iconLabel = new JLabel(key, icon, JLabel.CENTER);
//                    iconLabel.setForeground(Color.WHITE);
                    iconLabel.setHorizontalAlignment(JLabel.CENTER);
                    iconPanel.add(iconLabel);
                }
            }
        }

        // Adicionar o painel de ícones ao painel principal
        add(iconPanel, BorderLayout.CENTER);
	}

}
