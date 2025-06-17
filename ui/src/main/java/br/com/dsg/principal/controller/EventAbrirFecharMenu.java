package br.com.dsg.principal.controller;

import br.com.dsg.principal.view.PrincipalView;
import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.util.Constantes;

public class EventAbrirFecharMenu {

	public EventAbrirFecharMenu(PrincipalView principal) {

		boolean abrir = !principal.getMenu().isAberto();
		if (abrir) {
			
			abrir(principal);

		} else {
			
			fechar(principal);

		}
	}

	private void fechar(PrincipalView principal) {
		principal.getMenu().encolherItens();
		
		int largura = principal.getWidth() - Constantes.LARGURA_MENU_FECHADO;
		
		principal.remove(principal.getPrincipalJpane());
		principal.getContentPane().add(principal.getPrincipalJpane(), 
				new AbsoluteConstraints(Constantes.LARGURA_MENU_FECHADO,
				Constantes.ALTURA_APP_BAR, largura, principal.getHeight()-Constantes.ALTURA_APP_BAR));
		
		principal.remove(principal.getAppBar());
		principal.getContentPane().add(principal.getAppBar(), 
				new AbsoluteConstraints(Constantes.LARGURA_MENU_FECHADO,
						0, largura, Constantes.ALTURA_APP_BAR));
		
		principal.getAppBar().remove(principal.getAppBar().getjProgressBar());
		principal.getAppBar().add(principal.getAppBar().getjProgressBar(), 
				new AbsoluteConstraints(0,
						Constantes.ALTURA_APP_BAR - Constantes.ALTURA_PROGRESS_BAR, largura, Constantes.ALTURA_APP_BAR));

		principal.remove(principal.getMenu());
		principal.getContentPane().add(principal.getMenu(),
				new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_FECHADO, principal.getHeight()));

	}

	private void abrir(PrincipalView principal) {
		principal.getMenu().expandirItens();
		
		int largura = principal.getWidth() - Constantes.LARGURA_MENU_ABERTO;
		principal.remove(principal.getPrincipalJpane());
		principal.getContentPane().add(principal.getPrincipalJpane(), 
				new AbsoluteConstraints(Constantes.LARGURA_MENU_ABERTO,
					Constantes.ALTURA_APP_BAR, largura, principal.getHeight()-Constantes.ALTURA_APP_BAR));
		
		principal.remove(principal.getAppBar());
		principal.getContentPane().add(principal.getAppBar(), 
				new AbsoluteConstraints(Constantes.LARGURA_MENU_ABERTO,
						0, largura, Constantes.ALTURA_APP_BAR));
		
		principal.getAppBar().remove(principal.getAppBar().getjProgressBar());
		principal.getAppBar().add(principal.getAppBar().getjProgressBar(), 
				new AbsoluteConstraints(0,
						Constantes.ALTURA_APP_BAR - Constantes.ALTURA_PROGRESS_BAR, largura, Constantes.ALTURA_APP_BAR));
		
		principal.remove(principal.getMenu());
		principal.getContentPane().add(principal.getMenu(),
				new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_ABERTO, principal.getHeight()));

	}

}
