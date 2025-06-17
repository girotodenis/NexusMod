/**
 * 
 */
package br.com.dsg;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.formdev.flatlaf.FlatDarculaLaf;

import br.com.dsg.appteste.controller.ConfigController;
import br.com.dsg.appteste.controller.HomeController;
import br.com.dsg.principal.controller.PrincipalController;
import br.com.dsg.swing.util.PropertiesUtil;

/**
 * @author Denis Giroto
 *
 */
public class Main {
	
	private final static Logger LOG = Logger.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LOG.info(PropertiesUtil.get("sistema"));
		
		try {
			javax.swing.UIManager.setLookAndFeel(FlatDarculaLaf.class.getName());
        } catch (Exception ex) {
        	LOG.error(ex.getMessage(), ex);
        }
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LOG.info("PrincipalController criando...");
				new PrincipalController()
					//.habilitaMovimentavaoAppBar()
					.addItemMenu("Home","/imagens/home_house_10811.png", (controllerPai)->new HomeController(controllerPai), Boolean.TRUE)
					.addItemMenu("Configuração","/imagens/setting-configure.png",  (controllerPai)->new ConfigController(controllerPai))
					.addItemMenu("Sair","/imagens/icons8-exit-sign-64.png",  () -> System.exit(0))
					.fecharMenu()
					.visualizarApp();
				LOG.info("PrincipalController criado");
			}
		});

	}

}
