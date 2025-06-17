package br.com.dsg.principal.controller;

import br.com.dsg.swing.controller.AbstractController;

public class EventVoltarController {

	
	private AbstractController<?> controller;
	private Object valorDeCallback;
	

	public EventVoltarController(AbstractController<?> controller) {
		
		this(controller, null);
	}

	public EventVoltarController(AbstractController<?> controller, Object valorDeCallback) {

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
