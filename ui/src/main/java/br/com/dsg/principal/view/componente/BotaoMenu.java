package br.com.dsg.principal.view.componente;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;
import br.com.dsg.swing.util.Constantes;

public class BotaoMenu extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1923843665779481525L;
	private static final int TAMANHO_ICONE = 40;
	
	private JLabel jLabelIco = new javax.swing.JLabel();
	
	
	public BotaoMenu(boolean aberto) {
		
		setName("fecharmenu");
		
//		setBackground(Constantes.COR_FUNDO_ITEM_MENU);
		setLayout(new AbsoluteLayout());

		jLabelIco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icons8-cardapio-30.png")));
		jLabelIco.setSize(2,2);
		if(aberto)
			add(jLabelIco,new AbsoluteConstraints(Constantes.LARGURA_MENU_ABERTO - TAMANHO_ICONE, 0, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_ITEM_MENU));
		else
			add(jLabelIco,new AbsoluteConstraints(Constantes.LARGURA_MENU_FECHADO - TAMANHO_ICONE, 0, Constantes.LARGURA_MENU_FECHADO, Constantes.ALTURA_ITEM_MENU));
		
		

	}

	
}
