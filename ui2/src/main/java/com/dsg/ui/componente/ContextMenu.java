package com.dsg.ui.componente;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JPanel;

public interface ContextMenu {

	void showContent(JPanel panel);
	void updateAll(String look);
	void updateAll(Class<?> look);
	void toast(String title, String content, Icon icon, Color color) ;
	void lastItemMenu(String idItem);

}
