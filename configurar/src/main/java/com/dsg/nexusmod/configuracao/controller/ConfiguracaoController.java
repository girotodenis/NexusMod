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
import com.dsg.nexusmod.ui.OnInit;

public class ConfiguracaoController implements Controller<ConfiguracaoPanel>, OnInit, PluginlObserver {

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
		
		this.contextApp = contextApp;
		contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.visible", true);
		
		osgiService.bundles().forEach(p->{
			PluginModel pluginModel = new PluginModel(p);
			pluginModel.addObserver(this);
			listaPlugin.add(pluginModel);
		});
	}

	public void stop() {
		System.out.println("ConfiguracaoController stop");
		listaPlugin.clear();
		contextApp.fireEvent("com.dsg.ui.componente.CustomSideMenu$MenuItem.Configuração.visible", false);
		
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
		}
		if("STARTED".equals(plugin.getState())) {
			this.osgiService.restartBundle(plugin.getPluginId());
		}		
	}

}
