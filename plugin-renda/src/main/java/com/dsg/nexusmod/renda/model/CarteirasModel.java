package com.dsg.nexusmod.renda.model;

import java.util.List;

import com.dsg.nexusmod.model.ModelAbstract;

public class CarteirasModel extends ModelAbstract<List<CarteiraModel>> {

    public CarteirasModel(List<CarteiraModel> carteiras) {
        super(carteiras);
    }

	public void add(List<CarteiraModel> lista) {
		
		if(lista==null || lista.isEmpty()) {
			
			getModel().clear();
		}else {
			getModel().addAll(lista);
			getModel().sort((o1,o2)-> o1.getNome().compareTo(o2.getNome()));
		}
		notifyObservers();
	}
	
	public void add(CarteiraModel modal) {
		
		if(modal==null ) 
			return;
		
		getModel().add(modal);
		getModel().sort((o1,o2)-> o1.getNome().compareTo(o2.getNome()));
		notifyObservers();
	}

}
