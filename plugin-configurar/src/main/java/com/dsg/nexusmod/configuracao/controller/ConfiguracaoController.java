package com.dsg.nexusmod.configuracao.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dsg.nexusmod.configuracao.model.ListaPluginModel;
import com.dsg.nexusmod.configuracao.model.PluginModel;
import com.dsg.nexusmod.configuracao.model.PluginlObserver;
import com.dsg.nexusmod.configuracao.ui.ConfiguracaoView;
import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;

public class ConfiguracaoController implements ControllerContent<ConfiguracaoView>, OnInit,OnChange, PluginlObserver {

	ConfiguracaoView panel;
	ControllerRoot contextApp;
	int count = 0;
	boolean visible = true;
	OSGiFramework osgiService;
	
	ListaPluginModel listaPlugin = new ListaPluginModel(new ArrayList<>());
	
	@Override
	public ConfiguracaoView getPanel() {
		return panel;
	}

	public ConfiguracaoController(OSGiFramework osgiService) {
		this.osgiService = osgiService;
		this.panel = new ConfiguracaoView(listaPlugin);
	}

	@Override
	public void onInit(ControllerRoot contextApp) {
		this.contextApp = contextApp;
		
		if(this.contextApp==null) {
			return ;
		}
		
		 this.panel.file.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Criar um JFileChooser
	            	System.out.println("abrir");
	                JFileChooser fileChooser = new JFileChooser();
	                fileChooser.setDialogTitle("Selecione um plugin");
	                File defaultDirectory = new File("../");
	                fileChooser.setCurrentDirectory(defaultDirectory);
	                FileNameExtensionFilter jarFilter = new FileNameExtensionFilter("Arquivos JAR (*.jar)", "jar");
	                fileChooser.setFileFilter(jarFilter);
	             // Abrir a janela de diálogo
	                int result = fileChooser.showOpenDialog(contextApp.getFrame());

	                // Verificar se o usuário selecionou um arquivo
	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    try {
	                    	osgiService.copyInstallBundle(selectedFile.getCanonicalPath());
//	                    	osgiService.installBundle(selectedFile.getCanonicalPath(), false);
	                    } catch (Exception e2) {
	                    	e2.printStackTrace();
	                    }
	                }
	                
	            }
	        });
		
		contextApp.registerEvent("ConfiguracaoController.notificacao", (data)->{
			carregarListaPlugins();
			atualizarBadgeNumber(contextApp, data);
		});
	}

	private void atualizarBadgeNumber(ControllerRoot contextApp, Object data) {
		count = 0;
		listaPlugin.getModel().forEach(p -> {
			if(p.getPluginId().equals(data)) {
				p.setNotificacao(true);
				count++;
			}else if(p.isNotificacao()) {
				count++;
			}
		});
		contextApp.menuEvent("Configuração", "badgeNumber", count);
	}
	
	@Override
	public void onChage(ControllerRoot contextApp) {
		carregarListaPlugins();
		atualizarBadgeNumber(contextApp, "");
	}

	private void carregarListaPlugins() {
		var list = osgiService.bundles().stream().map(p->{
			PluginModel pluginModel = new PluginModel(p);
			pluginModel.setDisable(p.getPluginId().equals("configurar-plugin"));
			pluginModel.addObserver(this);
			return pluginModel;
		}).toList();
		listaPlugin.add(list);
		listaPlugin.notifyObservers();
	}

	public void stop() {
		listaPlugin.clear();
		contextApp.menuEvent("Configuração","visible", false);
	}

	public void start() {
		if(contextApp != null && osgiService != null) {
			listaPlugin.clear();
			onInit(contextApp);
		}
	}

	@Override
	public void update(Plugin plugin) {
		if("STOPED".equals(plugin.getState())) {
			this.osgiService.stopBundle(plugin.getPluginId());
		}else if("DELETED".equals(plugin.getState())) {
			this.osgiService.deleteBundle(plugin.getPluginId());
		}else {
			this.osgiService.restartBundle(plugin.getPluginId());
		}
		contextApp.fireEvent("ConfiguracaoController.notificacao", null);
	}

	

}
