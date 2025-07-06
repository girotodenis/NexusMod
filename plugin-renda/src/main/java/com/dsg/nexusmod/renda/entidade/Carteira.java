package com.dsg.nexusmod.renda.entidade;

public class Carteira {
	private Long id;
	private String nome;
	private String descricao;
	private String valorInvestido;
	private String rentabilidade;

	public Carteira(Long id, String nome, String descricao, String valorInvestido, String rentabilidade) {
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
