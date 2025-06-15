package com.dsg.ui.util;

import java.awt.Color;

public class UIUtils {

	public static Color ajustColor(Color color, int increment) {

        // Obtém os valores RGB
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Soma o valor ao RGB, limitando entre 0 e 255
        red = Math.min(255, red + increment);
        green = Math.min(255, green + increment);
        blue = Math.min(255, blue + increment);

        // Cria uma nova cor com os novos valores RGB
        return new Color(red, green, blue);
    }
}
