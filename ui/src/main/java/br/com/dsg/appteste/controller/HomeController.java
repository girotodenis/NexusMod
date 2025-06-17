package br.com.dsg.appteste.controller;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import br.com.dsg.appteste.views.HomeView;
import br.com.dsg.principal.controller.AtualizarConteudoEvento;
import br.com.dsg.principal.controller.EventAtualizarProgressBar;
import br.com.dsg.principal.controller.EventVoltarController;
import br.com.dsg.swing.controller.AbstractController;

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

	private int iValor = 1;

	public HomeController(AbstractController<?> controlerPai) {
		super(controlerPai, new HomeView());

		registerEventListener(MeuEvento.class, (event) -> System.out.print(event.toString()));

		registerAction(getPanel().getBotao(), () -> {
			LOG.info("action");
			SwingWorker<Boolean, Object> exec = new SwingWorker<Boolean, Object>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					for (int i = 0; i < 100; i++) {
						try {
							Thread.sleep(10);
							fireEvent(new EventAtualizarProgressBar(i, 99));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return Boolean.TRUE;
				}
				
				protected void done() {
					try {
						Boolean resultado = get();
						if(resultado) {
							
							fireEvent(new MeuEvento("Teste " + (iValor++), getPanel().getTarget()));
//							fireEvent(new EventAtualizarProgressBar(iValor,11));
							if(iValor == 10) {
								iValor = 1;
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			
			exec.execute();
			
			
		});

		registerAction(getPanel().getBotao2(), () -> {
			
			fireEvent(new AtualizarConteudoEvento("Configuração", new ConfigController(null)));
		
		});

		registerAction(getPanel().getBotao3(), () -> {
			fireEvent(new EventAtualizarProgressBar(iValor,11));
			fireEvent(new AtualizarConteudoEvento("Configuração", new ConfigController(this)));
		});
		
		/*
		 * Registra acao do que vai acontecer se outra tela voltar para essa
		 */
		registerEventListener(EventVoltarController.class, (event) -> {
			
			if(event.getValorDeCallback()!=null) {
				LOG.info(event.getValorDeCallback());
			}
		});

	}

	

}
