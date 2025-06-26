package com.dsg.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsg.nexusmod.controller.AbstractEventListener;

public class ContextApp {

	private Map<String, List<AbstractEventListener<?>>> eventListeners = new HashMap<String, List<AbstractEventListener<?>>>();

	private static ContextApp instance = new ContextApp();

	private ContextApp() {
	}

	public static ContextApp getInstance() {
		return instance;
	}

	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	public <T> void fireEvent(T date) {
		fireEvent(date.getClass().getName(), date);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void fireEvent(String event, T date) {
		System.out.println("####################################");
		System.out.println("fire: "+event+" "+eventListeners.containsKey(event));
		if (eventListeners.get(event) != null) {
			for (AbstractEventListener eventListener : eventListeners.get(event)) {
				System.out.println("listener : "+eventListener.getClass().getName());
				eventListener.handleEvent(date);
				System.out.println("####################################");
			}
		}
	}

	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	public <T> void registerEvent(Class<T> event, AbstractEventListener<T> eventListener) {
		registerEvent(event.getName(), eventListener);
	}
	
	public <T> void registerEvent(String event, AbstractEventListener<T> eventListener) {
		List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(event);
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<AbstractEventListener<?>>();
		}
		listenersForEvent.clear();
		listenersForEvent.add(eventListener);
		
		eventListeners.put(event, listenersForEvent);
		System.out.println(" addEvent: "+event+" "+listenersForEvent.size());
	}

	public void removeEvent(String event) {
		if(eventListeners.containsKey(event)) {
			List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(event);
			listenersForEvent.clear();
			eventListeners.remove(event);
			System.out.println(" removeEvent: "+event);
		}
	}

}
