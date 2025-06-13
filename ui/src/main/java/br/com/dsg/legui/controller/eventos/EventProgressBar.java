package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventProgressBar extends EventoController<LeGuiController>{
	
	private float valor;

	public EventProgressBar(float valor) {
		super(LeGuiController.get());
		this.valor = valor;
	}

	public float getValor() {
		return valor;
	}

	
}
