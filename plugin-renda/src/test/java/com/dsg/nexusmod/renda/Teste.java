package com.dsg.nexusmod.renda;

import javax.swing.UIManager;

import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.renda.controller.CarteiraController;
import com.dsg.ui.AppUtilities;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var app = AppUtilities.builder()
				//.lookAndFeel(FlatDarculaLaf.class)
				.size(1224, 768).title("Teste OSGi").build()
				.getMain();
		
			
			app.addMenuItem(MenuItem.builder()
				.text("Carteira")
				.group("Meu Dinenhiro")
				.icon(UIManager.getIcon("FileChooser.detailsViewIcon"))
				.controller(()->new CarteiraController())
			.build());
			
			app.getPanel().loadMenu();
		
	}

}
