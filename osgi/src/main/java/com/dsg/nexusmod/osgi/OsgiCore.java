package com.dsg.nexusmod.osgi;

import java.util.List;
import java.util.function.Consumer;

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
	public <T> void registerPlugin(Class<T> classPlugin, Consumer<T> putter) {
		this.osgi.registerPlugin(classPlugin, putter);
	}

	@Override
	public List<Plugin> bundles() {
		return this.osgi.bundles();
	}

}
