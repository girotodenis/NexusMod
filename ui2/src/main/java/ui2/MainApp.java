package ui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

public class MainApp {
    public static void main(String[] args) {
        // Configurar o tema FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
            	Object valor = keys.nextElement();
            	if(valor instanceof String) {
            		String key = (String)valor ;
            		Icon icon = UIManager.getIcon(key);
            		if (icon != null) {
            			System.out.println(key + " : " + icon);
            		}
            	}
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Criar a janela principal
        JFrame frame = new JFrame("Aplicativo com Menu Lateral");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


     // Criar o menu lateral
        CustomSideMenu sideMenu = new CustomSideMenu();

        // Adicionar itens de menu
        sideMenu.addMenuItem("Menu 1", UIManager.getIcon("FileView.directoryIcon"), () -> System.out.println("Menu 1 clicado"));
        sideMenu.addMenuItem("Menu 2", UIManager.getIcon("FileView.directoryIcon"), () -> System.out.println("Menu 2 clicado"));

        // Adicionar um item com sub-itens
        List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
        subItems.add(sideMenu.new MenuItem("SubItem 3.1", UIManager.getIcon("FileView.floppyDriveIcon"), () -> System.out.println("SubItem 1 clicado")));
        subItems.add(sideMenu.new MenuItem("SubItem 3.2", UIManager.getIcon("FileChooser.homeFolderIcon"), () -> System.out.println("SubItem 1 clicado")));
        subItems.add(sideMenu.new MenuItem("SubItem 3.3", UIManager.getIcon("HelpButton.icon"), () -> System.out.println("SubItem 2 clicado")));
        subItems.add(sideMenu.new MenuItem("SubItem 3.4", UIManager.getIcon("Tree.leafIcon"), () -> System.out.println("SubItem 2 clicado")));
        sideMenu.addMenuItemWithSubItems("Menu 3",  subItems);

        // Adicionar o menu lateral à janela
        frame.add(sideMenu, BorderLayout.WEST);

        // Criar conteúdo principal
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(35, 35, 35));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Exibir a janela
        frame.setVisible(true);
    }
}