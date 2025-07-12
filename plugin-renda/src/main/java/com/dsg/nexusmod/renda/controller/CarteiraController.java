package com.dsg.nexusmod.renda.controller;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.AbstractEventListener;
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

	private static final Logger log = LoggerFactory.getLogger(CarteiraController.class);
	
	private TelaCarteiraPanel panel;
	static int i = 0;
	int valor = 0;
	CarteirasModel model;
	ControllerRoot contextApp;
	
	final AbstractEventListener<String> eventListenerMeuPau =  (data)->{
//		log.error("meu pau:"+data);
		System.out.println("meu event:"+data+" - "+valor);
	};
	
	public CarteiraController() {
		System.out.println("CarteiraController"+ (valor=++i));
	}
	
	@Override
	public TelaCarteiraPanel getPanel() {
		return panel;
	}
	
	@Override
	public void onInit(ControllerRoot contextApp) {
		this.contextApp=contextApp;
		
		model = new CarteirasModel(new ArrayList<CarteiraModel>());
		
		this.panel = new TelaCarteiraPanel(model);
		
		contextApp.registerEvent("meuEVENT", eventListenerMeuPau);
	}
	
	@Override
	public void onChage(ControllerRoot contextApp) {
		
		
		panel.btnCriarCarteira.addActionListener(e->{
			contextApp.fireEvent("meuEVENT", "tem 20cm");
			System.out.println(valor);
			contextApp.showContent(new CadastroCarteiraController(this, new CarteiraModel(null, "", "", "", "", this, this)));
		});
		
		List<CarteiraModel> lista = List.of(
				new CarteiraModel(1l,"Carteira Rico", "Renda variavel e FII", "30.000,00", "21%", this, this),
				new CarteiraModel(2l, "Carteira Santander", "ETF e Renda fixa", "200.000,00", "11%", this, this)
				);
		model.add(lista);
		
		model.notifyObservers();
	}

	@Override
	public void update(Carteira carteira) {
		 System.out.printf("carteira modificada: %s -> %s%n","update", carteira.getNome());
		 model.notifyObservers();
	}

	@Override
	public void editar(CarteiraModel model) {
		System.out.printf("carteira modificada: %s -> %s%n", "editar", model.getNome());
		contextApp.showContent(new CadastroCarteiraController(this, model));
	}

	@Override
	public void visualizar(CarteiraModel model) {
		System.out.printf("carteira modificada: %s -> %s%n","visualizar", model.getNome());
	}

	

}
