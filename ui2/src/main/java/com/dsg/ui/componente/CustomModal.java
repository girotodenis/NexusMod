package com.dsg.ui.componente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CustomModal extends JPanel {
    private JPanel headerPanel; // Cabeçalho
    private JPanel contentPanel; // Conteúdo principal
    private JPanel footerPanel; // Rodapé
    private JLabel titleLabel; // Título da modal
    private JButton closeButton; // Botão para fechar
    private Point initialClick; // Para movimentar o modal
    private JPanel modalPanel; // Painel principal da modal

    public CustomModal() {
        setLayout(null); // Layout absoluto
        setBackground(new Color(0, 0, 0, 150)); // Fundo semitransparente

        // Painel central da modal inicializado, mas sem configuração
        modalPanel = new JPanel(null); // Layout absoluto no modal principal
        modalPanel.setBackground(Color.WHITE);
        modalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(modalPanel);

        // Cabeçalho inicializado, mas sem configuração
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(200, 200, 200));
        headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); // Cursor de movimento

        // Título da modal
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Espaço à esquerda
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Botão de fechar
        closeButton = new JButton("X");
        closeButton.setFocusable(false);
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setContentAreaFilled(false); // Remove fundo do botão
        closeButton.addActionListener(e -> setVisible(false)); // Esconde a modal ao clicar
        headerPanel.add(closeButton, BorderLayout.EAST);

        // Adiciona funcionalidade para mover o modal ao clicar e arrastar no cabeçalho
        addDragFunctionality(headerPanel, modalPanel);

        // Adiciona o cabeçalho ao modal
        modalPanel.add(headerPanel);
    }

    /**
     * Método para abrir a modal dinamicamente
     * 
     * @param title         Título da modal
     * @param content       Conteúdo do painel
     * @param size          Tamanho da modal (largura e altura)
     * @param footerButtons Botões do rodapé
     * @param position      Posição da modal ("center", "top-right", "bottom-right", "bottom-left", "top-left")
     */
    public void openModal(String title, JPanel content, Dimension size, JButton[] footerButtons, String position) {
        // Configura o título
        titleLabel.setText(title);

        // Configura o tamanho do modal
        modalPanel.setSize(size);

        // Calcula a posição do modal com base na posição desejada
        int x = 0, y = 0;

        switch (position.toLowerCase()) {
            case "center":
                x = (getWidth() - size.width) / 2;
                y = (getHeight() - size.height) / 2;
                break;
            case "top-right":
                x = getWidth() - size.width - 10;
                y = 10;
                break;
            case "bottom-right":
                x = getWidth() - size.width - 10;
                y = getHeight() - size.height - 10;
                break;
            case "bottom-left":
                x = 10;
                y = getHeight() - size.height - 10;
                break;
            case "top-left":
                x = 10;
                y = 10;
                break;
            default:
                throw new IllegalArgumentException("Posição inválida: " + position);
        }

        modalPanel.setLocation(x, y);

        // Configura o cabeçalho
        headerPanel.setBounds(0, 0, size.width, 40);

        // Configura o conteúdo
        if (contentPanel != null) {
            modalPanel.remove(contentPanel); // Remove o conteúdo anterior, se houver
        }
        contentPanel = content;
        contentPanel.setBounds(0, 40, size.width, size.height - 90); // Espaço entre cabeçalho e rodapé
        modalPanel.add(contentPanel);

        // Configura o rodapé
        if (footerPanel != null) {
            modalPanel.remove(footerPanel); // Remove o rodapé anterior, se houver
        }
        footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alinha os botões à direita
        footerPanel.setBackground(new Color(230, 230, 230));
        footerPanel.setBounds(0, size.height - 50, size.width, 50);

        if (footerButtons != null) {
            for (JButton button : footerButtons) {
                footerPanel.add(button);
            }
        }
        modalPanel.add(footerPanel);

        // Atualiza o layout e exibe a modal
        modalPanel.revalidate();
        modalPanel.repaint();
        setVisible(true);
    }

    /**
     * Adiciona funcionalidade de arrastar para o cabeçalho da modal
     */
    private void addDragFunctionality(JPanel header, JPanel modalPanel) {
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Calcula a diferença de movimento
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Atualiza a posição do modal
                Point modalLocation = modalPanel.getLocation();
                modalPanel.setLocation(modalLocation.x + xMoved, modalLocation.y + yMoved);
            }
        });
    }

    /**
     * Testa a modal em um JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Cria a janela principal
            JFrame frame = new JFrame("Teste de Modal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(null);

            // Painel de fundo
            JPanel backgroundPanel = new JPanel();
            backgroundPanel.setLayout(null);
            frame.setContentPane(backgroundPanel);

            // Cria a modal
            CustomModal customModal = new CustomModal();
            customModal.setBounds(0, 0, 800, 600); // Ocupa toda a área da janela
            customModal.setVisible(false); // Inicialmente invisível
            backgroundPanel.add(customModal);

            // Botão para abrir a modal
            JButton openModalButton = new JButton("Abrir Modal");
            openModalButton.setBounds(50, 50, 150, 30);
            openModalButton.addActionListener(e -> {
                // Conteúdo do modal
                JPanel content = new JPanel();
                content.setLayout(new BorderLayout());
                content.add(new JLabel("Este é o conteúdo da modal", SwingConstants.CENTER), BorderLayout.CENTER);

                // Botões do rodapé
                JButton button1 = new JButton("Salvar");
                JButton button2 = new JButton("Cancelar");
                JButton[] footerButtons = {button1, button2};

                // Abre a modal
                customModal.openModal("Minha Modal", content, new Dimension(400, 300), footerButtons, "center");
            });
            backgroundPanel.add(openModalButton);

            // Exibe a janela
            frame.setVisible(true);
        });
    }
}

