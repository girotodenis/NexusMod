package com.dsg.ui.componente;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;

import com.dsg.ui.componente.CustomSideMenu.MenuItem;


public class ItemMenu {
	
	private String text;
	private Icon icon;
	private Consumer<MenuItem> action;
	private List<ItemMenu> subItems;
	
	public ItemMenu(String text, Icon icon, Consumer<MenuItem> consumer, List<ItemMenu> subItems) {
		super();
		this.text = text;
		this.icon = icon;
		this.action = consumer;
		this.subItems = subItems;
	}

	public String getText() {
		return text;
	}

	public Icon getIcon() {
		return icon;
	}

	public Consumer<MenuItem> getAction() {
		return action;
	}

	public List<ItemMenu> getSubItems() {
		return subItems;
	}
	
	
}
