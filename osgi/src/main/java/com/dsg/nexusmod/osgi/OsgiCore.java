package com.dsg.nexusmod.osgi;

import java.util.List;
import java.util.function.BiConsumer;

public class OsgiCore implements OSGiFramework {
	
	private OSGiFramework osgi;
	
	public OsgiCore(OSGiFramework osgi) {
		this.osgi = osgi;
	}

	@Override
	public void start() {
		this.osgi.start();
	}

	@Override
	public void stop() {
		this.osgi.stop();
	}

	@Override
	public String installBundle(String directoryPath, boolean started) {
		return this.osgi.installBundle(directoryPath, started);
	}	

	@Override
	public void stopBundle(String bundleId) {
		this.osgi.stopBundle(bundleId);
	}

	@Override
	public void restartBundle(String bundleId) {
		this.osgi.restartBundle(bundleId);
	}

	@Override
	public void uninstallBundle(String bundleId) {
		this.osgi.uninstallBundle(bundleId);
	}

	@Override
	public void registerPlugin(Class classPlugin, BiConsumer putter) {
		this.osgi.registerPlugin(classPlugin, putter);
	}

	@Override
	public List<Plugin> bundles() {
		return this.osgi.bundles();
	}

	@Override
	public void loadPlugins() {
		this.osgi.loadPlugins();
	}

	@Override
	public void deleteBundle(String pluginId) {
		this.osgi.deleteBundle(pluginId);
	}

	@Override
	public void copyInstallBundle(String canonicalPath) {
		this.osgi.copyInstallBundle(canonicalPath);
	}

//	@Override
//	public void loadBundles(String directoryPath) {
//		 this.osgi.loadBundles(directoryPath);
//	}

}
