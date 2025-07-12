package com.dsg.nexusmod.renda.controller;

import com.dsg.nexusmod.controller.AbstractEventListener;
import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.renda.model.CarteiraModel;
import com.dsg.nexusmod.renda.view.CadastroCarteiraPanel;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.nexusmod.ui.TaskNotificationType;

public class CadastroCarteiraController implements ControllerContent<CadastroCarteiraPanel>, OnInit, OnChange{

	CadastroCarteiraPanel panel;
	CarteiraController pai;
	CarteiraModel model;
	
	final AbstractEventListener<String> eventListenerMeuPau =  (data)->{
//		log.error("meu pau:"+data);
		System.out.println("meu event cadastro:"+data+" - "+pai.valor);
	};
	
	@Override
	public CadastroCarteiraPanel getPanel() {
		return panel;
	}

	
	public CadastroCarteiraController(CarteiraController pai, CarteiraModel model) {
		this.model = model;
		this.pai = pai;
	}

	@Override
	public void onInit(ControllerRoot contextApp) {	
		this.panel = new CadastroCarteiraPanel(model);
		
		this.panel.botaoSalvar.addActionListener(e ->{
			
			this.panel.salvarCarteira((model)->{
				contextApp.addNotification("Salvou com sucesso", TaskNotificationType.INFO);
				
				boolean isCreate = model.getModel().getId()==null;
				
				//salva banco
				
				if(isCreate) {
					model.getModel().setRentabilidade("0%");
					model.getModel().setValorInvestido("0");
					model.getModel().setId(Long.valueOf(this.model.getNome().hashCode()));
					pai.model.add(this.model);
				}
				
				contextApp.showContent(pai);
			});
		});
		
		this.panel.botaoLimpar.addActionListener(e -> {
			panel.limparCampos();
			contextApp.showContent(pai);
		});
		
		this.model.notifyObservers();
		
		contextApp.registerEvent("meuEVENT", eventListenerMeuPau);
	}

	@Override
	public void onChage(ControllerRoot contextApp) {
		
	}

	

}
