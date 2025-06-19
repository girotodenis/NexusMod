package com.dsg.nexusmod.configuracao.controller;

import java.util.ArrayList;

import com.dsg.nexusmod.configuracao.model.ListaPlugin;
import com.dsg.nexusmod.configuracao.model.PluginModel;
import com.dsg.nexusmod.configuracao.model.PluginlObserver;
import com.dsg.nexusmod.configuracao.ui.ConfiguracaoPanel;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.osgi.OSGiFramework;
import com.dsg.nexusmod.osgi.Plugin;
import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;

public class ConfiguracaoController implements Controller<ConfiguracaoPanel>, OnInit,OnChange, PluginlObserver {

	ConfiguracaoPanel panel;
	ContextApp contextApp;
	int count = 0;
	boolean visible = true;
	OSGiFramework osgiService;
	
	ListaPlugin listaPlugin = new ListaPlugin(new ArrayList<>());
	
	@Override
	public ConfiguracaoPanel getPanel() {
		return panel;
	}

	public ConfiguracaoController(OSGiFramework osgiService) {
		this.osgiService = osgiService;
		this.panel = new ConfiguracaoPanel(listaPlugin);
	}

	@Override
	public void onInit(ContextApp contextApp) {
		System.out.println("ConfiguracaoController onInit");
		this.contextApp = contextApp;
		
		if(this.contextApp==null) {
			System.out.println("ContextApp não pode ser nulo. Certifique-se de que o ContextApp foi inicializado antes de chamar onInit.");
			return ;
		}
		
		contextApp.registerEvent("ConfiguracaoController.notificacao", (data)->{
			System.out.println("ConfiguracaoController notificacao: " + data);
			count = 0;
			carregarListaPlugins();
			listaPlugin.getModel().forEach(p -> {
				if(p.getPluginId().equals(data)) {
					p.setNotificacao(true);
					count++;
				}else if(p.isNotificacao()) {
					count++;
				}
			});
			contextApp.menuEvent("Configuração", "badgeNumber", count);
			listaPlugin.notifyObservers();
		});
	}
	
	@Override
	public void onChage(ContextApp contextApp) {
		System.out.println("ConfiguracaoController onChage");
		carregarListaPlugins();
	}

	private void carregarListaPlugins() {
		var list = osgiService.bundles().stream().map(p->{
			PluginModel pluginModel = new PluginModel(p);
			pluginModel.setDisable(p.getPluginId().equals("configurar-plugin"));
			pluginModel.addObserver(this);
			return pluginModel;
		}).toList();
		listaPlugin.add(list);
	}

	public void stop() {
		System.out.println("ConfiguracaoController stop");
		listaPlugin.clear();
		contextApp.menuEvent("Configuração","visible", false);
		
	}

	public void start() {
		if(contextApp != null && osgiService != null) {
			System.out.println("ConfiguracaoController start");
			listaPlugin.clear();
			onInit(contextApp);
		}
	}

	@Override
	public void update(Plugin plugin) {
		System.out.println("Plugin atualizado: " + plugin.getPluginId() + " - " + plugin.getState());
		if("STOPED".equals(plugin.getState())) {
			this.osgiService.stopBundle(plugin.getPluginId());
		}else {
			this.osgiService.restartBundle(plugin.getPluginId());
		}
		contextApp.fireEvent("ConfiguracaoController.notificacao", null);
	}

	

}
