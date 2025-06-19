package com.dsg.nexusmod.teste2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColorGridPanelView extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	public JButton button = new JButton("voltar");

	public ColorGridPanelView(int columns) {
		 // Gera uma lista de cores dinamicamente
        List<Color> dynamicColors = generateColorsWithShades();
		 // Calcula o número de linhas necessárias
        int rows = (int) Math.ceil((double) dynamicColors.size() / columns);

        // Configura o layout do painel
        setLayout(new GridLayout(rows, columns, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(button);

        // Adiciona as células de cores
        for (Color color : dynamicColors) {
            JPanel colorPanel = createColorPanel(color);
            add(colorPanel);
        }
	}
	
	 /**
     * Gera tons para cada cor padrão do Swing, variando 15 tons mais claros e 15 tons mais escuros.
     *
     * @return Uma lista de cores geradas.
     */
    public static List<Color> generateColorsWithShades() {
        // Lista de cores padrão do Swing
        List<Color> baseColors = List.of(
                Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
                Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,
                Color.RED, Color.WHITE, Color.YELLOW
        );

        List<Color> colorsWithShades = new ArrayList<>();

        // Para cada cor padrão, gera 15 tons mais claros e 15 tons mais escuros
        for (Color baseColor : baseColors) {
            colorsWithShades.addAll(generateShades(baseColor, 50)); // Gera os tons
        }

        return colorsWithShades;
    }

    /**
     * Gera tons mais claros e mais escuros para uma cor base.
     *
     * @param baseColor A cor base para gerar os tons.
     * @param numShades Número de tons mais claros e mais escuros.
     * @return Uma lista de tons gerados para a cor base.
     */
    public static List<Color> generateShades(Color baseColor, int numShades) {
        List<Color> shades = new ArrayList<>();

        // Valores RGB da cor base
        int baseRed = baseColor.getRed();
        int baseGreen = baseColor.getGreen();
        int baseBlue = baseColor.getBlue();

        // Incremento para clarear ou escurecer
        int step = 255 / numShades;

        // Gera tons mais escuros
        for (int i = 1; i <= numShades; i++) {
            int red = Math.max(baseRed - (i * step), 0);    // Garante que o valor não fique abaixo de 0
            int green = Math.max(baseGreen - (i * step), 0);
            int blue = Math.max(baseBlue - (i * step), 0);
            shades.add(new Color(red, green, blue));
        }

        // Adiciona a cor base no meio da lista (opcional)
        shades.add(baseColor);

        // Gera tons mais claros
        for (int i = 1; i <= numShades; i++) {
            int red = Math.min(baseRed + (i * step), 255); // Garante que o valor não fique acima de 255
            int green = Math.min(baseGreen + (i * step), 255);
            int blue = Math.min(baseBlue + (i * step), 255);
            shades.add(new Color(red, green, blue));
        }

        return shades;
    }
    
    /**
     * Cria um painel representando uma cor específica.
     *
     * @param color A cor para o painel.
     * @return Um JPanel com a cor e funcionalidade de copiar para a área de transferência.
     */
    public static JPanel createColorPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(50, 50));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Adiciona o evento de clique para copiar o código da cor
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String hex = colorToHex(color);
                String rgb = colorToRGB(color);
                StringSelection stringSelection = new StringSelection("Hex: " + hex + " | RGB: " + rgb);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                JOptionPane.showMessageDialog(panel,
                        "Cor copiada para a área de transferência:\nHex: " + hex + "\nRGB: " + rgb,
                        "Cor Copiada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Converte uma cor para seu código hexadecimal.
     *
     * @param color A cor a ser convertida.
     * @return O código hexadecimal da cor.
     */
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()).toUpperCase();
    }

    /**
     * Converte uma cor para seu formato RGB.
     *
     * @param color A cor a ser convertida.
     * @return A string no formato RGB (ex: "255, 0, 0").
     */
    public static String colorToRGB(Color color) {
        return String.format("%d, %d, %d", color.getRed(), color.getGreen(), color.getBlue());
    }
    
}
