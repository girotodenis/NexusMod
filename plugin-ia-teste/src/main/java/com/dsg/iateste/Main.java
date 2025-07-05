package com.dsg.iateste;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.dsg.iateste.agente.AgenteRebalanceamento;
import com.dsg.iateste.model.Acao;
import com.dsg.iateste.model.Carteira;
import com.dsg.iateste.model.Decisao;

import okhttp3.OkHttpClient;

public class Main {
    public static void main(String[] args) {
	// Configurar a carteira
    Carteira carteira = new Carteira();
    List<Acao> acoes = new ArrayList<>();

    Acao acao1 = new Acao();
    acao1.setCodigo("PETR4");
    acao1.setValorAtual(25.0);
    acao1.setValorMedio(20.0);
    acao1.setQuantidade(50);
    acao1.setFundamentos("Crescimento estável, bom lucro.");
    acoes.add(acao1);

    Acao acao2 = new Acao();
    acao2.setCodigo("VALE3");
    acao2.setValorAtual(80.0);
    acao2.setValorMedio(70.0);
    acao2.setQuantidade(30);
    acao2.setFundamentos("Volatilidade alta, fundamentos fracos.");
    acoes.add(acao2);

    carteira.setAcoes(acoes);
    carteira.setDinheiroDisponivel(1000.0);
    carteira.setDinheiroParaResgatar(500.0);

    // Criar o agente e executar o rebalanceamento
    String apiKey = "token"; // Substitua pela sua chave da OpenAI
    
    
    AgenteRebalanceamento agente = new AgenteRebalanceamento(apiKey);

    List<Decisao> decisoes = agente.rebalancearCarteira(carteira);

    // Imprimir as decisões
    for (Decisao decisao : decisoes) {
        System.out.println("Ação: " + decisao.getCodigo()
                + ", Quantidade: " + decisao.getQuantidade()
                + ", Justificativa: " + decisao.getJustificativa());
    }
}

  
}