package com.dsg.nexusmod.thememacligth;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.slf4j.Logger;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.ui.OnInit;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerTheme implements Controller, OnInit {

	private static final Logger log = LoggerFactory.getLogger(ControllerTheme.class);
	
	ControllerRoot contextApp;
	static LookAndFeel padrao;

	@Override
	public void onInit(ControllerRoot contextApp) {
		log.trace("getLookAndFeel: {}", UIManager.getLookAndFeel());
		this.contextApp = contextApp;
		if(padrao==null)
			padrao = UIManager.getLookAndFeel();
		log.trace(padrao.getClass().getName());
		contextApp.updateAll(FlatMacLightLaf.class);
		log.trace("padrao: "+padrao.getClass().getSimpleName());
	}

	public void start() {
		if(contextApp == null) {
			return;
		}
		contextApp.updateAll(FlatMacLightLaf.class);
	}

	public void stop() {
		if(contextApp == null) {
			return;
		}
		contextApp.updateAll(padrao.getClass());
	}

}
