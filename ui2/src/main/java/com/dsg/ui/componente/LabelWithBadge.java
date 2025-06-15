package com.dsg.ui.componente;

import javax.swing.*;
import java.awt.*;

public class LabelWithBadge extends JLabel {

    private static final long serialVersionUID = 466826492835743808L;
	private static final String TAB = "    ";
	private int badgeNumber; // Número a ser exibido no "badge"

    public LabelWithBadge(Icon icon, int badgeNumber) {
    	
        super(new ResizableIcon(icon, 15, 15)); // Configura o ícone para o JLabel
        this.badgeNumber = badgeNumber;
        setText(TAB);
    }

    public void setBadgeNumber(int badgeNumber) {
        this.badgeNumber = badgeNumber;
        repaint(); // Atualiza o componente para redesenhar o número
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (badgeNumber > 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Define a posição do badge
            int badgeSize = 15; // Tamanho do círculo
            int badgeX = 10; // Posição no canto superior direito
            int badgeY = 3;
            setText(TAB);
            // Desenha o círculo vermelho
            g2d.setColor(Color.RED);
            g2d.fillOval(badgeX, badgeY, badgeSize, badgeSize);

            // Desenha o número no círculo
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            String numberString = String.valueOf(badgeNumber);

            // Centraliza o número no círculo
            FontMetrics fm = g2d.getFontMetrics();
            int textX = badgeX + (badgeSize - fm.stringWidth(numberString)) / 2;
            int textY = badgeY + ((badgeSize - fm.getHeight()) / 2) + fm.getAscent();

            g2d.drawString(numberString, textX, textY);
        }
    }
}