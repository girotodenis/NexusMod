package uia3.componentes;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.seguranca.SeguracaController;
import uia3.Usuario;

/**
 * @author Denis Giroto
 *
 */
public class LoginComPermissaoController extends SeguracaController<Usuario, LoginView>  {


	public LoginComPermissaoController(AbstractController<?> controlerPai) {
		super(controlerPai, new LoginView());
		
		registerAction(getPanel().btLogar, (event) -> {
			login("asasasa", "asasasa");
		});
	}
	
	@Override
	protected Usuario logar(String... parametros) {
		if(parametros[0].equals(parametros[1])) {
			Usuario usuario = new Usuario();
			usuario.addPermissao("configurar");
			return usuario;
		}
		return null;
	}
	
}
