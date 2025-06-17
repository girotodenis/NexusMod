package com.dsg.ui.controller;

public class EventoController<E extends AbstractController> {
	
	private E controllerAlvo;

	public EventoController(E controllerAlvo) {
		this.controllerAlvo = controllerAlvo;
	}
	
	public E target() {
		return controllerAlvo;
	}
	
}
