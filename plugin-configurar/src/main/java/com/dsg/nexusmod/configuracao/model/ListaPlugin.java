package com.dsg.nexusmod.configuracao.model;

import java.util.List;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.osgi.Plugin;

public class ListaPlugin extends ModelAbstract<List<PluginModel>> implements PluginlObserver {


	List<PluginModel> plugins;
	
	public ListaPlugin(List<PluginModel> plugins) {
		super(plugins);
		this.plugins = plugins;
	}
	
	public void add(PluginModel plugin) {
		plugin.addObserver(this);
		this.plugins.add(plugin);
		notifyObservers();
	}
	
	public void add(List<PluginModel> plugins) {
		this.plugins.clear();
		this.plugins.forEach(p -> p.removeObserver(this));
		
		plugins.forEach(p ->{
			p.removeObserver(this);
			this.plugins.add(p);
		});
		
		notifyObservers();
	}

	
	public void remove(Plugin plugin) {
		this.plugins = this.plugins.stream()
			.filter(p -> !p.getPlugin().getPluginId().equals(plugin.getPluginId()))
			.toList();
		notifyObservers();
	}

	public void clear() {
		plugins.clear();
		notifyObservers();
	}

	@Override
	public void update(Plugin model) {
		notifyObservers();
	}
	

}
