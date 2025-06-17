package br.com.dsg.swing.controller;

/**
 * @author Denis Giroto
 * 
 * Contrato para componentes com a capacidade de definir tratamento adequado para um evento.
 * 
 * <p>
 *  Em conjunto com <code>AbstractController</code> e <code>AbstractEvent</code>, esse componente 
 *  é parte do trecho que implementa o design pattern <strong>Observer</strong>.
 * </p>
 * 
 * <p><code>AbstractEventListener</code> atua como <i>observador</i>.</p>
 * 
 *
 * @param <M> tipo do Evento que deverá ser tratado.
 */
public interface AbstractEventListener<M> {

	 public void handleEvent(M event);

}
