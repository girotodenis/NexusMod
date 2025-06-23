package com.dsg.nexusmod.teste2;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;

public class TesteColorController implements Controller<ColorGridPanelView>, OnInit, OnChange {

	ColorGridPanelView panel;
	Controller<?> pai;
	
	
	@Override
	public ColorGridPanelView getPanel() {
		return panel;
	}

	public TesteColorController(Controller<?> pai, int col) {
		this.panel = new ColorGridPanelView(col);
		this.pai = pai;
	}

	@Override
	public void onInit(ControllerRoot contextApp) {
		
		panel.button.addActionListener(e -> {
			contextApp.showContent(pai);
		});
		
	}

	@Override
	public void onChage(ControllerRoot contextApp) {
	}



}
