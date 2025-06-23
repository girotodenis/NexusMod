package com.dsg.nexusmod.controller;

import javax.swing.JPanel;

/**
 * @author Denis Giroto
 * 
 *         Classe abstrata que define uma estrutura para componentes da camada
 *         controller do padrão arquitetural MVC.
 * 
 */
public interface ControllerContent<T extends JPanel> extends Controller {


	T getPanel();
	
}
