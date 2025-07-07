package com.dsg.nexusmod.renda.model;

import java.util.List;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.renda.entidade.Ativo;
import com.dsg.nexusmod.renda.entidade.Carteira;

public class AtivosModel extends ModelAbstract<List<Ativo>> {

	private Carteira carteira;
	
    public AtivosModel(List<Ativo> ativos) {
        super(ativos);
    }
    
    

    public Carteira getCarteira() {
		return carteira;
	}



	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}



	public void adicionar(Ativo ativo) {
        getModel().add(ativo);
        notifyObservers();
    }

    public void remover(Ativo ativo) {
        getModel().remove(ativo);
        notifyObservers();
    }

    public void atualizar() {
        notifyObservers();
    }
}