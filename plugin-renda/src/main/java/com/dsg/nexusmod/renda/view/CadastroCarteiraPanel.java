package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.entidade.Carteira;
import com.dsg.nexusmod.renda.model.CarteiraModel;

public class CadastroCarteiraPanel extends JPanel implements ModelObserver<Carteira> {
    
    private ValidatedTextField campoNome;
    private ValidatedTextArea campoDescricao;
    public JButton botaoSalvar;
    public JButton botaoLimpar;
    
    private CarteiraModel carteiraModel;
    
    public CadastroCarteiraPanel(CarteiraModel carteiraModel) {
        
        this.carteiraModel = carteiraModel;
        this.carteiraModel.addObserver(this);
        
        // Configura o layout
        setLayout(new BorderLayout());
        
        // Cria o painel de formulário
        JPanel painelFormulario = criarPainelFormulario();
        add(painelFormulario, BorderLayout.CENTER);
        
        // Cria o painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Label Nome
        JLabel labelNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(labelNome, gbc);
        
        // Campo Nome com validação
        campoNome = new ValidatedTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(campoNome, gbc);
        
        // Label Descrição
        JLabel labelDescricao = new JLabel("Descrição:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0;
        painel.add(labelDescricao, gbc);
        
        // Campo Descrição com validação
        campoDescricao = new ValidatedTextArea(5, 20);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        painel.add(campoDescricao, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        botaoLimpar = new JButton("Limpar");
        botaoSalvar = new JButton("Salvar");
        
        painel.add(botaoLimpar);
        painel.add(botaoSalvar);
        
        return painel;
    }
    
    public void salvarCarteira(Consumer<CarteiraModel> consumer) {
        // Limpa erros anteriores
        campoNome.clearError();
        campoDescricao.clearError();
        
        String nome = campoNome.getText().trim();
        String descricao = campoDescricao.getText().trim();
        
        carteiraModel.setDescricao(descricao);
        carteiraModel.setNome(nome);
        
        // Valida o modelo
        Map<String, String> errors = carteiraModel.validate();
        
        if (!errors.isEmpty()) {
            // Exibe os erros nos campos correspondentes
            if (errors.containsKey("nome")) {
                campoNome.setError(errors.get("nome"));
            }
            if (errors.containsKey("descricao")) {
                campoDescricao.setError(errors.get("descricao"));
            }
            return; // Não prossegue com o salvamento
        }
        
        consumer.accept(carteiraModel);
        // Notifica os observadores
        carteiraModel.notifyObservers();
        
        limparCampos();
    }
    
    public void limparCampos() {
        campoNome.setText("");
        campoNome.clearError();
        campoDescricao.setText("");
        campoDescricao.clearError();
        campoNome.getTextField().requestFocus();
    }
    
    @Override
    public void update(Carteira carteira) {
    	campoNome.setText(carteira.getNome());
    	campoDescricao.setText(carteira.getDescricao());
    }
    
}