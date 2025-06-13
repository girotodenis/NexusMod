package br.com.dsg.legui.controller.eventos;

import org.apache.log4j.Logger;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ControllerEventListener;
import br.com.dsg.legui.controller.ExecutarEvento;
import br.com.dsg.legui.controller.LeGuiController;

public class ListenerEventVoltarController implements ControllerEventListener<EventVoltarController> {

	private final static Logger LOG = Logger.getLogger(ListenerEventVoltarController.class);
	public ListenerEventVoltarController() {
		super();
	}
	
	@Override
	public void handleEvent(EventVoltarController event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		
		AbstractController<?> controllerAtual = event.getController();
		AbstractController<?> controllerAnterior = event.getController().getControllerPai();
		
		if (controllerAnterior == null || controllerAnterior instanceof LeGuiController) {
			
			ExecutarEvento.get().lancar(
					new EventPrincipalController(controllerAtual)
			).executar();
			return;
		}
		
		if(controllerAnterior!=null && controllerAnterior.getPanel()!=null ){
			controllerAnterior.remove(controllerAtual);
			
			ExecutarEvento.get().lancar(
					new EventAtualizarConteudoEvento(controllerAnterior)
			).executar();
			
		}
	}
}
