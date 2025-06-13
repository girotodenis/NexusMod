package br.com.dsg.legui.controller;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Dialog;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseClickEvent.MouseClickAction;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;


/**
 * @author Denis Giroto
 * 
 *         Classe abstrata que define uma estrutura para componentes da camada
 *         controller do padrão arquitetural MVC.
 * 
 *         <p>
 *         <code>Controller</code> e o componente intermediario entre a
 *         apresentacao (View) e os componentes de negocio (Servicos + DAO +
 *         Model).
 *         </p>
 * 
 *         <p>
 *         Habilita:
 *         </p>
 *         <ul>
 *         <li>Definicao de <code>eventos</code> e <code>acoes</code> para os
 *         componentes graficos.</li>
 *         <li>Apresentar mensagens de erros gerados em <code>acoes</code>dos
 *         componentes graficos.</li>
 *         <li>Liberar recursos do componente no encerramento da janela.</li>
 *         </ul>
 * 
 *
 */
public abstract class AbstractController<T extends Panel> {

	private static Logger LOG = Logger.getLogger(AbstractController.class);

	private AbstractController<?> controllerPai;

	private T panel;

	private String nomeController;
	
	private java.util.List<AbstractController<?>> subControllers = new ArrayList<AbstractController<?>>();

	private Map<String, List<ControllerEventListener<?>>> eventosControllers = new HashMap<String, List<ControllerEventListener<?>>>();

	public AbstractController(T frame) {
		this.panel = frame;
	}

	/**
	 * Controller possui um auto-relacionamento, Sutil em situacoes aonde uma
	 * hierarquia de controladores deve ser respeitada.
	 * 
	 * @param controllerPai controller <i>pai</i>
	 */
	public AbstractController(AbstractController<?> controllerPai, T panel) {
		this.panel = panel;
		if (controllerPai != null) {
			this.controllerPai = controllerPai;
			this.controllerPai.subControllers.add(this);
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param action
	 */
	protected <E extends Event> void registerPanelListener(Class<E> eventClass, EventListener<E> listener) {
		this.panel.getListenerMap().addListener(eventClass, listener); 
	}
	
	
	/**
	 * Registra uma <code>acao</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	protected <E extends Event> void registerEventListener(Component source, Class<E> eventClass, EventListener<E> listener) {
		LOG.debug("Registrando action: " + eventClass.getName() + " para o botao: " + source.toString());
		source.getListenerMap().addListener(eventClass, listener); 
	}
	
	/**
	 * Registra uma <code>acao</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	protected void registerTarefa(Component source, Action acao) {
		LOG.debug("Registrando action para o botao: " + source.toString());
		source.getListenerMap().addListener(MouseClickEvent.class, new ActionClickListener( this, source, acao) ); 
	}
	/**
	 * Registra uma <code>acao</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	public void registerAction(Component source, Action acao) {
		System.out.println("Registrando action para o MouseButton1: " + source.toString());
		
		source.getListenerMap().addListener(MouseClickEvent.class, (event)->{
			
			System.out.println(String.format("Executando  action %s ", event.getAction() ));
			if(event.getAction().equals(MouseClickAction.CLICK) && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)) {
				try {
					System.out.println(String.format("click %s detalhe %s", source.toString() , event.toString()));
					acao.executar(event);
				} catch (Exception e) {
					handlerException(e);
				}
			}
		}); 
	}
	
	public void registerActionMouseButton2(Component source, Action acao) {
		LOG.debug("Registrando action para o MouseButton2: " + source.toString());
		
		source.getListenerMap().addListener(MouseClickEvent.class, (event)->{
			
			LOG.debug(String.format("Executando  action %s ", event.getAction() ));
			if(event.getAction().equals(MouseClickAction.CLICK) && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_2)) {
				try {
					LOG.debug(String.format("click %s detalhe %s", source.toString() , event.toString()));
					acao.executar(event);
				} catch (Exception e) {
					handlerException(e);
				}
			}
		}); 
	}

	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param class1    tipo do evento
	 * @param eventAbrirFecharMenu tratador (<code>listener</code>) do evento
	 */
	public void registerControllerEventListener(Class<?> class1, ControllerEventListener<?>... listeners) {
		LOG.debug("Registrando " + listeners.length + " listeners para o evento: " + class1.getName());
		java.util.List<ControllerEventListener<?>> listenersForEvent = eventosControllers.get(class1.getName());
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<ControllerEventListener<?>>();
		}
		
		for (ControllerEventListener<?> controllerEventListener : listeners) {
			listenersForEvent.add(controllerEventListener);
		}
		
		eventosControllers.put(class1.getName(), listenersForEvent);
	}


	/**
	 * Caso ocorra alguma falha durante a <code>ação</code> apresenta uma mensagem.
	 * 
	 * @param ex
	 */
	public void handlerException(Exception ex) {
		
		Dialog dialog = new Dialog("Erro", 300, 100);
		dialog.setDraggable(false);
		dialog.setResizable(false);
		Label erro = new Label(ex.getLocalizedMessage(), 10, 10, 300, 20);
		dialog.getContainer().add(erro);
		dialog.show(this.panel.getFrame());
		ex.printStackTrace();
	}

	public AbstractController<?> getControllerPai() {
		return controllerPai;
	}

	/**
	 * Método utilizado para liberar recursos carregados pela
	 * <code>Controller</code>.
	 */
	public void cleanUp() {
		LOG.debug("Liberando recursos do controller " + this.getClass().getName());

		for (AbstractController<?> subController : subControllers) {
			subController.cleanUp();
		}
	}

	/**
	 * Método utilizado para liberar recursos carregados pela
	 * <code>Controller</code>.
	 */
	public void remove(AbstractController<?> controller) {
		LOG.debug("Liberando recursos do controller " + controller.getClass().getName());
		LOG.debug("controller " + controller.subControllers.size());
		LOG.debug("subControllers " + subControllers.size());
		controller.cleanUp();
		subControllers.remove(controller);
		LOG.debug("controller " + controller.subControllers.size());
		LOG.debug("subControllers " + subControllers.size());
	}

	public void windowClosing(WindowEvent windowEvent) {
		cleanUp();
	}

	public T getPanel() {
		return panel;
	}

	public String getNomeController() {
		return nomeController;
	}

	public void setNomeController(String nomeController) {
		this.nomeController = nomeController;
	}

	protected Map<String, List<ControllerEventListener<?>>> getEventosControllers() {
		return eventosControllers;
	}
	
	

}
