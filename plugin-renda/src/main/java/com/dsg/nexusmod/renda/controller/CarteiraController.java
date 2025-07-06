package com.dsg.nexusmod.renda.controller;

import java.util.ArrayList;
import java.util.List;

import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.entidade.Carteira;
import com.dsg.nexusmod.renda.model.CarteiraCommand;
import com.dsg.nexusmod.renda.model.CarteiraModel;
import com.dsg.nexusmod.renda.model.CarteirasModel;
import com.dsg.nexusmod.renda.view.TelaCarteiraPanel;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;

public class CarteiraController implements ControllerContent<TelaCarteiraPanel>, OnInit, OnChange, 
ModelObserver<Carteira>, CarteiraCommand {

	private TelaCarteiraPanel panel;
	
	private CarteirasModel model;
	
	@Override
	public TelaCarteiraPanel getPanel() {
		return panel;
	}
	
	@Override
	public void onInit(ControllerRoot contextApp) {
        
		model = new CarteirasModel(new ArrayList<CarteiraModel>());
		this.panel = new TelaCarteiraPanel(model);
	}
	
	@Override
	public void onChage(ControllerRoot contextApp) {
		List<CarteiraModel> lista = List.of(
                new CarteiraModel("Carteira Rico", "Renda variavel e FII", "30.000,00", "21%", this, this),
                new CarteiraModel("Carteira Santander", "ETF e Renda fixa", "200.000,00", "11%", this, this)
            );
        model.add(lista);
		
	}

	@Override
	public void update(Carteira carteira) {
		 System.out.printf("carteira modificada: %s -> %s%n","update", carteira.getNome());
		 model.notifyObservers();
	}

	@Override
	public void editar(CarteiraModel model) {
		System.out.printf("carteira modificada: %s -> %s%n", "editar", model.getNome());
	}

	@Override
	public void visualizar(CarteiraModel model) {
		System.out.printf("carteira modificada: %s -> %s%n","visualizar", model.getNome());
	}

	

}
