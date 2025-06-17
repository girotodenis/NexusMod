package br.com.dsg.principal.view.componente;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;
import br.com.dsg.swing.util.Constantes;
import br.com.dsg.swing.util.PropertiesUtil;

public class AppBar extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615207212602152683L;
	
	private JProgressBar jProgressBar;

	public AppBar() {
		setBackground(Constantes.COR_FUNDO_APP_BAR);
		setLayout(new AbsoluteLayout());
		
		jProgressBar = new JProgressBar();
		
		JLabel jLabel = new JLabel(PropertiesUtil.get("sistema"));
		jLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
		jLabel.setForeground(new java.awt.Color(255, 255, 255));
		add(jLabel, new AbsoluteConstraints(0, 0, 180, 40));
		
		jProgressBar.setBackground(Constantes.COR_FUNDO_APP_BAR);
        jProgressBar.setForeground(new java.awt.Color(0, 204, 204));
        jProgressBar.setBorder(BorderFactory.createLineBorder(Constantes.COR_FUNDO_APP_BAR,-1,false));
        jProgressBar.setPreferredSize(new java.awt.Dimension(getWidth(), Constantes.ALTURA_PROGRESS_BAR));
        jProgressBar.setStringPainted(true);
        System.out.println(getWidth());
        add(jProgressBar, new AbsoluteConstraints(0,Constantes.ALTURA_APP_BAR - Constantes.ALTURA_PROGRESS_BAR , Constantes.LARGURA_APP_BAR, Constantes.ALTURA_PROGRESS_BAR));
        
	}

	public JProgressBar getjProgressBar() {
		return jProgressBar;
	}
	
	
	
}
