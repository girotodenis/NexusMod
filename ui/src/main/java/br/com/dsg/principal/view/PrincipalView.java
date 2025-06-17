/**
 * 
 */
package br.com.dsg.principal.view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import br.com.dsg.principal.view.componente.AppBar;
import br.com.dsg.principal.view.componente.Menu;
import br.com.dsg.principal.view.componente.PrincipalJpanel;
import br.com.dsg.swing.tela.layout.AbsoluteConstraints;
import br.com.dsg.swing.tela.layout.AbsoluteLayout;
import br.com.dsg.swing.util.Constantes;

/**
 * @author Denis Giroto
 *
 */
public class PrincipalView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4304807636437465026L;

	private Menu menu;
	private AppBar appBar;

	private PrincipalJpanel principalJpane;

	private java.awt.Button botao;
	private javax.swing.JPanel target;

	/**
	 * 
	 */
	public PrincipalView() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//		setBackground(Constantes.COR_FUNDO_APP);
		setLocationByPlatform(true);// centralizar

		javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/imagens/icons8-logo-java-coffee-cup-64.png"));
		setIconImage(icon.getImage());

		getContentPane().setLayout(new AbsoluteLayout());
		setResizable(true);
		setSize(Constantes.LARGURA_APP, Constantes.ALTURA_APP);

		this.menu = new Menu();
		add(menu, new AbsoluteConstraints(0, 0, Constantes.LARGURA_MENU_ABERTO, Constantes.ALTURA_APP));

		appBar = new AppBar();
		getContentPane().add(appBar, new AbsoluteConstraints(Constantes.LARGURA_MENU_ABERTO, 0,
				Constantes.LARGURA_APP_BAR, Constantes.ALTURA_APP_BAR));

		this.principalJpane = new PrincipalJpanel();
		getContentPane().add(principalJpane, new AbsoluteConstraints(Constantes.LARGURA_MENU_ABERTO,
				Constantes.ALTURA_APP_BAR, Constantes.LARGURA_APP_BAR, Constantes.ALTURA_APP_CONTEUDO));

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {

				int largura = PrincipalView.this.getWidth() - PrincipalView.this.getMenu().getWidth();

				PrincipalView.this.remove(menu);
				PrincipalView.this.add(menu, new AbsoluteConstraints(0, 0, PrincipalView.this.getMenu().getWidth(),
						PrincipalView.this.getHeight()));

				PrincipalView.this.remove(principalJpane);
				PrincipalView.this.add(principalJpane,
						new AbsoluteConstraints(PrincipalView.this.getMenu().getWidth(), Constantes.ALTURA_APP_BAR,
								largura, PrincipalView.this.getHeight() - Constantes.ALTURA_APP_BAR));

				PrincipalView.this.remove(appBar);
				PrincipalView.this.add(appBar, new AbsoluteConstraints(PrincipalView.this.getMenu().getWidth(), 0,
						largura, Constantes.ALTURA_APP_BAR));

				PrincipalView.this.atualizar();

			}
		});
	}

	public void atualizar() {
		this.invalidate();
		this.validate();
		this.repaint();
	}

	public PrincipalJpanel getPrincipalJpane() {
		return principalJpane;
	}

	public Menu getMenu() {
		return menu;
	}

	public AppBar getAppBar() {
		return appBar;
	}

	public static void main(String[] args) {
		new PrincipalView().setVisible(true);
	}

}
