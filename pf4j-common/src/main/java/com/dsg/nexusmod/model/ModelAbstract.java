package com.dsg.nexusmod.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelAbstract<T> {
	
	private T model;
	private final List<ModelObserver<T>> observers = new ArrayList<>();
	
	public ModelAbstract(T model) {
		this.model = model;
	}

	// Obtém o modelo
	public T getModel() {
		return model;
	}
	
	// Adiciona um observador
    public void addObserver( ModelObserver<T> observer) {
        observers.add(observer);
    }

    // Remove um observador
    public void removeObserver(ModelObserver<T> observer) {
        observers.remove(observer);
    }

    // Notifica os observadores sobre mudanças na lista de usuários
    public void notifyObservers() {
        for (ModelObserver<T> observer : observers) {
            observer.update(model);
        }
    }
    
    

}
