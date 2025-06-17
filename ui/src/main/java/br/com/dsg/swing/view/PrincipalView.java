/**
 * 
 */
package br.com.dsg.swing.view;

import javax.swing.JFrame;

import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;

/**
 * @author Denis Giroto
 *
 */
public class PrincipalView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4304807636437465026L;
	
	private java.awt.Button botao;
	private javax.swing.JPanel target;
	/**
	 * 
	 */
	public PrincipalView() {
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//		setBackground(new java.awt.Color(255, 255, 255));
//		
		//centralizar
        setLocationByPlatform(true);
        
        //retirar barra
        //setUndecorated(true);
        getContentPane().setLayout(new AbsoluteLayout());
        
        pack();
        setSize(1000, 600);
        
        
        botao = new java.awt.Button();
        botao.setName("botao01");
        botao.setLabel("Acao");
        getContentPane().add(botao, new AbsoluteConstraints(100, 20, 100, 40));
        
        
        target = new javax.swing.JPanel();
//        target.setBackground(new java.awt.Color(23, 35, 51));
        getContentPane().add(target, new AbsoluteConstraints(400, 20, 100, 100));
	}
	
	
	public java.awt.Button getBotao() {
		return botao;
	}
	
	public javax.swing.JPanel getTarget() {
		return target;
	}

	

}
