package br.com.dsg.legui.controller.eventos;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.liquidengine.legui.component.Dialog;

import br.com.dsg.legui.componentes.ItemMenu;
import br.com.dsg.legui.componentes.MenuLeGui;
import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.ActionMenu;
import br.com.dsg.legui.controller.ControllerEventListener;
import br.com.dsg.legui.controller.ExecutarEvento;
import br.com.dsg.legui.controller.GerarController;
import br.com.dsg.legui.controller.LeGuiController;
import br.com.dsg.legui.controller.LeGuiEventos;

public class ListenerEventAdicionarItemMenu implements ControllerEventListener<EventAdicionarItemMenu> {

	private final static Logger LOG = Logger.getLogger(ListenerEventAdicionarItemMenu.class);
	
	public ListenerEventAdicionarItemMenu() {
		super();
	}
	
	@Override
	public void handleEvent(EventAdicionarItemMenu event) {
		LOG.debug( String.format("executando listener %s ", this.getClass().getSimpleName()) );
		LeGuiController controller = event.getControllerAlvo();
		MenuLeGui menu = event.getControllerAlvo().getPanel().getMenu();
		
		final ItemMenu item;
				
		LOG.info(String.format("nome:%s; action:%s;  configSubMenuButtonMouse2:%s;  event.menuFlutuante.isEmpty():%s",event.nome, event.action!=null, event.configSubMenuButtonMouse2 , event.menuFlutuante.isEmpty()) );
		if(event.action!=null && event.menuFlutuante.isEmpty()) {
			
			item =addItemMenuAcao(controller, menu, 
					event.nome, 
					event.imageA, 
					event.imageB, 
					event.imageHorizontalAlignRIGHT, 
					event.desabilitarSelecaoMenu, 
					event.action,
					null);
			
			
		}else {
			if(event.menuFlutuante.isEmpty() || event.configSubMenuButtonMouse2) {
				item = addItemMenu(controller, menu, 
						event.nome, 
						event.imageA, 
						event.imageB, 
						event.imageHorizontalAlignRIGHT, 
						event.cController, 
						event.inicializar,
						null);
			}else {
				item =addItemMenuAcao(controller, menu, 
						event.nome, 
						event.imageA, 
						event.imageB, 
						event.imageHorizontalAlignRIGHT, 
						true, 
						(e)->LOG.debug("abrir submenu "+event.nome),
						null);
			}
		}
		
		addMenuFlutuante(event, controller, menu, item);
	}

	public void addMenuFlutuante(EventAdicionarItemMenu event, LeGuiController controller, MenuLeGui menu,
			final ItemMenu item) {
		if(!event.menuFlutuante.isEmpty()) {
			if(event.configSubMenuButtonMouse2) {
				controller.registerActionMouseButton2(item, (evento)->{
					acaoItemSubMenu(event, controller, menu, item);
				});
			}else {
				controller.registerAction(item, (evento)->{
					acaoItemSubMenu(event, controller, menu, item);
				});
			
			}
		}
	}

	public void acaoItemSubMenu(EventAdicionarItemMenu event, LeGuiController controller, MenuLeGui menu,
			final ItemMenu item) {
		float h = menu.hi() * event.menuFlutuante.size()+new Dialog().getTitleHeight();
		Dialog menuFrame = new Dialog("", menu.wo(), h);
		menuFrame.setCloseable(true);
		menuFrame.setDraggable(true);
		menuFrame.setResizable(false);
		MenuLeGui menuFlutuante = new MenuLeGui(menu.getTheme(),
				0,0,
				menu.wo(), 
				menu.hi()*event.menuFlutuante.size(),
				menu.wo(),
				menu.wo(), 
				menu.hi(), 
				null);
		
		//menuFrame.clearChildComponents();
		menuFrame.getContainer().add(menuFlutuante);
		event.menuFlutuante.forEach(si->{
			if(si.action!=null) {
				ItemMenu itemf =addItemMenuAcao(
						controller, menuFlutuante, 
						si.nome, 
						si.imageA, 
						si.imageB, 
						si.imageHorizontalAlignRIGHT, 
						si.desabilitarSelecaoMenu, 
						si.action,
						menuFrame);
			}else {
				ItemMenu itemf = addItemMenu(controller, menuFlutuante, 
						si.nome, 
						si.imageA, 
						si.imageB, 
						si.imageHorizontalAlignRIGHT, 
						si.cController, 
						si.inicializar,
						menuFrame);
			}
		});
		
		menuFlutuante.update();
		menuFrame.show(menu.getFrame());
		System.out.println("show "+menu.getFrame());
		
		menuFrame.setPosition(
				menu.wc(), 
				item.getPosition().y() + (menu.hi()/2)
				);
	}
	
	/**
	 * @param nome
	 * @param imageA
	 * @param cController
	 * @param inicializar
	 * @return
	 */
	public ItemMenu addItemMenu(LeGuiController controller,MenuLeGui menu, String nome, String imageA, String imageB,boolean imageHorizontalAlignRIGHT, GerarController<?> cController, Boolean inicializar,Dialog menuFrame) {
		
		
		LOG.debug("addItemMenu " + nome + " criado");
		final AbstractController<?> controllerMenu = cController.criar(controller);
		controllerMenu.setNomeController(nome+"_menu_"+System.currentTimeMillis());
		
		ItemMenu item = menu.criarItemMenu(nome, imageA, imageB, imageHorizontalAlignRIGHT);
		item.setPanel(controllerMenu.getPanel().getClass());
		
		controller.registerAction(item, (event) -> {
			menu.seleciona(item);
			
			ActionMenu<LeGuiController> action = (controllerPai) -> LeGuiEventos.irPara(  controllerMenu ) ;
			LOG.debug("ação de menu " + nome + " executada");
			action.executar(controller);
			if(menuFrame!=null) {
				menuFrame.close();
			}
		});
		
		if (inicializar) {
			menu.seleciona(item);
			
			LeGuiEventos.irPara( controllerMenu );
			
		}
		menu.update();
		return item;
	}

	public ItemMenu addItemMenuAcao(LeGuiController controller, MenuLeGui menu, String nome, String imageA, String imageB, boolean imageHorizontalAlignRIGHT, boolean desabilitarSelecaoMenu, ActionMenu<LeGuiController> action, Dialog menuFrame) {
		
		LOG.debug("addItemMenuAcao " + nome + " criado");
		ItemMenu item = menu.criarItemMenu(nome, imageA, imageB, imageHorizontalAlignRIGHT);
		controller.registerAction(item, (event) -> {
			
			if(!desabilitarSelecaoMenu) {
				menu.seleciona(item);
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					for (int i = 0; i <= 100; i++) {
						try {
							Thread.sleep(1);
							
							ExecutarEvento.get().lancar(
									new EventProgressBar(i)
							).executar();
							
						} catch (Exception e) {}
					}
				}
			});
			
			action.executar(controller);
			LOG.debug("ação de menu " + nome + " executada");
			if(menuFrame!=null) {
				menuFrame.close();
			}
		});
		
		if(menu.isAberto()) {
			menu.expandirItens();
		}else {
			menu.encolherItens();
		}
		menu.update();
		return item;
	}
	
}
