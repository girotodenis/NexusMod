package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventLoginApp extends EventoController<LeGuiController>{
	
	private AbstractController<?> newController;
	
	public EventLoginApp( AbstractController<?> newController) {
		super(LeGuiController.get());
		this.newController = newController;
	}

	protected AbstractController<?> getNewController() {
		return newController;
	}
	
	
}
