package br.com.dsg.legui.controller.eventos;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Dialog;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ControllerEventListener;
import br.com.dsg.legui.controller.ExecutarEvento;
import br.com.dsg.legui.controller.LeGuiController;
import br.com.dsg.legui.controller.LeGuiEventos;
import br.com.dsg.legui.controller.seguranca.SeguracaController;
import br.com.dsg.legui.controller.seguranca.Sessao;
import br.com.dsg.legui.controller.seguranca.TemPermissao;
import br.com.dsg.legui.controller.seguranca.UsuarioPrincipal;

public class ListenerEventLoginApp implements ControllerEventListener<EventLoginApp> {

	private final static Logger LOG = Logger.getLogger(ListenerEventLoginApp.class);
	
	private SeguracaController<? extends UsuarioPrincipal,? extends Panel> controllerSeguranca;
	
	public ListenerEventLoginApp(SeguracaController<? extends UsuarioPrincipal,? extends Panel> controllerSeguranca) {
		super();
		Sessao.get().setLoginAtivo(true);
		this.controllerSeguranca=controllerSeguranca;
	}

	@Override
	public void handleEvent(EventLoginApp event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		
		AbstractController<?> newController = event.getNewController();
		LOG.debug(String.format("newController %s", newController.getClass().getSimpleName()) );
		if(!Sessao.get().isAutenticado()) {
			controllerSeguranca.setCallback((usuario)->{
				if(Sessao.get().isAutenticado()) {
					LeGuiEventos.removerMenu(LeGuiController.get().getMenuFechado(), false);
					validarPermissao(usuario, newController);
				}
			});
			LOG.debug(String.format("indo para {%s}", controllerSeguranca.getClass().getSimpleName()) );
			event.getControllerAlvo().getPanel().addPanel(controllerSeguranca.getPanel());
		}else {
			if(Sessao.get().isLoginAtivo()) {
				validarPermissao(Sessao.get().getUsuario(), newController);
			}else {
//				LeGuiEventos.irPara(newController);
				ExecutarEvento.get().lancar(new EventAtualizarConteudoEvento(newController,true)).executar();
			}
		}
		
	}

	public void validarPermissao(UsuarioPrincipal usuario, AbstractController<?> newController) {
		Class<?> classeDoRecurso = newController.getClass();
		TemPermissao temPermissao = classeDoRecurso.getAnnotation(TemPermissao.class);
		
		if(temPermissao!=null) {
			
			if(usuario!=null && usuario.temPermissao(temPermissao.value())) {
//				LeGuiEventos.irPara( newController );
				ExecutarEvento.get().lancar(new EventAtualizarConteudoEvento(newController,true)).executar();
			}else {
				Dialog dialog = new Dialog("Sem permissão", 500, 100);
				dialog.setDraggable(false);
				dialog.setResizable(false);
				Label erro = new Label(String.format("Usuario %s sem a permissão \"%s\"", usuario.usuario(), temPermissao.value()), 10, 10, 500, 20);
				dialog.getContainer().add(erro);
				dialog.show(LeGuiController.get().getPanel().getFrame());
			}
		}else {
			
			ExecutarEvento.get().lancar(new EventAtualizarConteudoEvento(newController,true)).executar();
		}
	}
}
