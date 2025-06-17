package br.com.dsg.appteste.controller;

import org.apache.log4j.Logger;

import br.com.dsg.appteste.views.ConfView;
import br.com.dsg.principal.controller.EventVoltarController;
import br.com.dsg.swing.controller.AbstractController;

/**
 * @author Denis Giroto
 *
 */
public class ConfigController extends AbstractController<ConfView> {

	private final static Logger LOG = Logger.getLogger(ConfigController.class);


	public ConfigController(AbstractController<?> controlerPai) {
		super(controlerPai, new ConfView());

		if(controlerPai!=null && controlerPai.getPanel()!=null )
			registerAction(getPanel().getBotao(), ()-> fireEvent(new EventVoltarController(this, "Voltei do ConfigController"))
			);
		else
			getPanel().getBotao().setEnabled(false);
	}
	
	

}
