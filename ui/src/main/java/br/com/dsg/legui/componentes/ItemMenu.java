package br.com.dsg.legui.componentes;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.theme.Theme;

import br.com.dsg.legui.componentes.eventos.MenuChangeSizeEvent;
import br.com.dsg.legui.componentes.eventos.MenuChangeSizeEventListener;
import br.com.dsg.util.Constantes;

public class ItemMenu extends Button {

	private static final String ESPACO = ".              ";


	/**
	 * 
	 */
	private static final long serialVersionUID = 1923843665779481525L;

	private String nome = "";
	private String imagePathAberta = "";
	private String imagePathFechada = "";
	private boolean imageHorizontalAlignRIGHT = false;
	
	private Class<? extends Panel> panel;
	
	private float w, h = 0;
	private int ordem = 0;
	private int posicao = 0;
	private Vector4f color;
	private Vector4f colorSelecionado;
	
	private Theme theme = null;

	public ItemMenu(Theme theme,float w, float h, String nome, String imagePathAberta) {
		this(theme, w, h, nome, imagePathAberta, imagePathAberta, false);
	}
	
	public ItemMenu(Theme theme,float w, float h, String nome, String imagePathAberta, boolean imageHorizontalAlignRIGHT) {
		this(theme,w,h,nome, imagePathAberta, imagePathAberta, false);
	}
	
	public ItemMenu(Theme theme,float w, float h, String nome, String imagePathAberta, String imagePathFechada, boolean imageHorizontalAlignRIGHT) {
		
		this.theme=theme;
		
		
		
		this.nome=ESPACO + nome;
		this.imagePathAberta = imagePathAberta;
		this.imagePathFechada = imagePathFechada;
		this.imageHorizontalAlignRIGHT = imageHorizontalAlignRIGHT;
		this.w=w;
		this.h=h;
		
		
		getTextState().setText(this.nome);
		
		setSize(this.w, this.h);

		Icon bgIm = new ImageIcon(ImageLoader.loadImage(this.imagePathAberta));
		bgIm.setSize(new Vector2f(35, 30));
		bgIm.setPosition(new Vector2f(8, 5));
		getStyle().getBackground().setIcon(bgIm);
		
		expandir();
		
		getListenerMap().addListener(MenuChangeSizeEvent.class, (MenuChangeSizeEventListener) event -> {
			this.w = event.getNewSize().x();
			setSize(w, Constantes.ALTURA_ITEM_MENU);
			if(event.isAberto()) {
				expandir();
			}else {
				encolher();
			}
		});
		
		theme.getThemeManager().getComponentTheme(Button.class).applyAll(this);
		this.color = getStyle().getBackground().getColor();
		this.colorSelecionado = getPressedStyle().getBackground().getColor();
		getStyle().setHorizontalAlign(HorizontalAlign.LEFT);
	}

	private void encolher() {
		Icon bgIm = new ImageIcon(ImageLoader.loadImage(this.imagePathFechada!=null?this.imagePathFechada:this.imagePathAberta));
		bgIm.setSize(new Vector2f(35, 30));
		bgIm.setPosition(new Vector2f(8, 5));
		getStyle().getBackground().setIcon(bgIm);
		
		getTextState().setText("");
	}
	
	private void expandir() {
		if(imageHorizontalAlignRIGHT) {
			Icon bgIm = new ImageIcon(ImageLoader.loadImage(this.imagePathAberta));
			bgIm.setSize(new Vector2f(35, 30));
			bgIm.setPosition(new Vector2f(getSize().x()-37, 5));
			getStyle().getBackground().setIcon(bgIm);
		}else {
			Icon bgIm = new ImageIcon(ImageLoader.loadImage(this.imagePathAberta));
			bgIm.setSize(new Vector2f(35, 30));
			bgIm.setPosition(new Vector2f(8, 5));
			getStyle().getBackground().setIcon(bgIm);
		}
		getTextState().setText(nome);
	}
	
	protected void seleciona() {
		this.setEnabled(false);
		this.setPressed(true);
		this.setFocused(true);
		update();
		this.getStyle().getBackground().setColor(this.colorSelecionado);
		//this.getStyle().getBackground().setColor(this.getPressedStyle().getBackground().getColor());
	}

	public void rest() {
		this.setEnabled(true);
		this.setPressed(false);
		this.setFocused(false);
		update();
		this.getStyle().getBackground().setColor(this.color);
		//this.getStyle().getBackground().setColor(this.color);
	}

	public int getPosicao() {
		return posicao;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
		this.posicao = ((int) this.h) * this.ordem;
	}

	protected String getNome() {
		return nome;
	}

	public void setPanel(Class<? extends Panel> panel) {
		this.panel = panel;
	}

	protected Class<? extends Panel> getPanel() {
		return panel;
	}
	
	public void update() {
		theme.getThemeManager().getComponentTheme(Button.class).applyAll(this);
		
		for(Component c: getChildComponents() ) {
			if(c.getChildComponents()!=null && !c.getChildComponents().isEmpty()) {
				update(c.getChildComponents());
			}
		}
		this.getStyle().setHorizontalAlign(HorizontalAlign.LEFT);
		if(this.isEnabled()){
			this.getStyle().getBackground().setColor(this.color);
		}else {
			this.getStyle().getBackground().setColor(this.colorSelecionado);
		}
	}
	
	public void update(List<Component> lista) {
		for(Component c: lista ) {
			theme.getThemeManager().getComponentTheme((Class<Component>) c.getClass()).applyAll((Component)c);
			if(c.getChildComponents()!=null && !c.getChildComponents().isEmpty()) {
				update(c.getChildComponents());
			}
		}
	}

	public Theme getTheme() {
		return theme;
	}

}
