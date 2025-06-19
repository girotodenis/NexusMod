package com.dsg.nexusmod.configuracao.model;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.osgi.Plugin;

public class PluginModel extends ModelAbstract<Plugin> {

	private Plugin plugin;
	private boolean disable = false;
	private boolean notificacao = false;

	public PluginModel(Plugin plugin) {
		super(plugin);
		this.plugin = plugin;
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

	public boolean isDisable() {
		return disable;
	}
	
	public boolean isNotificacao() {
		return notificacao;
	}

	public void setNotificacao(boolean notificacao) {
		this.notificacao = notificacao;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
		notifyObservers();
	}

}
