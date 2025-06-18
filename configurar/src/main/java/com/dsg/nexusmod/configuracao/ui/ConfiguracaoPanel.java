package com.dsg.nexusmod.configuracao.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.dsg.nexusmod.osgi.Plugin;

public class ConfiguracaoPanel extends JPanel {

	JPanel listPanel; // Painel que contém os itens da lista
	Consumer<Plugin> consumer;
    public ConfiguracaoPanel(List<Plugin> plugins, Consumer<Plugin> consumer) {
    	
    	this.consumer = consumer;
        // Configura o layout do painel principal
        setLayout(new BorderLayout());

        // Painel central para a lista de plugins
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Elementos organizados verticalmente
        JScrollPane scrollPane = new JScrollPane(listPanel);

        // Carrega os itens iniciais
        load(plugins);

        // Adiciona o painel rolável ao painel principal
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Método para criar cada item do painel com informações de um plugin.
     */
    private JPanel createPluginPanel(Plugin plugin) {
        JPanel pluginPanel = new JPanel(new GridBagLayout());
//        pluginPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borda para visualização
        pluginPanel.setPreferredSize(new Dimension(600, 70)); // Tamanho fixo do painel
        pluginPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); // Garante altura fixa
        pluginPanel.setMinimumSize(new Dimension(600, 70)); // Garante tamanho mínimo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        // Linha superior: ID e versão (esquerda)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pluginPanel.add(new JLabel(plugin.getPluginId() + " (" + plugin.getVersao() + ")"), gbc);

        // Linha superior: Estado (centro)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel stateLabel = new JLabel(plugin.getState());
        pluginPanel.add(stateLabel, gbc);

        // Linha superior: Botão toggle (direita)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JToggleButton toggleButton = new JToggleButton(plugin.getState().equals("STARTED") ? "Desativar" : "Ativar");
        toggleButton.setSelected("STARTED".equals(plugin.getState())); // Define estado inicial do botão
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	toggleButton.setText("STARTED".equals(plugin.getState())? "Desativar": "Ativar");
                consumer.accept(plugin);
            }
        });
        pluginPanel.add(toggleButton, gbc);

        // Linha inferior: Descrição (ocupa toda a largura)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todas as 3 colunas
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pluginPanel.add(new JLabel("Descrição: " + plugin.getDescription()), gbc);

        return pluginPanel;
    }

    /**
     * Método para carregar uma nova lista de plugins e atualizar a interface.
     */
    public void load(List<Plugin> plugins) {
        // Remove todos os componentes existentes
        listPanel.removeAll();

        // Adiciona os novos itens
        for (Plugin plugin : plugins) {
            JPanel itemPanel = createPluginPanel(plugin);
            listPanel.add(itemPanel);
        }

        // Atualiza a interface gráfica
        listPanel.revalidate();
        listPanel.repaint();
    }
}