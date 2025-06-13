package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventAbrirFecharMenu extends EventoController<LeGuiController>{
	
	protected Boolean removerMenu;
	protected Boolean fechar;

	public EventAbrirFecharMenu(Boolean fechar, Boolean removerMenu) {
		super(LeGuiController.get());
		this.fechar = fechar;
		this.removerMenu = removerMenu;
	}
	
	public EventAbrirFecharMenu() {
		this(null, Boolean.FALSE);
	}

}
