package com.dsg.nexusmod.configuracao.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Dialog;
import org.liquidengine.legui.component.Label;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ExecutarEvento;
import br.com.dsg.legui.controller.LeGuiEventos;
import br.com.dsg.legui.controller.LeGuiView;
import br.com.dsg.legui.controller.eventos.EventAtualizarConteudoEvento;
import br.com.dsg.legui.controller.eventos.EventProgressBar;

/**
 * @author Denis Giroto
 *
 */
public class HomeController extends AbstractController<HomeView> {

	private final static Logger LOG = Logger.getLogger(HomeController.class);

	class MeuEvento {

		public MeuEvento(String valor, JPanel target) {
			((JLabel) target.getComponent(0)).setText(valor);
		}

	}


	public HomeController(AbstractController<?> controlerPai) {
		super(controlerPai, new HomeView());

		registerAction(getPanel().botao1, (event) -> LeGuiEventos.irPara( new ConfigController(this) ));

		registerTarefa(getPanel().botao2, (event) -> {
			try {
				for (int i = 0; i <= 100; i++) {
					Thread.sleep(50);
					LeGuiEventos.atualizarProgressBar( i );
				}
				Thread.sleep(500);
			} catch (Exception e) {
			}
		});

		registerAction(getPanel().botao3, (event) -> {
			Dialog dialog = new Dialog("Erro", 300, 100);
			dialog.setDraggable(false);
			dialog.setResizable(false);
			Label erro = new Label("botão 3", 10, 10, 300, 20);
			dialog.getContainer().add(erro);
			dialog.show(getPanel().getFrame());
		});
		
		
		registerAction(getPanel().botao4, (event) -> {
			((LeGuiView) getControllerPai().getPanel()).getMenu().esconder();
		});
		
		registerAction(getPanel().botao5, (event) -> {
			((LeGuiView) getControllerPai().getPanel()).getMenu().exibir();
		});


	}

}
