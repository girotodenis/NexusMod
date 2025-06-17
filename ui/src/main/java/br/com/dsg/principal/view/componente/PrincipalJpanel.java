package br.com.dsg.principal.view.componente;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import br.com.dsg.swing.util.Constantes;

public class PrincipalJpanel extends javax.swing.JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615207212602152683L;
	

//	private AppBar appBar;
	private JPanel conteudo;
	
	public PrincipalJpanel() {
//		setBackground(Constantes.COR_FUNDO_APP);
		setLayout(new GridBagLayout());
		
//		appBar = new AppBar();
//		appBar.setSize(Constantes.LARGURA_APP_BAR, Constantes.ALTURA_APP_BAR);
//		
//		GridBagConstraints gbcAppBar = new GridBagConstraints();
//		gbcAppBar.anchor = GridBagConstraints.NORTHEAST;
//		gbcAppBar.gridx = GridBagConstraints.REMAINDER;
//		gbcAppBar.gridy = GridBagConstraints.REMAINDER;
//		gbcAppBar.fill = GridBagConstraints.HORIZONTAL;
//		gbcAppBar.weightx = 1.0;
//		gbcAppBar.weighty = 1.0;
//		add(appBar, gbcAppBar ,0);
		
		conteudo = new JPanel();
		conteudo.setBackground(Constantes.COR_FUNDO_APP);

		GridBagConstraints gbcConteudo = new GridBagConstraints();
		gbcConteudo.anchor = GridBagConstraints.NORTHWEST;
		gbcConteudo.gridx = GridBagConstraints.REMAINDER;
		gbcConteudo.gridy = GridBagConstraints.REMAINDER;
		gbcConteudo.fill = GridBagConstraints.BOTH;
		gbcConteudo.weightx = 1.0;
		gbcConteudo.weighty = 1.0;
		add(conteudo, gbcConteudo , 0);
	}
	
//	public AppBar getAppBar() {
//		return appBar;
//	}

	public JPanel getConteudo() {
		return conteudo;
	}

	
}
