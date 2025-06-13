package uia3;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import uia3.componentes.ConfigController;
import uia3.componentes.HomeController;


public class TesteStart {
	
	public static void main(String[] args) {
		new TesteStart().deve_iniciar_app_com_menu_1();
//		new TesteStart().deve_iniciar_app_com_menu_2();
//		new TesteStart().deve_iniciar_app_sem_menu();
	}


	public void deve_iniciar_app_com_menu_1() {
		StartLeGui.get(800, 600, "App test")
		//.autenticacao( (controllerPai) -> new LoginController(controllerPai) )
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
						true
						)
				.addCOntrollerMenuSubMenuFlutuante(
						"Sub Item 1", 
						"imagens/setting-configure.png",
						(controllerPai)->new ConfigController(controllerPai)
						)
				.addActionMenuSubMenuFlutuante(
						"Sub Item 2", 
						"imagens/icons8-exit-sign-64.png",
						(c)->System.out.println("Sair")
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
		.menuFechado()
		.start();
	}
	
	public void deve_iniciar_app_com_menu_2() {
		StartLeGui.get(800, 600, "App test")
		//.autenticacao( (controllerPai) -> new LoginController(controllerPai) )
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
						true
				)
				.addCOntrollerMenuSubMenuFlutuante(
						"Sub Item 1", 
						"imagens/setting-configure.png",
						(controllerPai)->new ConfigController(controllerPai)
				)
				.addActionMenuSubMenuFlutuante(
						"Sub Item 2", 
						"imagens/icons8-exit-sign-64.png",
						(c)->System.out.println("Sair")
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
		//.menuFechado()
		.start();
	}
	
	public void deve_iniciar_app_sem_menu() {
		StartLeGui.get(800, 600, "App test")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.start();
	}
}
