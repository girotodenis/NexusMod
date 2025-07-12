package com.dsg.nexusmod.renda.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CadastroAcaoPanel extends JPanel {

    private ValidatedTextField campoCodigo;
    private ValidatedTextField campoDescricao;
    private JComboBox<String> campoTipo;
    private ValidatedTextArea campoFundamentos;
    public JButton botaoSalvar;
    public JButton botaoLimpar;

    private AcaoModel acaoModel;

    public CadastroAcaoPanel(AcaoModel acaoModel) {
        this.acaoModel = acaoModel;

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

        // Label Código
        JLabel labelCodigo = new JLabel("Código:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(labelCodigo, gbc);

        // Campo Código com validação
        campoCodigo = new ValidatedTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(campoCodigo, gbc);

        // Label Descrição
        JLabel labelDescricao = new JLabel("Descrição:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(labelDescricao, gbc);

        // Campo Descrição com validação
        campoDescricao = new ValidatedTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(campoDescricao, gbc);

        // Label Tipo
        JLabel labelTipo = new JLabel("Tipo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(labelTipo, gbc);

        // ComboBox Tipo
        campoTipo = new JComboBox<>(new String[]{"Tipo 1", "Tipo 2", "Tipo 3"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(campoTipo, gbc);

        // Label Fundamentos
        JLabel labelFundamentos = new JLabel("Fundamentos:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painel.add(labelFundamentos, gbc);

        // Campo Fundamentos com validação
        campoFundamentos = new ValidatedTextArea(5, 20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        painel.add(campoFundamentos, gbc);

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

    public void salvarAcao(Consumer<AcaoModel> consumer) {
        // Limpa erros anteriores
        campoCodigo.clearError();
        campoDescricao.clearError();
        campoFundamentos.clearError();

        String codigo = campoCodigo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String tipo = (String) campoTipo.getSelectedItem();
        String fundamentos = campoFundamentos.getText().trim();

        acaoModel.setCodigo(codigo);
        acaoModel.setDescricao(descricao);
        acaoModel.setTipo(tipo);
        acaoModel.setFundamentos(fundamentos);

        // Valida o modelo
        Map<String, String> errors = acaoModel.validate();

        if (!errors.isEmpty()) {
            // Exibe os erros nos campos correspondentes
            if (errors.containsKey("codigo")) {
                campoCodigo.setError(errors.get("codigo"));
            }
            if (errors.containsKey("descricao")) {
                campoDescricao.setError(errors.get("descricao"));
            }
            if (errors.containsKey("fundamentos")) {
                campoFundamentos.setError(errors.get("fundamentos"));
            }
            return; // Não prossegue com o salvamento
        }

        consumer.accept(acaoModel);
        // Notifica os observadores ou realiza ação de persistência
        limparCampos();
    }

    public void limparCampos() {
        campoCodigo.setText("");
        campoCodigo.clearError();
        campoDescricao.setText("");
        campoDescricao.clearError();
        campoFundamentos.setText("");
        campoFundamentos.clearError();
        campoCodigo.getTextField().requestFocus();
    }
}

class AcaoModel {
    private String codigo;
    private String descricao;
    private String tipo;
    private String fundamentos;

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFundamentos(String fundamentos) {
        this.fundamentos = fundamentos;
    }

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        if (codigo == null || codigo.isEmpty()) {
            errors.put("codigo", "O código é obrigatório.");
        }
        if (descricao == null || descricao.isEmpty()) {
            errors.put("descricao", "A descrição é obrigatória.");
        }
        if (fundamentos == null || fundamentos.isEmpty()) {
            errors.put("fundamentos", "Os fundamentos são obrigatórios.");
        }
        return errors;
    }
}

