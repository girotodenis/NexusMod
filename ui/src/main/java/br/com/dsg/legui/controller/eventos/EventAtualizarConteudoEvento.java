package br.com.dsg.legui.controller.eventos;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventAtualizarConteudoEvento extends EventoController<LeGuiController>{
	
	private String nome;
	private AbstractController<?> controller;
	private boolean ignoreAutenticacao = false;
	
	public EventAtualizarConteudoEvento(AbstractController<?> controller) {
		this(controller, false);
	}
	
	public EventAtualizarConteudoEvento(AbstractController<?> controller, boolean ignoreAutenticacao) {
		super(LeGuiController.get());
		this.nome = controller.getNomeController();
		this.controller = controller;
		this.ignoreAutenticacao = ignoreAutenticacao;
	}

	public String getNome() {
		return nome;
	}

	public AbstractController<?> getController() {
		return controller;
	}

	public boolean isIgnoreAutenticacao() {
		return ignoreAutenticacao;
	}

		
}
