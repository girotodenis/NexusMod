package br.com.dsg.principal.controller;

import br.com.dsg.swing.controller.AbstractController;

public class AtualizarConteudoEvento {
	
	private String nome;
	private AbstractController<?> controller;
	
	public AtualizarConteudoEvento(String nomeItem, AbstractController<?> controller) {
		this.nome = nomeItem;
		this.controller = controller;
	}
	
	public AtualizarConteudoEvento(AbstractController<?> controller) {
		this.nome = controller.getNomeController();
		this.controller = controller;
	}

	public String getNome() {
		return nome;
	}

	public AbstractController<?> getController() {
		return controller;
	}
	
	

}
