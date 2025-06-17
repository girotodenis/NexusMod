package com.dsg.ui.controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.dsg.nexusmod.ui.Action;



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
public abstract class AbstractController<T> implements ActionListener {


	private AbstractController<?> controllerPai;

	private T panel;

	private String nomeController;;

	private List<AbstractController<?>> subControllers = new ArrayList<AbstractController<?>>();

	private Map<String, Action> actions = new HashMap<String, Action>();

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
	protected void registerAction(JButton source, Action action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (Button) sem acao definida!");
		}
		source.addActionListener(this);
		this.actions.put(source.getActionCommand(), action);
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
		for (AbstractController<?> subController : subControllers) {
			subController.cleanUp();
		}
	}

	/**
	 * Método utilizado para liberar recursos carregados pela
	 * <code>Controller</code>.
	 */
	public void remove(AbstractController<?> controller) {
		controller.cleanUp();
		subControllers.remove(controller);
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

}
