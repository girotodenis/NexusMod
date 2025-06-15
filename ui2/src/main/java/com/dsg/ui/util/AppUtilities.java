package com.dsg.ui.util;

import java.util.function.BiConsumer;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.dsg.ui.JPanelApp;
import com.dsg.ui.componente.ContextMenu;
import com.dsg.ui.componente.CustomSideMenu;
import com.formdev.flatlaf.FlatDarculaLaf;

public class AppUtilities {
	
	private JPanelApp main;
	private JFrame frame;
	
	private AppUtilities(String title, int width, int height, JPanelApp mainPanel ) {
		
		this.main = mainPanel;
		
		SwingUtilities.invokeLater(() -> {
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
	}

	public static CustomModalBuilder builder() {
		return new CustomModalBuilder();
	}
	
	public JPanelApp getMain() {
		return main;
	}

	public static class CustomModalBuilder {
		private String title = "-";
		private int frameWidth = 600; 
		private int frameHeight = 400;
		private JPanelApp main = new JPanelApp(FlatDarculaLaf.class);
		
		public CustomModalBuilder lookAndFeel(Class<? extends LookAndFeel> classLook) {
			main.updateAll(classLook);
			return this;
		}
		
		public CustomModalBuilder title(String title) {
			this.title = title;
			return this;
		}
		
		public CustomModalBuilder size(int width, int height) {
			frameWidth = width;
			frameHeight = height;
			return this;
		}
		
		public CustomModalBuilder addMenuItem(String text, Icon icon, BiConsumer<ContextMenu, CustomSideMenu.MenuItem> action) {
			main.addMenuItem(text, icon, action);
			return this;
		}
		
		public CustomModalBuilder addMenuItem(String group, String text, Icon icon, BiConsumer<ContextMenu, CustomSideMenu.MenuItem> action) {
			main.addMenuItem(group, text, icon, action);
			return this;
		}
		
		public AppUtilities build() {
			this.main.loadMenu();
			return new AppUtilities(this.title, this.frameWidth,this.frameHeight, this.main);
		}
		
	}

}
