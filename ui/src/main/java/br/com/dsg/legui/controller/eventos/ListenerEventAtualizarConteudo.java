package br.com.dsg.legui.controller.eventos;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Panel;

import br.com.dsg.legui.componentes.ItemMenu;
import br.com.dsg.legui.componentes.MenuLeGui;
import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ControllerEventListener;
import br.com.dsg.legui.controller.ExecutarEvento;
import br.com.dsg.legui.controller.LeGuiEventos;
import br.com.dsg.legui.controller.LeGuiView;
import br.com.dsg.legui.controller.seguranca.Sessao;

public class ListenerEventAtualizarConteudo implements ControllerEventListener<EventAtualizarConteudoEvento> {

	private final static Logger LOG = Logger.getLogger(ListenerEventAtualizarConteudo.class);
	
	public ListenerEventAtualizarConteudo() {
	}

	@Override
	public void handleEvent(EventAtualizarConteudoEvento event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		AbstractController<?> newController = event.getController();
		Panel newView = newController.getPanel();
		
		LeGuiView leGuiView = event.getControllerAlvo().getPanel();
		MenuLeGui menu = leGuiView.getMenu();
		
		if(leGuiView.getConteudoLeGui().getChildComponents().size()>0 && leGuiView.getConteudoLeGui().getChildComponents().get(0).getClass().equals(newView.getClass())){
			LOG.info(String.format(" tela {%s} já está ativa ", newView.getClass().getSimpleName() ));
			return;
		}
		
		if(Sessao.get().isLoginAtivo() && ( !event.isIgnoreAutenticacao() || !Sessao.get().isAutenticado())) {
			LOG.info(String.format("indo para login volta para controller {%s} ", newController.getClass().getSimpleName()));
			ExecutarEvento.get()
					.lancar(
							new EventLoginApp(newController)
					).executar();
			return;
		}
		
		
		LOG.debug(String.format("Atualizar tela {%s} do controller {%s} ", newView.getClass().getSimpleName(), newController.getClass().getSimpleName()));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i <= 100; i++) {
					try {
						Thread.sleep(1);
						LeGuiEventos.atualizarProgressBar( i );
					} catch (Exception e) {
					}
				}
			}
		});
		
		
		if(menu.isEmpty()) {
			menu.esconder();
		}
		
		ItemMenu itemMenu = menu.getItem(newView.getClass());
		if(itemMenu!=null) {
			menu.seleciona(itemMenu);
		}
		
		leGuiView.addPanel(newView);
		
	}
}
