package br.com.dsg.legui.controller.seguranca;

import org.liquidengine.legui.component.Panel;

import br.com.dsg.legui.controller.AbstractController;

public abstract class SeguracaController<U extends UsuarioPrincipal, T extends Panel> extends AbstractController<T>{

	private Seguranca callback;
	
	public SeguracaController(AbstractController<?> controllerPai, T panel) {
		super(controllerPai, panel);
	}

	protected boolean login(String... parametros) {
		
		U usuario = logar(parametros);
		Sessao.get().setLoginAtivo(true);
		Sessao.get().setAutenticado( usuario != null );
		if(Sessao.get().isAutenticado()) {
			callback.call(usuario);
			Sessao.get().setUsuario(usuario);
		}
		return Sessao.get().isAutenticado();
	}
	
	protected abstract U logar(String... parametros) ;

	public void setCallback(Seguranca<U> callback) {
		this.callback = callback;
	}

	

}
