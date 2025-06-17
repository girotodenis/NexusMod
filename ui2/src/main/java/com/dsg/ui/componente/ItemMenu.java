package com.dsg.ui.componente;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;


public class ItemMenu {
	
	private String text;
	private Icon icon;
	private Consumer<CustomSideMenu.MenuItem> action;
	private List<ItemMenu> subItems;
	
	public ItemMenu(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer, List<ItemMenu> subItems) {
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

	public Consumer<CustomSideMenu.MenuItem> getAction() {
		return action;
	}

	public List<ItemMenu> getSubItems() {
		return subItems;
	}
	
	public ItemMenu addSubItems(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer) {
		if(subItems==null) {
			subItems = new ArrayList<ItemMenu>();
		}
		subItems.add(new ItemMenu(text, icon, consumer, null));
		return this;
	}
	
	
}
