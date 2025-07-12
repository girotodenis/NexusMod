package com.dsg.nexusmod.renda.entidade;

public class Acao {
    private String codigo;
    private double valor;
    private String tipo;
    private double valorAbertura;

    public Acao(String codigo, double valor, String tipo, double valorAbertura) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipo = tipo;
        this.valorAbertura = valorAbertura;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValorAbertura() {
        return valorAbertura;
    }

    public double getDiferencaValor() {
        return valor - valorAbertura;
    }
}

