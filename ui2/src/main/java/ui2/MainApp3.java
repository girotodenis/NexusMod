package ui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.dsg.ui.JPanelApp;
import com.dsg.ui.componente.ContextMenu;
import com.dsg.ui.componente.CustomSideMenu;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;


public class MainApp3 {
    private static JPanelApp mainPanel; // Painel de conteúdo principal
    public static int click = 0;
    public static void main(String[] args) {
        // Configurar o tema FlatLaf
//    	FlatMacDarkLaf.setup();
//    	FlatMacLightLaf.setup();
    	FlatDarculaLaf.setup();

        // Criar a janela principal
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aplicativo com FlatLaf");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null);

            mainPanel = new JPanelApp(FlatDarculaLaf.class);
            createSideMenuPanel();
            
            // Adicionar o painel principal à janela
            
            frame.setContentPane(mainPanel);
            
            frame.setVisible(true);
        });
    }
    
    // Método para criar o menu lateral
    private static JPanel createSideMenuPanel() {
        CustomSideMenu sideMenu = new CustomSideMenu((ContextMenu) mainPanel);
        // Adicionar itens de menu
        mainPanel.addMenuItem("Menu 1 Tela Icons", UIManager.getIcon("FileView.directoryIcon"), (mainPanel,itemMenu) -> mainPanel.showContent(createSubItemPanel("Menu 1")));
        mainPanel.addMenuItem("Menu 2 BadgeNumber ", UIManager.getIcon("FileView.directoryIcon"), (mainPanel,itemMenu) -> itemMenu.setBadgeNumber(++click));
//        mainPanel.addMenuItem("Menu Modal ", UIManager.getIcon("FileView.directoryIcon"), (itemMenu) -> {
//        	 JPanel content = new JPanel();
//             content.setLayout(new BorderLayout());
//             content.add(new JLabel("Este é o conteúdo da modal", SwingConstants.CENTER), BorderLayout.CENTER);
//
//             // Botões do rodapé
//             JButton button1 = new JButton("Salvar");
//             JButton button2 = new JButton("Cancelar");
//             JButton[] footerButtons = {button1, button2};
//
//             // Abre a modal
//             mainPanel.openModal("Minha Modal", content, new Dimension(600, 600), footerButtons, "center");
//        });
        // Adicionar um item com sub-itens
//        List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
//        subItems.add(sideMenu.new MenuItem("SubItem 3.1 FlatMacDarkLaf", UIManager.getIcon("FileView.floppyDriveIcon"), (mainPanel,itemMenu) -> mainPanel.updatePanel(FlatMacDarkLaf.class) ));
//        subItems.add(sideMenu.new MenuItem("SubItem 3.2 FlatDarculaLaf", UIManager.getIcon("FileChooser.homeFolderIcon"), (mainPanel,itemMenu) -> mainPanel.updatePanel(FlatDarculaLaf.class)));
//        subItems.add(sideMenu.new MenuItem("SubItem 3.3 FlatIntelliJLaf", UIManager.getIcon("HelpButton.icon"), (mainPanel,itemMenu) -> mainPanel.updatePanel(FlatIntelliJLaf.class)));
//        subItems.add(sideMenu.new MenuItem("SubItem 3.4 FlatMacLightLaf", UIManager.getIcon("Tree.leafIcon"), (mainPanel,itemMenu) -> mainPanel.updatePanel(FlatMacLightLaf.class)));
//        mainPanel.addMenuItemWithSubItems("Menu 3", subItems);
        
        mainPanel.loadMenu();
        return sideMenu;
    }

    

    // Método para criar um painel para subitens
    private static JPanel createSubItemPanel(String content) {
    	JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Usar BorderLayout para adicionar título e tabela

        // Adicionar título
        JLabel titleLabel = new JLabel(content);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Criar um painel para os ícones
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridLayout(0, 3)); // 0 linhas e 5 colunas

        // Adicionar ícones ao painel
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object valor = keys.nextElement();
            if (valor instanceof String) {
                String key = (String) valor;
                Icon icon = UIManager.getIcon(key);
                if (icon != null) {
                    JLabel iconLabel = new JLabel(key, icon, JLabel.CENTER);
//                    iconLabel.setForeground(Color.WHITE);
                    iconLabel.setHorizontalAlignment(JLabel.CENTER);
                    iconPanel.add(iconLabel);
                }
            }
        }

        // Adicionar o painel de ícones ao painel principal
        panel.add(iconPanel, BorderLayout.CENTER);
        
        return panel;
    }

   
    
    
}