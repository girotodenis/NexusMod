package com.dsg.iateste.model;

//Representa uma ação na carteira
public class Acao {
	private String codigo; // Ex.: "PETR4"
	private double valorAtual; // Valor atual da ação no dia
	private double valorMedio; // Valor médio pago pelo investidor
	private int quantidade; // Quantidade de ações na carteira
	private String fundamentos; // Ex.: "Crescimento estável, lucros crescentes."

	// Getters e Setters
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(double valorAtual) {
		this.valorAtual = valorAtual;
	}

	public double getValorMedio() {
		return valorMedio;
	}

	public void setValorMedio(double valorMedio) {
		this.valorMedio = valorMedio;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getFundamentos() {
		return fundamentos;
	}

	public void setFundamentos(String fundamentos) {
		this.fundamentos = fundamentos;
	}
}
