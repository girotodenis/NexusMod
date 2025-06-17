package br.com.dsg.principal.controller;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import br.com.dsg.principal.view.PrincipalView;
import br.com.dsg.principal.view.componente.ItemMenu;
import br.com.dsg.swing.controller.AbstractController;
import br.com.dsg.swing.controller.action.AbstractAction;
import br.com.dsg.swing.controller.action.Action;

/**
 * @author Denis Giroto
 *
 */
public class PrincipalController extends AbstractController<PrincipalView> {

	private int xx, xy;

	private final static Logger LOG = Logger.getLogger(PrincipalController.class);

	/**
	 * @author Denis Giroto
	 *Classe utilizada para disparar um evento que atualiza a tela Principal.
	 */
	public class AtualizarAppEvento {}

	/**
	 * 
	 */
	private class ActionBotaoMenu extends java.awt.event.MouseAdapter {
		
		PrincipalController controller;
		
		public ActionBotaoMenu(PrincipalController controller) {
			this.controller = controller;
		}
		
		public void mousePressed(java.awt.event.MouseEvent evt) {

			controller.fireEvent(new EventAbrirFecharMenu(getPanel()));

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					fireEvent(new AtualizarAppEvento());
				}
			});
		}
	};

	/**
	 * 
	 */
	public PrincipalController() {
		super(new PrincipalView());

		getPanel().addWindowListener(this);

		/*
		 * Cadastro do evento de atualização da tela principal
		 * 
		 */
		registerEventListener(AtualizarAppEvento.class, (event) -> {
			
			
			getPanel().atualizar();
			
			//em determinados caso que é nescessario manipular
			//o estado do menu é necessario recriar o listener do menu
			if(getPanel().getMenu().getBotaoMenu().getMouseListeners().length==0) {
				getPanel().getMenu().getBotaoMenu().addMouseListener(new ActionBotaoMenu(this));
			}
				
		});
		
		/*
		* Cadastro do evento de atualização do conteudo principal
		* 
		* 
		*/
		registerEventListener(AtualizarConteudoEvento.class, (event) -> {
			
			if (event.getController() == null || event.getController() instanceof PrincipalController) {
				return;
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					if (event.getNome() != null) {
						
						ItemMenu item = PrincipalController.this.getPanel().getMenu().getItem(event.getNome());
						item.seleciona();
						getPanel().getMenu().resetOutros(item);
					}
					
					if (event.getController() != null) {
						getPanel().getPrincipalJpane().getConteudo().removeAll();
						getPanel().getPrincipalJpane().getConteudo().setLayout(new GridBagLayout());
						GridBagConstraints c = new GridBagConstraints();
						c.gridx = GridBagConstraints.RELATIVE;
						c.gridy = GridBagConstraints.RELATIVE;
						c.fill = GridBagConstraints.BOTH;
						c.weightx = 1.0;
						c.weighty = 1.0;
						c.gridwidth = GridBagConstraints.REMAINDER;
						c.gridheight = GridBagConstraints.REMAINDER;
						getPanel().getPrincipalJpane().getConteudo().add((Component) event.getController().getPanel(),
						c);
						fireEvent(new AtualizarAppEvento());
					}
				}
			});
		});
		
		/*
		 * Cadastro do evento de atualização da tela principal
		 * con a tela anterior
		 */
		registerEventListener(EventVoltarController.class, (event) -> {
			
			AbstractController<?> controllerAtual = event.getController();
			AbstractController<?> controllerAnterior = event.getController().getControllerPai();

			if(controllerAnterior!=null && controllerAnterior.getPanel()!=null ){
	
				controllerAnterior.remove(controllerAtual);
				fireEvent(new AtualizarConteudoEvento(controllerAnterior));
			}
		});
		
		/*
		 * Cadastro do evento de atualização do ProgressBar tela principal
		 * 
		 */
		registerEventListener(EventAtualizarProgressBar.class, (event) -> {
			
			getPanel().getAppBar().getjProgressBar().setValue(event.getValor());
			if(event.getAtual()==event.getTotal() || event.getTotal()==0) {
				
				getPanel().getAppBar().getjProgressBar().setValue(0);
			}
		});

		//Cadastro da ação do botão do menu
		getPanel().getMenu().getBotaoMenu().addMouseListener(new ActionBotaoMenu(this));

	}

	/**
	 * @return
	 */
	public PrincipalController habilitaMovimentavaoAppBar() {
		getPanel().setUndecorated(true);// retirar barra
		getPanel().getAppBar().addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				int x = evt.getXOnScreen();
				int y = evt.getYOnScreen();
				getPanel().setLocation(x - xx, y - xy);
			}
		});

		getPanel().getAppBar().addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				xx = evt.getX();
				xy = evt.getY();
				if(evt.getClickCount()==3){
					if(getPanel().getExtendedState()!=JFrame.MAXIMIZED_BOTH)
						getPanel().setExtendedState(JFrame.MAXIMIZED_BOTH);
					else
						getPanel().setExtendedState(JFrame.NORMAL);
				}
			}
		});

		return this;
	}

	/**
	 * @param nome
	 * @return
	 */
	public PrincipalController setIconImage(String imagemCaminho) {
		javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource(imagemCaminho));
		getPanel().setIconImage(icon.getImage());
		return this;
	}

	/**
	 * @param nome
	 * @param cController
	 * @return
	 */
	public PrincipalController addItemMenu(String nome, CriaController cController) {

		LOG.info("menu " + nome + " criado");

		addItemMenu(nome, null, cController, Boolean.FALSE);

		return this;
	}

	/**
	 * @param nome
	 * @param imagem
	 * @param cController
	 * @return
	 */
	public PrincipalController addItemMenu(String nome, String imagem, CriaController cController) {

		LOG.info("menu " + nome + " criado");

		addItemMenu(nome, imagem, cController, Boolean.FALSE);

		return this;
	}

	/**
	 * @param nome
	 * @param imagem
	 * @param cController
	 * @param inicializar
	 * @return
	 */
	public PrincipalController addItemMenu(String nome, String imagem, CriaController cController,
			Boolean inicializar) {

		LOG.info("menu " + nome + " criado");

		getPanel().getMenu().addItem(new ItemMenu(nome, imagem, new AbstractAction() {
			protected void action() {
				
				AbstractController<?> controllerMenu = cController.criar(PrincipalController.this);
				controllerMenu.setNomeController(nome);
				fireEvent(new AtualizarConteudoEvento(controllerMenu));
				fireEvent(new EventAtualizarProgressBar(0,0));
			}
		}));

		if (inicializar) {
			fireEvent(new AtualizarConteudoEvento(nome, cController.criar(PrincipalController.this)));
		}
		return this;
	}
	
	public PrincipalController addItemMenu(String nome, String imagem, Action action) {
		
		LOG.info("menu " + nome + " criado");
		getPanel().getMenu().addItem(new ItemMenu(nome, imagem, action));
		return this;
	}
	
	/**
	 * @return
	 */
	public PrincipalController fecharMenu() {

		fireEvent(new EventAbrirFecharMenu(getPanel()));
		return this;
	}

	/**
	 * 
	 */
	public void visualizarApp() {
		this.getPanel().setVisible(true);
	}

}
