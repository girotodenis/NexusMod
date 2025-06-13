package br.com.dsg.legui.controller;

public class EventoController<E extends AbstractController<?>> {
	
	private E controllerAlvo;

	public EventoController(E controllerAlvo) {
		this.controllerAlvo = controllerAlvo;
	}
	
	public E getControllerAlvo() {
		return controllerAlvo;
	}
	
}
