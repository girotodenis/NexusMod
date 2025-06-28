package com.dsg.nexusmod.osgi;

import org.pf4j.ExtensionPoint;


public interface OsgiPlugin extends ExtensionPoint{
	void load(CoreResourses resourses);
}
