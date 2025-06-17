package br.com.dsg.principal.view.componente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;
import br.com.dsg.swing.util.Constantes;

public class Menu extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275627456468898718L;

	private Map<String, ItemMenu> mapa = new HashMap<String, ItemMenu>();

	private BotaoMenu botaoMenu;
	private boolean aberto = Boolean.TRUE;
	
	public Menu() {
//		setBackground(Constantes.COR_FUNDO_MENU);
		setLayout(new AbsoluteLayout());
		
		botaoMenu = new BotaoMenu(true);
		add(botaoMenu, new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU));
	}

	public void addItem(ItemMenu item) {
		mapa.put(item.getName(), item);
		item.setOrdem(mapa.size());

		add(item, new AbsoluteConstraints(0, item.getPosicao(), Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU));
	}

	public ItemMenu getItem(String nome) {
		return mapa.get(nome);
	}

	public void resetOutros(ItemMenu item) {
		List<ItemMenu> itens = mapa.values().stream().filter(i -> !i.getName().equals(item.getName()))
				.collect(Collectors.toList());
		itens.forEach(i -> i.rest());
	}

	public void encolherItens() {
		
		aberto = false;
		remove(botaoMenu);
		botaoMenu = new BotaoMenu(false);
		add(botaoMenu, new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_FECHADO, Constantes.ALTURA_ITEM_MENU));
		
		
		List<ItemMenu> itens = mapa.values().stream().collect(Collectors.toList());
		itens.forEach(i -> {
			i.getjLabel().setText("");
			remove(i);
			add(i, new AbsoluteConstraints(0, i.getPosicao(), Constantes.LARGURA_MENU_FECHADO, Constantes.ALTURA_ITEM_MENU));
		});
	}

	public void expandirItens() {
		aberto = true;
		remove(botaoMenu);
		botaoMenu = new BotaoMenu(true);
		add(botaoMenu, new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU));
		
		List<ItemMenu> itens = mapa.values().stream().collect(Collectors.toList());
		itens.forEach(i -> {
			i.getjLabel().setText(i.getName());
			remove(i);
			add(i, new AbsoluteConstraints(0, i.getPosicao(), Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU));
		});
	}
	
	public BotaoMenu getBotaoMenu() {
		return botaoMenu;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	

}
