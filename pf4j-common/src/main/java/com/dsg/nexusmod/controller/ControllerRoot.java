package com.dsg.nexusmod.controller;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dsg.nexusmod.ui.TaskNotificationType;

public interface ControllerRoot  {
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	<T> void fireEvent(T event);
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	<T> void fireEvent(String event, T date );
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	<T> void menuEvent(String menu, String event, T date );
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	<T> void registerEvent(Class<T> eventClass, AbstractEventListener<T> eventListener);
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	<T> void registerEvent(String event, AbstractEventListener<T> eventListener);
	
	
	/**
	 * @param menuItem
	 */
	void addMenuItem(MenuItem menuItem);
	
	void showContent(Controller<? extends JPanel> controller);
	 
	void addNotification(String message, TaskNotificationType type);

	void removeMenu(String string);

	JFrame getFrame();

}
