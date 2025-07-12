package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.dsg.nexusmod.renda.entidade.Acao;

public class TelaAcoesPanel extends JPanel {
    private List<Acao> listaAcoes; // Lista de ações
    private JPanel painelGrid; // Painel para exibir os cards
    private JComboBox<String> comboFiltro; // Filtro por tipo
    private JComboBox<String> comboOrdenacao; // Ordenação

    public TelaAcoesPanel(int colunas) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Cor de fundo do painel principal

        listaAcoes = new ArrayList<>();

        // Painel superior com o botão de cadastro, filtros e ordenação
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelSuperior.setBackground(new Color(240, 240, 240)); // Cor de fundo do painel superior

        JButton btnCadastrar = new JButton("Cadastrar Ação");
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Tipo 1", "Tipo 2", "Tipo 3"});
        comboOrdenacao = new JComboBox<>(new String[]{"Código", "Valor de Alta"});

        painelSuperior.add(comboFiltro);
        painelSuperior.add(comboOrdenacao);
        painelSuperior.add(btnCadastrar);

        // Painel central com a grade das ações
        painelGrid = new JPanel(new GridLayout(0, colunas, 15, 15)); // Grade com número de colunas configurável
        painelGrid.setBackground(new Color(220, 220, 220)); // Cor de fundo da grade

        JScrollPane scrollPane = new JScrollPane(painelGrid);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(painelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Ação do botão de cadastro
        btnCadastrar.addActionListener(e -> abrirTelaCadastro());

        // Ação de filtro/ordenação
        comboFiltro.addActionListener(e -> atualizarLista());
        comboOrdenacao.addActionListener(e -> atualizarLista());

        // Exemplo de dados para teste
        listaAcoes.add(new Acao("ACAO1", 110.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO2", 95.00, "Tipo 2", 100.00));
        listaAcoes.add(new Acao("ACAO3", 100.00, "Tipo 3", 100.00));
        listaAcoes.add(new Acao("ACAO4", 120.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO1", 110.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO2", 95.00, "Tipo 2", 100.00));
        listaAcoes.add(new Acao("ACAO3", 100.00, "Tipo 3", 100.00));
        listaAcoes.add(new Acao("ACAO4", 120.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO1", 110.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO2", 95.00, "Tipo 2", 100.00));
        listaAcoes.add(new Acao("ACAO3", 100.00, "Tipo 3", 100.00));
        listaAcoes.add(new Acao("ACAO4", 120.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO1", 110.00, "Tipo 1", 100.00));
        listaAcoes.add(new Acao("ACAO2", 95.00, "Tipo 2", 100.00));
        listaAcoes.add(new Acao("ACAO3", 100.00, "Tipo 3", 100.00));
        listaAcoes.add(new Acao("ACAO4", 120.00, "Tipo 1", 100.00));
        atualizarLista();
    }

    private void abrirTelaCadastro() {
        JOptionPane.showMessageDialog(this, "Abrir tela de cadastro de ação.");
        // Aqui você pode implementar a lógica para abrir outra tela de cadastro.
    }

    private void atualizarLista() {
        painelGrid.removeAll();

        // Aplicar filtro
        String filtro = (String) comboFiltro.getSelectedItem();
        List<Acao> acoesFiltradas = listaAcoes.stream()
                .filter(acao -> filtro.equals("Todos") || acao.getTipo().equals(filtro))
                .collect(Collectors.toList());

        // Aplicar ordenação
        String ordenacao = (String) comboOrdenacao.getSelectedItem();
        if (ordenacao.equals("Código")) {
            acoesFiltradas.sort(Comparator.comparing(Acao::getCodigo));
        } else if (ordenacao.equals("Valor de Alta")) {
            acoesFiltradas.sort(Comparator.comparing(Acao::getDiferencaValor).reversed());
        }

        // Adicionar os cards ao painel
        for (Acao acao : acoesFiltradas) {
            painelGrid.add(criarPanelComCard(acao));
        }

        painelGrid.revalidate();
        painelGrid.repaint();
    }

    private JPanel criarPanelComCard(Acao acao) {
        // Painel pai que será redimensionado, mas mantém o card fixo no centro
        JPanel panelContainer = new JPanel(new GridBagLayout());
        panelContainer.setBackground(new Color(220, 220, 220)); // Fundo igual ao painelGrid

        // O card em si
        JPanel card = criarCardAcao(acao);

        // Adiciona o card fixo ao centro do painel pai
        panelContainer.add(card);

        return panelContainer;
    }

    private JPanel criarCardAcao(Acao acao) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(4, 1));
        card.setPreferredSize(new Dimension(180, 120)); // Tamanho fixo do card
        card.setBackground(new Color(235, 235, 235)); // Cor de fundo do card
        card.setBorder(new LineBorder(getCorBorda(acao), 3));

        // Adicionar informações ao card
        card.add(new JLabel("Código: " + acao.getCodigo()));
        card.add(new JLabel("Valor: R$ " + acao.getValor()));
        card.add(new JLabel("Tipo: " + acao.getTipo()));
        card.add(new JLabel("Abertura: R$ " + acao.getValorAbertura()));

        // Ação ao clicar no card
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirDetalhesAcao(acao);
            }
        });

        return card;
    }

    private Color getCorBorda(Acao acao) {
        if (acao.getValor() > acao.getValorAbertura()) {
            return Color.GREEN;
        } else if (acao.getValor() < acao.getValorAbertura()) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }

    private void abrirDetalhesAcao(Acao acao) {
        JOptionPane.showMessageDialog(this, "Detalhes da ação:\n" +
                "Código: " + acao.getCodigo() + "\n" +
                "Valor: R$ " + acao.getValor() + "\n" +
                "Tipo: " + acao.getTipo() + "\n" +
                "Abertura: R$ " + acao.getValorAbertura());
    }
}