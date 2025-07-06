package com.dsg.nexusmod.renda.model;

import java.util.Map;

import com.dsg.nexusmod.model.ModelAbstract;
import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.entidade.Carteira;

public class CarteiraModel extends ModelAbstract<Carteira>{

	private CarteiraCommand command = null;
	 private Validator<Carteira> validator;
	 
	public CarteiraModel(Carteira carteira) {
		super(carteira);
		this.validator = new CarteiraValidator();
	}
	
	public CarteiraModel(Long id, String nome, String descricao, String valorInvestido, String rentabilidade, 
			ModelObserver<Carteira> obsevador,CarteiraCommand command) {
		super(new Carteira(id, nome, descricao, valorInvestido, rentabilidade));
		addObserver(obsevador);
		this.command = command;
		this.validator = new CarteiraValidator();
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
	public void setNome(String nome) {
		getModel().setNome(nome);
	}
	
	public void setDescricao(String descricao) {
		getModel().setDescricao(descricao);
	}

	public String getValorInvestido() {
		return getModel().getValorInvestido();
	}

	public String getRentabilidade() {
		return getModel().getRentabilidade();
	}
	
	public Map<String, String> validate() {
        return validator.validate(getModel());
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
