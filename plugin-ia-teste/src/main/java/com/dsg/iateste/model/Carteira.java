package com.dsg.iateste.model;

import java.util.List;

//Representa a carteira de investimentos
public class Carteira {
	private List<Acao> acoes;
	private double dinheiroDisponivel; // Dinheiro para investir
	private double dinheiroParaResgatar; // Dinheiro que pode ser resgatado

	// Getters e Setters
	public List<Acao> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Acao> acoes) {
		this.acoes = acoes;
	}

	public double getDinheiroDisponivel() {
		return dinheiroDisponivel;
	}

	public void setDinheiroDisponivel(double dinheiroDisponivel) {
		this.dinheiroDisponivel = dinheiroDisponivel;
	}

	public double getDinheiroParaResgatar() {
		return dinheiroParaResgatar;
	}

	public void setDinheiroParaResgatar(double dinheiroParaResgatar) {
		this.dinheiroParaResgatar = dinheiroParaResgatar;
	}
}