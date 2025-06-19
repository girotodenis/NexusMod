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
	public void installBundle(String directoryPath) {
		this.osgi.installBundle(directoryPath);
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
	public <T,P> void registerPlugin(Class<T> classPlugin, BiConsumer<T,P> putter) {
		this.osgi.registerPlugin(classPlugin, putter);
	}

	@Override
	public List<Plugin> bundles() {
		return this.osgi.bundles();
	}

//	@Override
//	public void loadBundles(String directoryPath) {
//		 this.osgi.loadBundles(directoryPath);
//	}

}
