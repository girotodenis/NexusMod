package br.com.dsg.legui.componentes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.theme.Theme;

import br.com.dsg.legui.componentes.eventos.MenuChangeSizeEvent;
import br.com.dsg.util.Constantes;

public class MenuLeGui extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275627456468898718L;

	private Map<String, ItemMenu> mapa = new HashMap<String, ItemMenu>();

	private Component[] components;
	private boolean aberto = Boolean.TRUE;
	
	private float x, y, w, h, wc, wo, hi = 0;
	
	private Theme theme = null;

	public MenuLeGui(Theme theme, float x, float y, float w, float h,float wc, float wo, float hi, Component... panels) {

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.wc = wc;
		this.wo = wo;
		this.hi = hi;
		this.components = panels;
		this.theme = theme;

		this.setPosition(this.x, this.y);
		this.setSize(this.wo, this.h);

		getListenerMap().addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> {
			
			this.y = event.getHeight();
			this.x = getSize().x;
			
			Vector2f newSize = new Vector2f(this.x, event.getHeight());
			notifica(newSize);
			this.setSize(newSize);
			
			update();
			
		});
		
		update();

	}

	
	public ItemMenu criarItemMenu(String nome, String imageA, String imageB, boolean imageHorizontalAlignRIGHT) {
		ItemMenu item = new ItemMenu(this.theme, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU, nome, imageA, imageB, imageHorizontalAlignRIGHT);
		mapa.put(item.getNome(), item);
		item.setOrdem(mapa.size()-1);
		item.setPosition(0, item.getPosicao());
		item.setSize(this.wo, this.hi);
		
		theme.getThemeManager().getComponentTheme(ItemMenu.class).applyAll(item);
		add(item);
		update();
		return item;
	}

	public ItemMenu getItem(String nome) {
		return mapa.get(nome);
	}
	
	public ItemMenu getItem(Class<?> classe) {
		List<ItemMenu> itens = mapa.values().stream().filter(i -> i.getPanel()!=null && i.getPanel().equals(classe))
				.collect(Collectors.toList());
		return itens.isEmpty()?null:itens.get(0);
	}

	public void seleciona(ItemMenu item) {
		item.seleciona();
		resetOutros(item);
	}
	
	public void resetOutros(ItemMenu item) {
		List<ItemMenu> itens = mapa.values().stream().filter(i -> !i.getNome().equals(item.getNome()))
				.collect(Collectors.toList());
		itens.forEach(i -> i.rest());
		update();
	}

	public void encolherItens() {
		aberto = false;
//		this.w = this.wc;
		Vector2f newSize = new Vector2f(this.wc, this.getSize().y);
		notifica(newSize);
		setSize(newSize);
		update();

	}

	public void expandirItens() {
		aberto = true;
//		this.w = this.wo;
		Vector2f newSize = new Vector2f(this.wo, this.getSize().y);
		notifica(newSize);
		setSize(newSize);
		update();
	}
	
	
	public void esconder() {
		Vector2f newSize = new Vector2f(0, this.getSize().y);
		notifica(newSize);
		setSize(newSize);
		update();
	}

	public void exibir() {
		Vector2f newSize = null;
		if(aberto) {
			newSize = new Vector2f(this.wo, this.getSize().y);
		}else {
			newSize = new Vector2f(this.wc, this.getSize().y);
		}
		notifica(newSize);
		setSize(newSize);
		update();
	}
	

	public boolean isAberto() {
		return aberto;
	}

	public void notifica(Vector2f newSize) {

		mapa.values().forEach(item -> {
			EventProcessorProvider.getInstance().pushEvent(new MenuChangeSizeEvent<Component>(item, null,
					MenuLeGui.this.getFrame(), MenuLeGui.this.getSize(), newSize, this.aberto));
		});

		if (this.components != null) {
			for (Component panel : this.components) {
				EventProcessorProvider.getInstance().pushEvent(new MenuChangeSizeEvent<Component>(panel, null,
						MenuLeGui.this.getFrame(), MenuLeGui.this.getSize(), newSize, this.aberto));
			}
		}
	}


	public float x() {
		return x;
	}


	public float y() {
		return y;
	}


	public float w() {
		return w;
	}


	public float h() {
		return h;
	}


	public float wc() {
		return wc;
	}


	public float wo() {
		return wo;
	}


	public float hi() {
		return hi;
	}
	
	public void update() {
		theme.getThemeManager().getComponentTheme(Panel.class).applyAll(this);
		mapa.values().stream().forEach(e->e.update());
	}
	
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	

}
