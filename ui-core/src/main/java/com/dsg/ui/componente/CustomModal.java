package com.dsg.ui.componente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dsg.ui.util.UIUtils;

public class CustomModal extends JPanel {
    
	private static final long serialVersionUID = -4263943549113847042L;
    
	private JPanel headerPanel; // Cabeçalho
    private JPanel contentPanel; // Conteúdo principal
//    private JPanel footerPanel; // Rodapé
    private JLabel titleLabel; // Título da modal
//    private JButton closeButton; // Botão para fechar
    private JPanel modalPanel; // Painel principal da modal

    public CustomModal(Color color) {
        setLayout(null); // Layout absoluto
        setBackground(new Color(0, 0, 0, 0)); // Fundo semitransparente
        
        // Painel central da modal inicializado, mas sem configuração
        modalPanel = new JPanel(null); // Layout absoluto no modal principal
        modalPanel.setBackground(color);
        modalPanel.setBorder(BorderFactory.createLineBorder(UIUtils.ajustColor(color, 130), 3));
        add(modalPanel);

        // Cabeçalho inicializado, mas sem configuração
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIUtils.ajustColor(color, 130));

        // Título da modal
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Espaço à esquerda
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Botão de fechar
//        closeButton = new JButton("X");
//        closeButton.setFocusable(false);
//        closeButton.setPreferredSize(new Dimension(20, 20));
//        closeButton.setBorder(BorderFactory.createEmptyBorder());
//        closeButton.setContentAreaFilled(false); // Remove fundo do botão
//        closeButton.addActionListener(e -> setVisible(false)); // Esconde a modal ao clicar
//        headerPanel.add(closeButton, BorderLayout.EAST);

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
//        if (footerPanel != null) {
//            modalPanel.remove(footerPanel); // Remove o rodapé anterior, se houver
//        }
//        footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alinha os botões à direita
//        footerPanel.setBackground(new Color(230, 230, 230));
//        footerPanel.setBounds(0, size.height - 50, size.width, 50);

//        if (footerButtons != null) {
//            for (JButton button : footerButtons) {
//                footerPanel.add(button);
//            }
//        }
        //modalPanel.add(footerPanel);

        // Atualiza o layout e exibe a modal
        modalPanel.revalidate();
        modalPanel.repaint();
        setVisible(true);
    }

   
}

