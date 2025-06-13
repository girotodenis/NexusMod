package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventVoltarController extends EventoController<LeGuiController>{

	
	private AbstractController<?> controller;
	private Object valorDeCallback;
	

	public EventVoltarController(AbstractController<?> controller) {
		this(controller, null);
	}

	public EventVoltarController(AbstractController<?> controller, Object valorDeCallback) {
		super(LeGuiController.get());
		this.controller = controller;
		this.valorDeCallback = valorDeCallback;
	}

	public AbstractController<?> getController() {
		return controller;
	}

	public Object getValorDeCallback() {
		return valorDeCallback;
	}

}
