package com.dsg.nexusmod.thememacligth;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.ui.OnInit;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class ControllerTheme implements Controller, OnInit {

	ControllerRoot contextApp;
	LookAndFeel padrao;

	@Override
	public void onInit(ControllerRoot contextApp) {
		this.contextApp = contextApp;
		padrao = UIManager.getLookAndFeel();
		System.out.println(padrao.getClass().getName());
		contextApp.updateAll(FlatMacLightLaf.class);
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
