package com.dsg.iateste.model;

//Representa uma decisão de compra/venda
public class Decisao {
	private String codigo; // Código da ação
	private int quantidade; // Quantidade a comprar/vender (positivo = comprar, negativo = vender)
	private String justificativa; // Razão para a decisão

// Getters e Setters
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
}