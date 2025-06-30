package com.dsg.nexusmod.osgi;

public class Plugin {
	private int ordem = 0;
	private String pluginId;
	private String versao;
	private String state;
	private String description;

	public Plugin(String pluginId, String versao, String state, String description) {
		super();
		this.pluginId = pluginId;
		this.versao = versao;
		this.state = state;
		this.description = description;
	}

	public String getPluginId() {
		return pluginId;
	}

	public String getVersao() {
		return versao;
	}

	public String getState() {
		return state;
	}
	
	public String getDescription() {
		return description;
	}
	

	public void setState(String state) {
		this.state = state;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	@Override
	public String toString() {
		return "Plugin [pluginId=" + pluginId + ", versao=" + versao + ", state=" + state + "]";
	}

	
}
