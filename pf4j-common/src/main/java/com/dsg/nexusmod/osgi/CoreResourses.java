package com.dsg.nexusmod.osgi;

public class CoreResourses {
	
	private Session session;
	private OSGiFramework osgiService;
	private Plugin plugin;
	
	public CoreResourses(Session session, OSGiFramework osgiService, Plugin plugin) {
		super();
		this.session = session;
		this.osgiService = osgiService;
		this.plugin = plugin;
	}

	public Session getSession() {
		return session;
	}

	public OSGiFramework getOsgiService() {
		return osgiService;
	}

	public Plugin getPlugin() {
		return plugin;
	}
	
}
