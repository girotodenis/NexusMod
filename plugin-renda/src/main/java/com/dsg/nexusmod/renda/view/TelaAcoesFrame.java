package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TelaAcoesFrame extends JFrame {
    public TelaAcoesFrame() {
        setTitle("Tela de Ações");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centralizar a janela
        setLayout(new BorderLayout());

        // Adicionar o JPanel principal
        TelaAcoesPanel telaAcoesPanel = new TelaAcoesPanel(5);
        add(telaAcoesPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaAcoesFrame frame = new TelaAcoesFrame();
            frame.setVisible(true);
        });
    }
}
