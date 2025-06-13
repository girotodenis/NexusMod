package br.com.dsg.legui.controller;

import org.apache.log4j.Logger;

import br.com.dsg.legui.controller.eventos.EventAbrirFecharMenu;
import br.com.dsg.legui.controller.eventos.EventAtualizarConteudoEvento;
import br.com.dsg.legui.controller.eventos.EventProgressBar;

public class LeGuiEventos {
	
	private static Logger LOG = Logger.getLogger(LeGuiEventos.class);
    private static LeGuiEventos instance = new LeGuiEventos();
	
	private LeGuiEventos() {
	}
	
	public static LeGuiEventos get() {
		return instance;
	}
	
	public LeGuiEventos empilhar(AbstractController<?>... controllers) {
		for (AbstractController<?> controller : controllers) {
			ExecutarEvento.get().lancar(new EventAtualizarConteudoEvento(controller));
		}
		return LeGuiEventos.this;
	}
	
	public void executar() {
		ExecutarEvento.get().executar();
	}
	

	public static void atualizarProgressBar(float valor) {
		ExecutarEvento.get().lancar(new EventProgressBar(valor)).executar();
	}

	public static void irPara(AbstractController<?> controller) {
		LOG.info(String.format("irPara -> %s", controller.getClass().getSimpleName()));
		ExecutarEvento.get().lancar(new EventAtualizarConteudoEvento(controller)).executar();;
	}
	
	public static void removerMenu(Boolean fechar, Boolean remover) {
		ExecutarEvento.get().lancar(new EventAbrirFecharMenu(fechar,remover)).executar();
	}
}
