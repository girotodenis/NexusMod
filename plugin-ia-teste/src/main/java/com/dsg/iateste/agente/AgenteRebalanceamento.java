package com.dsg.iateste.agente;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.dsg.iateste.model.Acao;
import com.dsg.iateste.model.Carteira;
import com.dsg.iateste.model.Decisao;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import okhttp3.OkHttpClient;

public class AgenteRebalanceamento {
	
    private final OpenAiService openAiService;

    public AgenteRebalanceamento(String apiKey) {
    	// Configurando o cliente HTTP com tempo limite personalizado
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(30)) // Tempo limite para conexão
                .readTimeout(Duration.ofSeconds(60))    // Tempo limite para leitura
                .writeTimeout(Duration.ofSeconds(60))   // Tempo limite para escrita
                .build();

        
        this.openAiService = new OpenAiService(apiKey, Duration.ZERO);
    }

    // Método principal para rebalancear a carteira
    public List<Decisao> rebalancearCarteira(Carteira carteira) {
        // 1. Criar o prompt com as informações da carteira
        String prompt = criarPrompt(carteira);

        // 2. Enviar o prompt para a OpenAI
        String resposta = enviarParaOpenAI(prompt);

        // 3. Processar a resposta e gerar decisões
        return processarResposta(resposta);
    }

    private String criarPrompt(Carteira carteira) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
        			Você é um assistente financeiro. 
        			Sua tarefa é rebalancear a seguinte carteira de investimentos:\n\n
        		""");

        prompt.append("Carteira de ações:\n");
        for (Acao acao : carteira.getAcoes()) {
            prompt.append("- Código: ").append(acao.getCodigo())
                  .append(", Quantidade: ").append(acao.getQuantidade())
                  .append(", Valor Médio: ").append(acao.getValorMedio())
                  .append(", Valor Atual: ").append(acao.getValorAtual())
                  .append(", Fundamentos: ").append(acao.getFundamentos())
                  .append("\n");
        }

        prompt.append("\nDinheiro disponível para investir: ").append(carteira.getDinheiroDisponivel()).append("\n");
        prompt.append("Dinheiro para resgatar: ").append(carteira.getDinheiroParaResgatar()).append("\n\n");

        prompt.append("Baseado nas informações acima, sugira quantidades para comprar ou vender cada ação, justificando cada decisão com base em fundamentos e estratégia de rebalanceamento.\n");
        return prompt.toString();
    }

    private String enviarParaOpenAI(String prompt) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4") // Use GPT-4 ou outro modelo compatível
                .messages(List.of(new ChatMessage("system", prompt)))
                .temperature(0.7)
                .maxTokens(500)
                .build();

        return openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    private List<Decisao> processarResposta(String resposta) {
       
    	System.out.println(resposta);
        List<Decisao> decisoes = new ArrayList<>();
        // Simulação de decisão baseada na resposta da OpenAI
        Decisao d1 = new Decisao();
        d1.setCodigo("PETR4");
        d1.setQuantidade(10); // Comprar 10 ações
        d1.setJustificativa("A ação está subvalorizada e tem fundamentos sólidos.");
        decisoes.add(d1);

        Decisao d2 = new Decisao();
        d2.setCodigo("VALE3");
        d2.setQuantidade(-5); // Vender 5 ações
        d2.setJustificativa("A ação está sobrevalorizada em relação aos fundamentos.");
        decisoes.add(d2);

        return decisoes;
    }
}