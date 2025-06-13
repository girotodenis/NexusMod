package uia3.componentes;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

/**
 *
 * @author dgiroto
 */
public class LoginView extends Panel {

	public Button btLogar;

	public LoginView() {

		add(this.btLogar = new Button("Logar", 50, 50, 50, 30));

	}

}