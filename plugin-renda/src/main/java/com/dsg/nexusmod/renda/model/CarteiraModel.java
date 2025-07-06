package com.dsg.nexusmod.renda.model;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.entidade.Carteira;

public class CarteiraModel extends ModelAbstract<Carteira>{

	private CarteiraCommand command = null;
	
	public CarteiraModel(Carteira carteira) {
		super(carteira);
	}
	
	public CarteiraModel(String nome, String descricao, String valorInvestido, String rentabilidade, 
			ModelObserver<Carteira> obsevador,CarteiraCommand command) {
		super(new Carteira(nome, descricao, valorInvestido, rentabilidade));
		addObserver(obsevador);
		this.command = command;
	}
	
	public void setCommand(CarteiraCommand command) {
		this.command = command;
	}

	public String getNome() {
		return getModel().getNome();
	}

	public String getDescricao() {
		return getModel().getDescricao();
	}

	public String getValorInvestido() {
		return getModel().getValorInvestido();
	}

	public String getRentabilidade() {
		return getModel().getRentabilidade();
	}

	public void execute(CarteiraCommand.COMMAND event) {
		
		if(command == null || event == null) {
			System.out.println(" - "+command+" - "+event);
			return;
		}
		
		if(CarteiraCommand.COMMAND.EDIT == event) {
			command.editar(this);
		}else if(CarteiraCommand.COMMAND.VIEW == event) {
			command.visualizar(this);
		}
	}

	

}
