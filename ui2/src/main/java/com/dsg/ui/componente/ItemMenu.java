package com.dsg.ui.componente;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;

import com.dsg.ui.componente.CustomSideMenu.MenuItem;


public class ItemMenu {
	
	private String id;
	private String text;
	private Icon icon;
	private Consumer<CustomSideMenu.MenuItem> action;
	private List<ItemMenu> subItems;
	
	public ItemMenu(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer, List<ItemMenu> subItems) {
		super();
		this.id =  String.format("%s.%s", MenuItem.class.getName(), text.trim().replaceAll(" ", "_"));
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
	
	public String getId() {
		return id;
	}

	public String addSubItems(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> consumer) {
		if(subItems==null) {
			subItems = new ArrayList<ItemMenu>();
		}
		ItemMenu itemMenuGroup = new ItemMenu(text, icon, consumer, null);
		subItems.add(itemMenuGroup);
		return itemMenuGroup.getId();
	}
	
	
}
