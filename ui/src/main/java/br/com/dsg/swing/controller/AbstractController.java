package br.com.dsg.swing.controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.dsg.swing.controller.action.AbstractAction;
import br.com.dsg.swing.controller.action.Action;

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
public abstract class AbstractController<T> implements ActionListener, WindowListener {

	private static Logger LOG = Logger.getLogger(AbstractController.class);

	private AbstractController<?> controllerPai;

	private T panel;

	private String nomeController;;

	private java.util.List<AbstractController<?>> subControllers = new ArrayList<AbstractController<?>>();

	private Map<String, Action> actions = new HashMap<String, Action>();

	private Map<String, List<AbstractEventListener<?>>> eventListeners = new HashMap<String, List<AbstractEventListener<?>>>();

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
	 * Registra uma <code>acao</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	protected  void registerAction(JButton source, AbstractAction action) {
		registerAction((AbstractButton) source, action);
	}
	
	protected  void registerAction(AbstractButton source, AbstractAction action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (Button) sem acao definida!");
		}
		LOG.debug("Registrando action: " + action.getClass().getName() + " para o botao: " + source.getText());
		source.addActionListener(this);
		this.actions.put(source.getActionCommand(), action);
	}

	/**
	 * Registra uma <code>acao</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	protected void registerAction(JButton source, Action action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (Button) sem acao definida!");
		}
		LOG.debug("Registrando action: " + action.getClass().getName() + " para o botao: " + source.getLabel());
		source.addActionListener(this);
		this.actions.put(source.getActionCommand(), action);
	}

	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> void fireEvent(T event) {
		if (eventListeners.get(event.getClass().getName()) != null) {
			for (AbstractEventListener eventListener : eventListeners.get(event.getClass().getName())) {
				LOG.debug("Evento: " + event.getClass().getSimpleName() + " com listener: "
						+ eventListener.getClass().getName());
				eventListener.handleEvent(event);
			}
		}
		if (controllerPai != null)
			controllerPai.fireEvent(event);
	}

	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	protected <T> void registerEventListener(Class<T> eventClass, AbstractEventListener<T> eventListener) {
		LOG.debug("Registrando listener: " + eventListener + " para o evento: " + eventClass.getName());
		java.util.List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(eventClass.getName());
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<AbstractEventListener<?>>();
		}
		listenersForEvent.add(eventListener);
		eventListeners.put(eventClass.getName(), listenersForEvent);
	}

	protected Action getAction(ActionEvent actionEvent) {

		String actionCommand = null;
		if (actionEvent.getSource() instanceof AbstractButton) {

			AbstractButton button = (AbstractButton) actionEvent.getSource();
			actionCommand = button.getActionCommand();

		} else if (actionEvent.getSource() instanceof Button) {

			Button button = (Button) actionEvent.getSource();
			actionCommand = button.getActionCommand();

		} else {

			return null;
		}
		return actions.get(actionCommand);
	}

	public void actionPerformed(ActionEvent actionEvent) {
		try {

			Action action = getAction(actionEvent);

			if (action != null) {
				LOG.debug("Executando action: " + action.getClass());
				try {
					action.executar();
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		} catch (ClassCastException e) {
			handlerException(
					new IllegalArgumentException("Action source não é um Abstractbutton/Button: " + actionEvent));
		}
	}

	/**
	 * Caso ocorra alguma falha durante a <code>ação</code> apresenta uma mensagem.
	 * 
	 * @param ex
	 */
	protected void handlerException(Exception ex) {

		JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public AbstractController<?> getControllerPai() {
		return controllerPai;
	}

	/**
	 * Método utilizado para liberar recursos carregados pela
	 * <code>Controller</code>.
	 */
	protected void cleanUp() {
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

	public void windowOpened(WindowEvent windowEvent) {
	}

	public void windowClosed(WindowEvent windowEvent) {
	}

	public void windowIconified(WindowEvent windowEvent) {
	}

	public void windowDeiconified(WindowEvent windowEvent) {
	}

	public void windowActivated(WindowEvent windowEvent) {
	}

	public void windowDeactivated(WindowEvent windowEvent) {
	}

}
