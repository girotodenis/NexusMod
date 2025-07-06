package com.dsg.nexusmod.renda.entidade;

public class Carteira {
	private String nome;
	private String descricao;
	private String valorInvestido;
	private String rentabilidade;

	public Carteira(String nome, String descricao, String valorInvestido, String rentabilidade) {
		this.nome = nome;
		this.descricao = descricao;
		this.valorInvestido = valorInvestido;
		this.rentabilidade = rentabilidade;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getValorInvestido() {
		return valorInvestido;
	}

	public String getRentabilidade() {
		return rentabilidade;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setValorInvestido(String valorInvestido) {
		this.valorInvestido = valorInvestido;
	}

	public void setRentabilidade(String rentabilidade) {
		this.rentabilidade = rentabilidade;
	}

}
