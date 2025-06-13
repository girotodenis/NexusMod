package br.com.dsg.legui.controller.seguranca;

public class Sessao {
	
	
	
	private static Sessao instance = new Sessao(false);
	
	private boolean loginAtivo;
	private boolean autenticado;
	private UsuarioPrincipal usuario;
	
	private Sessao(boolean autenticado) {
		this.autenticado = autenticado;
		this.loginAtivo = autenticado;
	}
	
	public static Sessao get(){
		return instance;
	}

	public boolean isAutenticado() {
		return loginAtivo ? autenticado : true;
	}

	public <T extends UsuarioPrincipal> T getUsuario() {
		return (T) usuario;
	}
	
	public <T extends UsuarioPrincipal> void setUsuario(T usuario) {
		this.usuario = usuario;
	}

	public boolean isLoginAtivo() {
		return loginAtivo;
	}

	public void setLoginAtivo(boolean loginAtivo) {
		this.loginAtivo = loginAtivo;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	

}
