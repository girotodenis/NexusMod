package uia3;

import br.com.dsg.legui.controller.StartLeGui;
import uia3.componentes.HomeController;
import uia3.componentes.LoginComPermissaoController;


public class TesteStartTelaSemMenuComLogin {
	
	public static void main(String[] args) {
		new TesteStartTelaSemMenuComLogin().deve_iniciar_app_sem_menu_com_login();
	}


	public void deve_iniciar_app_sem_menu_com_login() {
		StartLeGui.get(800, 600, "App test")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.autenticacao( (controllerPai) -> new LoginComPermissaoController(controllerPai) )
		.start();
	}
}
