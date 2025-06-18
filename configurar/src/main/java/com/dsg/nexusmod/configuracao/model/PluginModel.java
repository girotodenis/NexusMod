package com.dsg.nexusmod.configuracao.model;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.osgi.Plugin;

public class PluginModel extends ModelAbstract<Plugin> {


	private Plugin plugin;
	
	public PluginModel(Plugin plugin) {
		super(plugin);
		this.plugin =  plugin;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setState(String state) {
		this.getModel().setState(state);
		notifyObservers();
	}

	public String getPluginId() {
		return plugin.getPluginId();
	}
	public String getDescription() {
		return plugin.getDescription();
	}
	public String getVersao() {
		return plugin.getVersao();
	}
	public String getState() {
		return plugin.getState();
	}

}
