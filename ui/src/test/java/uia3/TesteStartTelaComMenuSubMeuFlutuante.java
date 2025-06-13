package uia3;

import org.liquidengine.legui.theme.Themes;

import br.com.dsg.legui.controller.StartLeGui;
import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import uia3.componentes.ConfigController;
import uia3.componentes.HomeController;


public class TesteStartTelaComMenuSubMeuFlutuante {
	
	public static void main(String[] args) {
		new TesteStartTelaComMenuSubMeuFlutuante().deve_iniciar_app_com_menu();
	}

	
	public void deve_iniciar_app_com_menu() {
		StartLeGui.get(800, 600, "App test TesteStartTelaComMenuSubMeuFlutuante")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.abrirFecharMenuPadrao()
		.addItemMenu(
					new EventAdicionarItemMenu(
						"Home",
						"imagens/home_house_10811.png", 
						null,
						false,
						(controllerPai)->new HomeController(controllerPai),
						true
					).addCOntrollerMenuSubMenuFlutuante(
							"Configuração", 
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
		.setThemeConteudo(Themes.FLAT_PETERRIVER)
		.setThemeMenu(Themes.FLAT_WHITE)
//		.menuFechado()
		.start();
	}
}
