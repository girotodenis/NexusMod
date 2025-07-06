package com.dsg.nexusmod.renda.model;

public interface CarteiraCommand {
	
	enum COMMAND{
		EDIT,
		VIEW
	}
	
	void editar(CarteiraModel model);
	void visualizar(CarteiraModel model);
	
}
