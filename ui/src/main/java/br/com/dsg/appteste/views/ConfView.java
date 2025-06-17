package br.com.dsg.appteste.views;

import javax.swing.JButton;
import javax.swing.JLabel;

import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;
import br.com.dsg.swing.util.Constantes;

public class ConfView extends javax.swing.JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615207212602152683L;
	

	private JButton botao;

	public ConfView() {
//		setBackground(Constantes.COR_FUNDO_APP);
		setLayout(new AbsoluteLayout());
		JLabel jLabel = new JLabel();
        jLabel.setName("texto");
        jLabel.setText("texto qualquer");
		add(jLabel, new AbsoluteConstraints(0, 0, 120, 40));
		
		botao = new JButton();
		botao.setSize(10, 5);
		botao.setName("botao01");
		botao.setLabel("Voltar");
		add(botao, new AbsoluteConstraints(0, 60, 120, 40));
	}
	
	public JButton getBotao(){
		return botao;
	}

}
