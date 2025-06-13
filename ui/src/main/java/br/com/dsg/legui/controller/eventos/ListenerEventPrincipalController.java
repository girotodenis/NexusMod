package br.com.dsg.legui.controller.eventos;

import org.apache.log4j.Logger;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ControllerEventListener;
import br.com.dsg.legui.controller.LeGuiEventos;

public class ListenerEventPrincipalController implements ControllerEventListener<EventPrincipalController> {

	private final static Logger LOG = Logger.getLogger(ListenerEventPrincipalController.class);
	private AbstractController<?> principal;

	public ListenerEventPrincipalController(AbstractController<?> primeiro) {
		super();
		this.principal = primeiro;
	}

	@Override
	public void handleEvent(EventPrincipalController event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		AbstractController<?> controllerAtual = event.getControllerAtual();
		controllerAtual.cleanUp();
		LeGuiEventos.irPara(principal);
	}
}
