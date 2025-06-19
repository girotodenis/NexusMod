package com.dsg.ui.componente;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.UIManager;

public class ResizableIcon implements Icon {
    private final Icon originalIcon;
    private final int width;
    private final int height;

    public ResizableIcon(Icon originalIcon, int width, int height) {
    	
        this.originalIcon = originalIcon==null ? UIManager.getIcon("Menu.arrowIcon") : originalIcon;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Redimensiona o ícone ao desenhá-lo
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha o ícone redimensionado
        g2d.translate(x, y);
        g2d.scale((double) width / originalIcon.getIconWidth(), (double) height / originalIcon.getIconHeight());
        originalIcon.paintIcon(c, g2d, 0, 0);
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }


}
