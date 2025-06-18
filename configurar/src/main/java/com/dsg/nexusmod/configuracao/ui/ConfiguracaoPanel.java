package com.dsg.nexusmod.configuracao.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dsg.nexusmod.configuracao.model.ListPluginObsever;
import com.dsg.nexusmod.configuracao.model.ListaPlugin;
import com.dsg.nexusmod.configuracao.model.PluginModel;

public class ConfiguracaoPanel extends JPanel implements ListPluginObsever {

	ListaPlugin model; // Modelo que contém a lista de plugins
	JPanel listPanel; // Painel que contém os itens da lista
//	Consumer<Plugin> consumer;
	
    public ConfiguracaoPanel( ListaPlugin model) {
    	
    	this.model = model;
        // Configura o layout do painel principal
        setLayout(new BorderLayout());

        // Painel central para a lista de plugins
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Elementos organizados verticalmente
        JScrollPane scrollPane = new JScrollPane(listPanel);

        // Carrega os itens iniciais
        update(model.getModel());

        // Adiciona o painel rolável ao painel principal
        add(scrollPane, BorderLayout.CENTER);
        
        this.model.addObserver(this);
    }

    /**
     * Método para criar cada item do painel com informações de um plugin.
     */
    private ListPluginView createPluginPanel(PluginModel plugin) {
        return new ListPluginView(plugin); // Retorna o painel do plugin
    }

    /**
     * Método para carregar uma nova lista de plugins e atualizar a interface.
     */
    public void update(List<PluginModel> plugins) {
    	
    	System.out.println("Atualizando lista de plugins: " + plugins.size() + " plugins encontrados.");
        // Remove todos os componentes existentes
        listPanel.removeAll();

        // Adiciona os novos itens
        for (PluginModel plugin : plugins) {
            JPanel itemPanel = createPluginPanel(plugin);
            listPanel.add(itemPanel);
        }

        // Atualiza a interface gráfica
        listPanel.revalidate();
        listPanel.repaint();
    }



	
}