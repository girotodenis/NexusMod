package uia3;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import uia3.componentes.ConfigController;
import uia3.componentes.HomeController;


public class TesteStartTelaComMenu {
	
	public static void main(String[] args) {
		new TesteStartTelaComMenu().deve_iniciar_app_com_menu();
	}

	
	public void deve_iniciar_app_com_menu() {
		StartLeGui.get(800, 600, "App test")
//		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
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
}
