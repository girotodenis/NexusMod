package com.dsg.nexusmod.controller;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.Icon;

public class MenuItem {
	
	/**
	 * Classe que representa um item de menu no sistema. Utiliza o padrão Builder
	 * para facilitar a criação de instâncias.
	 * 
	 * @author Denis Giroto
	 */
	private String id; // Grupo do item de menu
	private final String group; // Grupo do item de menu
	private final String text; // Texto do item de menu
	private final Icon icon; // Ícone do item de menu
	private final Supplier<ControllerContent<?>> controller; // Controller associado
	private final int order; // Ordem do item de menu

	// Construtor privado (somente acessível pelo Builder)
	private MenuItem(Builder builder) {
		this.group = builder.group;
		this.text = builder.text;
		this.icon = builder.icon;
		this.controller = builder.controller;
		this.order = builder.order;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	// Getters (somente leitura porque a classe é imutável)
	public String getGroup() {
		return group;
	}

	public String getText() {
		return text;
	}

	public Icon getIcon() {
		return icon;
	}

	public ControllerContent<?> getController() {
		return controller.get();
	}

	public int getOrder() {
		return order;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Builder interno
	public static class Builder {
		private String group;
		private String text;
		private Icon icon;
		private Supplier<ControllerContent<?>> controller;
		private int order;

		// Método para definir o grupo
		public Builder group(String group) {
			this.group = group;
			return this; // Permite encadeamento
		}

		// Método para definir o texto
		public Builder text(String text) {
			this.text = text;
			return this;
		}

		// Método para definir o ícone
		public Builder icon(Icon icon) {
			this.icon = icon;
			return this;
		}

		// Método para definir o controller
		public Builder controller(Supplier<ControllerContent<?>> controller) {
			this.controller = controller;
			return this;
		}

		// Método para definir a ordem
		public Builder order(int order) {
			this.order = order;
			return this;
		}

		// Método para construir a instância final de MenuItem
		public MenuItem build() {
			return new MenuItem(this);
		}
	}

	@Override
	public String toString() {
		return "MenuItem{" + "  group='" + group + '\'' + ", text='" + text + '\'' + ", order=" + order + '}';
	}
}
