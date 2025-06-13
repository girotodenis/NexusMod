package br.com.dsg.legui.controller.eventos;

import org.apache.log4j.Logger;

import br.com.dsg.legui.componentes.MenuLeGui;
import br.com.dsg.legui.controller.ControllerEventListener;

public class ListenerEventAbrirFecharMenu implements ControllerEventListener<EventAbrirFecharMenu> {

	private final static Logger LOG = Logger.getLogger(ListenerEventAbrirFecharMenu.class);
	
	public ListenerEventAbrirFecharMenu() {
		super();
	}
	
	@Override
	public void handleEvent(EventAbrirFecharMenu event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		
		MenuLeGui menu = event.getControllerAlvo().getPanel().getMenu();
		if(event.removerMenu || menu.isEmpty()) {
			LOG.info("esconder menu");
			menu.esconder();
		}else {
			if(event.fechar!=null) {
				event.getControllerAlvo().setMenuFechado(event.fechar);
				if(event.fechar) {
					LOG.info("encolher menu");
					menu.encolherItens();
				}else {
					LOG.info("expandir menu");
					menu.expandirItens();
				}
			}else {
				boolean abrir = !menu.isAberto();
				event.getControllerAlvo().setMenuFechado(!abrir);
				if (abrir) {
					LOG.info("expandir menu");
					menu.expandirItens();
				} else {
					LOG.info("expandir menu");
					menu.encolherItens();
				}
			}
		}
		menu.update();
	}
}
