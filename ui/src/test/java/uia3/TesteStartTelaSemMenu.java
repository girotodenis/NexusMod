package uia3;

import br.com.dsg.legui.controller.StartLeGui;
import uia3.componentes.HomeController;


public class TesteStartTelaSemMenu {
	
	public static void main(String[] args) {
		new TesteStartTelaSemMenu().deve_iniciar_app_sem_menu();
	}

	
	public void deve_iniciar_app_sem_menu() {
		StartLeGui.get(800, 600, "App test")
		.controllerPrincipall( (controllerPai) -> new HomeController(controllerPai) )
		.start();
	}
}
