package com.dsg.nexusmod.configuracao.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.dsg.nexusmod.configuracao.model.PluginModel;
import com.dsg.nexusmod.configuracao.model.PluginlObserver;
import com.dsg.nexusmod.osgi.Plugin;

public class ListPluginView extends JPanel implements PluginlObserver{

	PluginModel plugin;
	JToggleButton toggleButton;
	
	public ListPluginView(PluginModel plugin) {
		this.plugin = plugin;
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borda para visualização
        setPreferredSize(new Dimension(600, 70)); // Tamanho fixo do painel
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); // Garante altura fixa
        setMinimumSize(new Dimension(600, 70)); // Garante tamanho mínimo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        // Linha superior: ID e versão (esquerda)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(plugin.getPluginId() + " (" + plugin.getVersao() + ")"), gbc);

        // Linha superior: Estado (centro)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel stateLabel = new JLabel(plugin.getState());
        add(stateLabel, gbc);

        // Linha superior: Botão toggle (direita)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JToggleButton toggleButton = new JToggleButton(plugin.getState().equals("STARTED") ? "Desativar" : "Ativar");
        toggleButton.setSelected("STARTED".equals(plugin.getState())); // Define estado inicial do botão
        toggleButton.setEnabled(!plugin.isDisable()); // Define visibilidade do botão com base no estado do plugin
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(plugin.isNotificacao()) {
            		plugin.setNotificacao(false);
            	}
            	plugin.setState( toggleButton.isSelected() ? "STARTED": "STOPED");
            }
        });
        add(toggleButton, gbc);

        // Linha inferior: Descrição (ocupa toda a largura)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todas as 3 colunas
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Descrição: " + plugin.getDescription()), gbc);
	}
	
	@Override
	public void update(Plugin plugin) {
		toggleButton.setText("STARTED".equals(plugin.getState())? "Desativar": "Ativar");
	}

}
