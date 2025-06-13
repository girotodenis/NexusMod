package uia3.componentes;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

/**
 *
 * @author dgiroto
 */
public class ConfigView extends Panel {

	public Button btCriarMenu;
	public Button btCancelar;

	public ConfigView() {

		add(this.btCriarMenu = new Button("criar menu", 50, 50, 50, 30));
		add(this.btCancelar = new Button("Cancelar", 150, 50, 50, 30));

	}

}