package uia3.componentes;

import org.apache.log4j.Logger;

import br.com.dsg.legui.controller.AbstractController;
import br.com.dsg.legui.controller.seguranca.SeguracaController;
import uia3.Usuario;

/**
 * @author Denis Giroto
 *
 */
public class LoginSemPermissaoController extends SeguracaController<Usuario, LoginView>  {

	private final static Logger LOG = Logger.getLogger(LoginSemPermissaoController.class);

	public LoginSemPermissaoController(AbstractController<?> controlerPai) {
		super(controlerPai, new LoginView());
		
		registerAction(getPanel().btLogar, (event) -> {
			LOG.debug("sdsdsdsdsds");
			login("asasasa", "asasasa");
//			if(getSession().isAutenticado()) {
//				fireEvent(new EventAtualizarConteudoEvento(new HomeController(this)));
//			}
		});
	}
	
	@Override
	protected Usuario logar(String... parametros) {
		if(parametros[0].equals(parametros[1])) {
			Usuario usuario = new Usuario();
			usuario.addPermissao("configurar2");
			return usuario;
		}
		return null;
	}
	
}
