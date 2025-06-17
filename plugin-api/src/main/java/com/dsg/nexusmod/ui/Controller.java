package com.dsg.nexusmod.ui;

import javax.swing.JPanel;

/**
 * @author Denis Giroto
 * 
 *         Classe abstrata que define uma estrutura para componentes da camada
 *         controller do padrão arquitetural MVC.
 * 
 */
public interface Controller<T extends JPanel> {


	T getPanel();
	
}
