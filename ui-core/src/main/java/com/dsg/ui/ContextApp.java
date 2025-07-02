package com.dsg.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.AbstractEventListener;

public class ContextApp {

	private static final Logger log = LoggerFactory.getLogger(ContextApp.class);
	
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
		log.trace("####################################");
		log.trace("fire: {} {}", event, eventListeners.containsKey(event));
		if (eventListeners.get(event) != null) {
			for (AbstractEventListener eventListener : eventListeners.get(event)) {
				log.trace("listener : {}", eventListener.getClass().getName());
				eventListener.handleEvent(date);
				log.trace("####################################");
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
		log.trace(" addEvent: {} {} ", event, listenersForEvent.size());
	}

	public void removeEvent(String event) {
		if(eventListeners.containsKey(event)) {
			List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(event);
			listenersForEvent.clear();
			eventListeners.remove(event);
			log.trace(" removeEvent: {}", event);
		}
	}

}
