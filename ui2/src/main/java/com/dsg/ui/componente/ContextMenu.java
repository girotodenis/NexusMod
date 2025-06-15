package com.dsg.ui.componente;

import javax.swing.Icon;
import javax.swing.JPanel;

public interface ContextMenu {

	void showContent(JPanel panel);
	void updateAll(String look);
	void updateAll(Class<?> look);
	void toast(String title, String content, Icon icon) ;
	void lastItemMenu(String idItem);

}
