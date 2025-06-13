package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.ControllerEventListener;

public class ListenerEventProgressBar implements ControllerEventListener<EventProgressBar> {

	public ListenerEventProgressBar() {
		super();
	}
	
	@Override
	public void handleEvent(EventProgressBar event) {
		event.getControllerAlvo().getPanel().setValueProgressBar(event.getValor());
	    if(event.getValor()>=100) {
	    	event.getControllerAlvo().getPanel().setValueProgressBar(0);
	    }
	}
}
