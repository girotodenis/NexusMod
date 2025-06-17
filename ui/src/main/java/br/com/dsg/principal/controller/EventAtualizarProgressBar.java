package br.com.dsg.principal.controller;

public class EventAtualizarProgressBar {

	private int atual, total, valor = 0;
	private String texto = null;

	public EventAtualizarProgressBar(int atual, int total) {
		this(atual, total, null);
	}
	
	public EventAtualizarProgressBar(int atual, int total, String texto) {
		
		this.atual = atual;
		this.total = total;
		
		this.texto = texto;
		try {
			double x = (atual*100)/total;
			if(x>0){
				this.valor = Double.valueOf(x).intValue();
			}
		} catch (Exception e) {
		}
	}

	public int getAtual() {
		return atual;
	}

	public int getTotal() {
		return total;
	}

	public int getValor() {
		return valor;
	}

	public String getTexto() {
		return texto;
	}

	

}
