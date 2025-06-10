package com.dsg.nexusmod.osgi;

import org.osgi.framework.BundleActivator;

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
	public void setBundleDirectory(String directoryPath) {
		this.osgi.setBundleDirectory(directoryPath);
	}

	
	@Override
	public void installBundle(Object bundleClass) {
		this.osgi.installBundle(bundleClass);
	}
	

	@Override
	public void stopBundle(long bundleId) {
		this.osgi.stopBundle(bundleId);
	}

	@Override
	public void restartBundle(long bundleId) {
		this.osgi.restartBundle(bundleId);
	}

	@Override
	public void uninstallBundle(long bundleId) {
		this.osgi.uninstallBundle(bundleId);
	}

}
