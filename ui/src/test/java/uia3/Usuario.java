package uia3;

import java.util.ArrayList;
import java.util.List;

import br.com.dsg.legui.controller.seguranca.UsuarioPrincipal;

public class Usuario implements UsuarioPrincipal {

	private String usuario;
	private List<String> permissoes = new ArrayList<String>();
	
	
	@Override
	public String usuario() {
		return "usuario xxxx";
	}

	@Override
	public boolean temPermissao(String permissao) {
		return permissoes.contains(permissao);
	}
	
	public void addPermissao(String acesso) {
		permissoes.add(acesso);
	}

}
