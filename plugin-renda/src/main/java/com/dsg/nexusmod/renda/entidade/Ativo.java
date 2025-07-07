package com.dsg.nexusmod.renda.entidade;

public class Ativo {
	private String codigo;
    private String empresa;
    private double precoAtual;
    private int quantidade;
    private double precoMedio;
    private double rentabilidade;
    private String analise;
    
    
	public Ativo(String codigo, String empresa, double precoAtual, int quantidade, double precoMedio,
			double rentabilidade, String analise) {
		super();
		this.codigo = codigo;
		this.empresa = empresa;
		this.precoAtual = precoAtual;
		this.quantidade = quantidade;
		this.precoMedio = precoMedio;
		this.rentabilidade = rentabilidade;
		this.analise = analise;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public double getPrecoAtual() {
		return precoAtual;
	}
	public void setPrecoAtual(double precoAtual) {
		this.precoAtual = precoAtual;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(double precoMedio) {
		this.precoMedio = precoMedio;
	}
	public double getRentabilidade() {
		return rentabilidade;
	}
	public void setRentabilidade(double rentabilidade) {
		this.rentabilidade = rentabilidade;
	}
	public String getAnalise() {
		return analise;
	}
	public void setAnalise(String analise) {
		this.analise = analise;
	}
    
    
}
