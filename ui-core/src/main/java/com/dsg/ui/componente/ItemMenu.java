package com.dsg.ui.componente;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;

import com.dsg.ui.componente.CustomSideMenu.MenuItem;


public class ItemMenu {
	
	private int order;
	private String id;
	private String text;
	private Icon icon;
	private Consumer<CustomSideMenu.MenuItem> action;
	private List<ItemMenu> subItems;
	private Boolean enabled = true;
	
	public ItemMenu(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer, List<ItemMenu> subItems) {
		super();
		this.text = text;
		this.icon = icon;
		this.action = consumer;
		this.subItems = subItems;
		this.id = String.format("%s.%s", MenuItem.class.getName(), text.trim().replaceAll(" ", "_"));
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
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public ItemMenu addSubItems(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer) {
		if(subItems==null) {
			subItems = new ArrayList<ItemMenu>();
		}
		subItems.add(new ItemMenu(text, icon, consumer, null));
		return this;
	}

}
