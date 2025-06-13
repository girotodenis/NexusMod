package br.com.dsg.legui.controller;

import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.Themes;

import br.com.dsg.legui.componentes.AppBar;
import br.com.dsg.legui.componentes.ConteudoLeGui;
import br.com.dsg.legui.componentes.MenuLeGui;
import br.com.dsg.util.Constantes;

public class LeGuiView extends Panel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7467979951269544962L;
	
	private MenuLeGui menu;
	private AppBar appBarr;
	private ConteudoLeGui conteudoLeGui;
	
	private Theme themeMenu = null;
	private Theme themeConteudo = null;
	

	public LeGuiView(int width, int height) {
		this(Themes.getDefaultTheme(), Themes.getDefaultTheme(), width, height);
	}
	
	public LeGuiView(Theme themeMenu, Theme themeConteudo, int width, int height) {
        super(0, 0, width, height);
        this.themeMenu = themeMenu;
        this.themeConteudo = themeConteudo;
        add(this.appBarr = new AppBar(Constantes.LARGURA_MENU_ABERTO, 0, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_APP_BAR));
        add(this.conteudoLeGui = new ConteudoLeGui(this.themeConteudo, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_APP_BAR, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_APP_CONTEUDO));
        add(this.menu = new MenuLeGui(this.themeMenu,0,0,
        							Constantes.LARGURA_MENU_ABERTO, 
        							Constantes.ALTURA_APP,
        							Constantes.LARGURA_MENU_FECHADO,
        							Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU, 
        							this.appBarr, this.conteudoLeGui));
        //add(this.menu = new MenuLeGui(this.appBarr));
	}
	
	public MenuLeGui getMenu() {
		return menu;
	}
	
	public void addPanel(Panel panel) {
		conteudoLeGui.addPanel(panel);
	}
	
	public void setValueProgressBar(float value) {
		appBarr.setValue(value);
	}

	public ConteudoLeGui getConteudoLeGui() {
		return conteudoLeGui;
	}

	public void setThemeMenu(Theme themeMenu) {
		this.themeMenu = themeMenu;
	}

	public void setThemeConteudo(Theme themeConteudo) {
		this.themeConteudo = themeConteudo;
	}
	
	public void update() {
		this.menu.setTheme(themeMenu);
		this.menu.update();
		this.conteudoLeGui.setTheme(themeConteudo);
		this.conteudoLeGui.update();
	}

}
