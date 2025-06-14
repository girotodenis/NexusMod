package br.com.dsg.legui.controller.eventos;

import java.util.ArrayList;
import java.util.List;

import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.image.loader.ImageLoader;

import br.com.dsg.legui.controller.ActionMenu;
import br.com.dsg.legui.controller.EventoController;
import br.com.dsg.legui.controller.GerarController;
import br.com.dsg.legui.controller.LeGuiController;

public class EventAdicionarItemMenu extends EventoController<LeGuiController>{
	
	protected String nome;
	protected LoadableImage imageA; 
	protected LoadableImage imageB;
	protected boolean imageHorizontalAlignRIGHT; 
	protected GerarController<?> cController;
	protected Boolean inicializar;
	protected Boolean desabilitarSelecaoMenu;
	protected ActionMenu<LeGuiController> action;
	protected boolean configSubMenuButtonMouse2 = false;
	
	protected List<EventAdicionarItemMenu> menuFlutuante = new ArrayList<>();

	public EventAdicionarItemMenu(String nome, String imageA, String imageB,boolean imageHorizontalAlignRIGHT, GerarController<?> cController, Boolean inicializar) {
		this(nome, imageA, imageB, imageHorizontalAlignRIGHT, cController, inicializar, false);
	}
	
	public EventAdicionarItemMenu(String nome, String imageA, String imageB,boolean imageHorizontalAlignRIGHT, GerarController<?> cController, Boolean inicializar, boolean configSubMenuButtonMouse2) {
		super(LeGuiController.get());
		
		this.nome = nome;
		this.imageA = imageA!= null ? ImageLoader.loadImage(imageA):null;
		this.imageA.getImageData();
		this.imageB = imageB!= null ? ImageLoader.loadImage(imageB):null;// ImageLoader.loadImage(imageB);
		this.imageHorizontalAlignRIGHT = imageHorizontalAlignRIGHT;
		this.cController = cController;
		this.inicializar = inicializar;
		this.configSubMenuButtonMouse2 = configSubMenuButtonMouse2;
		
	}
	
	public EventAdicionarItemMenu(String nome, String imageA, String imageB, boolean imageHorizontalAlignRIGHT, boolean desabilitarSelecaoMenu, ActionMenu<LeGuiController> action) {
		this(nome, imageA, imageB, imageHorizontalAlignRIGHT, desabilitarSelecaoMenu, action, false);
	}
	public EventAdicionarItemMenu(String nome, String imageA, String imageB, boolean imageHorizontalAlignRIGHT, boolean desabilitarSelecaoMenu, ActionMenu<LeGuiController> action, boolean configSubMenuButtonMouse2) {
		super(LeGuiController.get());
		this.nome = nome;
		this.imageA = imageA!= null ? ImageLoader.loadImage(imageA):null;
		this.imageB = imageB!= null ? ImageLoader.loadImage(imageB):null;
		this.imageHorizontalAlignRIGHT = imageHorizontalAlignRIGHT;
		this.desabilitarSelecaoMenu = desabilitarSelecaoMenu;
		this.action = action;
		this.configSubMenuButtonMouse2 = configSubMenuButtonMouse2;
	}	
	

	public EventAdicionarItemMenu addActionMenuSubMenuFlutuante(String nome, String imageA, ActionMenu<LeGuiController> action) {
		this.menuFlutuante.add(new EventAdicionarItemMenu(nome,imageA,imageA,false,true,action));
		return this;
	}
	
	public EventAdicionarItemMenu addCOntrollerMenuSubMenuFlutuante(String nome, String imageA, GerarController<?> cController) {
		this.menuFlutuante.add(new EventAdicionarItemMenu(nome,imageA,imageA,false,cController,false));
		return this;
	}
	
}
