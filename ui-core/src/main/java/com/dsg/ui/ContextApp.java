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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void fireEvent(String event, T date) {
		System.out.println("Firing event: " + event + " with data: " + date);
		if (eventListeners.get(event) != null) {
			System.out.println("Found listeners for event: " + event);
			for (AbstractEventListener eventListener : eventListeners.get(event)) {
				// SwingUtilities.invokeLater(() -> {
				eventListener.handleEvent(date);
				// });
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
	public <T> void registerEvent(String event, AbstractEventListener<T> eventListener) {
		System.out.println("Registering event listener for event: " + event);
		List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(event);
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<AbstractEventListener<?>>();
		}
		listenersForEvent.clear();
		listenersForEvent.add(eventListener);
		eventListeners.put(event, listenersForEvent);
		System.out.println(
				"Registered event listener for event: " + event + " with listener: " + listenersForEvent.size());
	}

}
