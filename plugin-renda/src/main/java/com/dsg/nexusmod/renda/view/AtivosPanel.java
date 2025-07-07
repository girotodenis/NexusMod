package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.entidade.Ativo;
import com.dsg.nexusmod.renda.model.AtivosModel;

public class AtivosPanel extends JPanel implements ModelObserver<List<Ativo>> {

    private final AtivosModel ativosModel;
    private final JPanel listaAtivosPanel;

    public AtivosPanel(AtivosModel ativosModel) {
        this.ativosModel = ativosModel;
        this.ativosModel.addObserver(this);

        setLayout(new BorderLayout());

        // Topo com título e botões
        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topoPanel.add(new JLabel("Carteira: "+ativosModel.getCarteira().getNome()));
        topoPanel.add(Box.createHorizontalStrut(20));
        topoPanel.add(new JButton("Adicionar"));
        topoPanel.add(new JButton("Importar Notas"));
        add(topoPanel, BorderLayout.NORTH);

        // Painel para lista de ativos
        listaAtivosPanel = new JPanel();
        listaAtivosPanel.setLayout(new BoxLayout(listaAtivosPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listaAtivosPanel);
        add(scrollPane, BorderLayout.CENTER);

        renderizarAtivos(ativosModel.getModel());
    }

    private void renderizarAtivos(List<Ativo> ativos) {
        listaAtivosPanel.removeAll();
        for (Ativo ativo : ativos) {
            listaAtivosPanel.add(criarPainelAtivo(ativo));
        }
        listaAtivosPanel.revalidate();
        listaAtivosPanel.repaint();
    }

    private JPanel criarPainelAtivo(Ativo ativo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Cabeçalho
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.add(new JLabel(ativo.getCodigo()), BorderLayout.WEST);
        cabecalho.add(new JLabel("Preço: " + ativo.getPrecoAtual()), BorderLayout.EAST);
        painel.add(cabecalho, BorderLayout.NORTH);

        // Informações e Análise
        JPanel detalhes = new JPanel(new GridLayout(1, 2));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(ativo.getEmpresa()));
        infoPanel.add(new JLabel("Quantidade: " + ativo.getQuantidade()));
        infoPanel.add(new JLabel("Preço médio: " + ativo.getPrecoMedio()));
        infoPanel.add(new JLabel("Rentabilidade: " + ativo.getRentabilidade() + "%"));

        JPanel analiseEBotoes = new JPanel(new BorderLayout());
        JTextArea analiseArea = new JTextArea(ativo.getAnalise());
        analiseArea.setLineWrap(true);
        analiseArea.setWrapStyleWord(true);
        analiseArea.setEditable(false);
        analiseEBotoes.add(analiseArea, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(new JButton("Comprar"));
        botoesPanel.add(new JButton("Vender"));
        analiseEBotoes.add(botoesPanel, BorderLayout.SOUTH);

        detalhes.add(infoPanel);
        detalhes.add(analiseEBotoes);

        painel.add(detalhes, BorderLayout.CENTER);

        return painel;
    }

    @Override
    public void update(List<Ativo> ativos) {
        renderizarAtivos(ativos);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Carteira Rico");

            // Cria o modelo
            AtivosModel model = new AtivosModel(new ArrayList<>());

            // Popula exemplo
            Ativo ativo = new Ativo("BBDC3", "Bradesco", 26.56, 100, 25.66, 5.0,
                    "Análise feita pela IA...");
            model.adicionar(ativo);

            Ativo ativo2 = new Ativo("CMIG4", "Cemig energia", 26.56, 100, 25.66, 5.0,
                    "Outra análise feita pela IA...");
            model.adicionar(ativo2);

            AtivosPanel painel = new AtivosPanel(model);
            frame.setContentPane(painel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setVisible(true);
        });
    }
}