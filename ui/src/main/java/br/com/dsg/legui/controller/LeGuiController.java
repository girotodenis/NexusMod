package br.com.dsg.legui.controller;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Panel;

import br.com.dsg.legui.controller.eventos.EventAbrirFecharMenu;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import br.com.dsg.legui.controller.eventos.EventAtualizarConteudoEvento;
import br.com.dsg.legui.controller.eventos.EventLoginApp;
import br.com.dsg.legui.controller.eventos.EventPrincipalController;
import br.com.dsg.legui.controller.eventos.EventProgressBar;
import br.com.dsg.legui.controller.eventos.EventVoltarController;
import br.com.dsg.legui.controller.eventos.ListenerEventAbrirFecharMenu;
import br.com.dsg.legui.controller.eventos.ListenerEventAdicionarItemMenu;
import br.com.dsg.legui.controller.eventos.ListenerEventAtualizarConteudo;
import br.com.dsg.legui.controller.eventos.ListenerEventLoginApp;
import br.com.dsg.legui.controller.eventos.ListenerEventPrincipalController;
import br.com.dsg.legui.controller.eventos.ListenerEventProgressBar;
import br.com.dsg.legui.controller.eventos.ListenerEventVoltarController;
import br.com.dsg.legui.controller.seguranca.SeguracaController;
import br.com.dsg.legui.controller.seguranca.UsuarioPrincipal;

/**
 * @author Denis Giroto
 *
 */
public class LeGuiController extends AbstractController<LeGuiView> {

	private final static Logger LOG = Logger.getLogger(LeGuiController.class);
	
	private Boolean menuFechado = Boolean.FALSE;
	private Boolean removerMenu = Boolean.FALSE;
	
	private  boolean appFinalizado = false;
	
	private static LeGuiController instance;
	

	/**
	 * 
	 */
	protected LeGuiController(LeGuiView leGuiView) {
		super(leGuiView);
		instance = this;
		
		registerControllerEventListener(EventAbrirFecharMenu.class, new ListenerEventAbrirFecharMenu());
		registerControllerEventListener(EventAtualizarConteudoEvento.class, new ListenerEventAtualizarConteudo());
		registerControllerEventListener(EventVoltarController.class, new ListenerEventVoltarController());
		registerControllerEventListener(EventProgressBar.class, new ListenerEventProgressBar());
		registerControllerEventListener(EventAdicionarItemMenu.class, new ListenerEventAdicionarItemMenu());
		
//		loadMenuFechado();
	}

	public static LeGuiController get() {
		return instance;
	}
	
	public LeGuiController addItemMenu(EventAdicionarItemMenu itemMenu) {
		ExecutarEvento.get().lancar( itemMenu );
		return this;
	}

	public <T extends SeguracaController<? extends UsuarioPrincipal,? extends Panel>> LeGuiController autenticacao(GerarController<T> cController ) {
		final SeguracaController<? extends UsuarioPrincipal,? extends Panel> controllerSeguranca= cController.criar(LeGuiController.this);
		controllerSeguranca.setNomeController("SeguracaController_"+System.currentTimeMillis());
		
		LeGuiController.this.removerMenu = true;
		
		registerControllerEventListener(EventLoginApp.class, new ListenerEventLoginApp( controllerSeguranca ));
		return this;
	}
	
	public <T extends AbstractController<?>> LeGuiController controllerPrincipall(GerarController<T> cController ) {
		final AbstractController<?> controller = cController.criar(LeGuiController.this);
		
		LeGuiEventos.get().empilhar( controller  );
		registerControllerEventListener(EventPrincipalController.class, new ListenerEventPrincipalController( controller ));
		return this;
	}
	

	/**
	 * @return
	 */
	protected void loadController() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
					
					ExecutarEvento.get().lancar(
							new EventAbrirFecharMenu(LeGuiController.this.menuFechado, LeGuiController.this.removerMenu)
					).executar();
					
					if( LeGuiController.this.removerMenu ) {
						LeGuiController.this.getPanel().getMenu().esconder();
					}
						
				} catch (Exception e) {
				}
			}
		});
	}
	
	public LeGuiController menuAbrirFecharMenu() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ExecutarEvento.get().lancar(
							new EventAbrirFecharMenu()
					).executar();
					
				} catch (Exception e) {}
			}
		});
		return this;
	}

	public void menuFechado() {
		this.menuFechado = Boolean.TRUE;
	}
	
	public Boolean getMenuFechado() {
		return menuFechado;
	}

	public void setMenuFechado(Boolean menuFechado) {
		this.menuFechado = menuFechado;
	}

	protected boolean isAppFinalizado() {
		return appFinalizado;
	}
	
	public void setAppFinalizado(boolean appFinalizado) {
		this.appFinalizado = appFinalizado;
	}

}
