package br.com.dsg.legui.controller;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

public class ExecutarEvento {
	
	private static ExecutarEvento instance = new ExecutarEvento();
	
	private ExecutarEvento() {
	}
	
	private static Logger LOG = Logger.getLogger(ExecutarEvento.class);

	private Queue<EventoController<?>> primario = new ConcurrentLinkedQueue<>();
	private Queue<EventoController<?>> secundario = new ConcurrentLinkedQueue<>();

	public void executar() {
		swap();
		System.out.println("ExecutarEvento.executar() - " + primario.size() + " eventos");
		for (EventoController<?> evento = secundario.poll(); evento != null; evento = secundario.poll()) {
			AbstractController<?> targetComponent = evento.getControllerAlvo();
			if (targetComponent == null) {
				return;
			}
			List<? extends ControllerEventListener<?>> lista = targetComponent.getEventosControllers().get(evento.getClass().getName());
			if(lista!=null) {
				for (ControllerEventListener listener : lista) {
					System.out.println(listener.getClass().getName() + " - " + evento.getClass().getName());
					listener.handleEvent(evento);
				}
			}else {
				LOG.error(String.format("Evento %s sem listeners cadastrados", evento.getClass().getName()));
			}
		}
	}

	private void swap() {
		Queue<EventoController<?>> temp = primario;
		primario = secundario;
		secundario = temp;
	}

	public ExecutarEvento lancar(EventoController<?> event ) {
		primario.add(event);
		return this;
	}
	
	public static ExecutarEvento get() {
		return instance;
	}
	
}
