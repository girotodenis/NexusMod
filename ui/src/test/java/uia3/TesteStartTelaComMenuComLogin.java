package uia3;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import uia3.componentes.ConfigController;
import uia3.componentes.HomeController;
import uia3.componentes.LoginComPermissaoController;
import uia3.componentes.LoginSemPermissaoController;


public class TesteStartTelaComMenuComLogin {
	
	public static void main(String[] args) {
		new TesteStartTelaComMenuComLogin().deve_iniciar_app_com_menu_fechado_com_login();
//		new TesteStartTelaComMenuComLogin().deve_iniciar_app_com_menu_com_login();
	}


	public void deve_iniciar_app_com_menu_com_login() {
		StartLeGui.get(800, 600, "App test")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.autenticacao( (controllerPai) -> new LoginComPermissaoController(controllerPai) )
		.abrirFecharMenuPadrao()
		.addItemMenu(
				new EventAdicionarItemMenu(
						"Home",
						"imagens/home_house_10811.png", 
						null,
						false,
						(controllerPai)->new HomeController(controllerPai),
						false
				)
		)
		.addItemMenu(
				new EventAdicionarItemMenu(
						"Sair",
						"imagens/icons8-exit-sign-64.png",
						null,
						false,
						true,
						(c) -> c.setAppFinalizado(true) 
				)
		)
		.start();
	}
	
	public void deve_iniciar_app_com_menu_fechado_com_login() {
		StartLeGui.get(800, 600, "App test")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.autenticacao( (controllerPai) -> new LoginSemPermissaoController(controllerPai) )
		.menuFechado()
		.abrirFecharMenuPadrao()
		.addItemMenu(
				new EventAdicionarItemMenu(
						"Configuração",
						"imagens/setting-configure.png", 
						null,
						false,
						(controllerPai)->new ConfigController(controllerPai),
						false
				)
		)
		.addItemMenu(
				new EventAdicionarItemMenu(
						"Home",
						"imagens/home_house_10811.png", 
						null,
						false,
						(controllerPai)->new HomeController(controllerPai),
						false
						)
				)
		.addItemMenu(
				new EventAdicionarItemMenu(
						"Sair",
						"imagens/icons8-exit-sign-64.png",
						null,
						false,
						true,
						(c) -> c.setAppFinalizado(true) 
						)
				)
		.start();
	}
}
