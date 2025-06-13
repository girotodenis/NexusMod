package br.com.dsg.legui.controller.seguranca;

public interface UsuarioPrincipal {
	
	String usuario();
	boolean temPermissao(String autorizacao);

}
