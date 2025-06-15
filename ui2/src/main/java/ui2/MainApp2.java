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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.util.UIUtils;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;


public class MainApp2 {
    private static JPanel contentPanel; // Painel de conteúdo principal
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

            JPanel mainPanel = new JPanel(new BorderLayout());
            newPanel(mainPanel);

            // Adicionar o painel principal à janela
            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
    }
    private static void updatePanel(JPanel mainPanel, Class classLook) {
    	try {
//    		FlatIntelliJLaf.setup() ;
    		UIManager.setLookAndFeel(classLook.getName());
    		mainPanel.removeAll();
    		mainPanel.setLayout(new BorderLayout());
    		newPanel(mainPanel);
    		mainPanel.revalidate();
    		mainPanel.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private static void newPanel(JPanel mainPanel) {
		// Criar o layout principal (BorderLayout)

		// Cabeçalho
		JPanel headerPanel = createHeaderPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		// Menu lateral
		JPanel sideMenuPanel = createSideMenuPanel(mainPanel); // Não precisa mais do frame aqui
		mainPanel.add(sideMenuPanel, BorderLayout.WEST);

		// Conteúdo principal
		contentPanel = createContentPanel(); // Inicializa o painel de conteúdo
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Rodapé
		JPanel footerPanel = createFooterPanel();
		mainPanel.add(footerPanel, BorderLayout.SOUTH);
	}

    // Método para criar o cabeçalho
    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(new Color(45, 45, 45));
        headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Cabeçalho do Aplicativo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        headerPanel.add(titleLabel);

        return headerPanel;
    }

    // Método para criar o menu lateral
    private static JPanel createSideMenuPanel(JPanel frame) {
        CustomSideMenu sideMenu = new CustomSideMenu();

        // Adicionar itens de menu
        sideMenu.addMenuItem("Menu 1", UIManager.getIcon("FileView.directoryIcon"), (itemMenu) -> showContent(createSubItemPanel("Menu 1")));
        sideMenu.addMenuItem("Menu 2", UIManager.getIcon("FileView.directoryIcon"), (itemMenu) -> itemMenu.setBadgeNumber(++click));

        // Adicionar um item com sub-itens
        List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
        subItems.add(sideMenu.new MenuItem("SubItem 3.1 FlatMacDarkLaf", UIManager.getIcon("FileView.floppyDriveIcon"), (itemMenu) -> updatePanel(frame, FlatMacDarkLaf.class) ));
        subItems.add(sideMenu.new MenuItem("SubItem 3.2 FlatDarculaLaf", UIManager.getIcon("FileChooser.homeFolderIcon"), (itemMenu) -> updatePanel(frame, FlatDarculaLaf.class)));
        subItems.add(sideMenu.new MenuItem("SubItem 3.3 FlatIntelliJLaf", UIManager.getIcon("HelpButton.icon"), (itemMenu) -> updatePanel(frame, FlatIntelliJLaf.class)));
        subItems.add(sideMenu.new MenuItem("SubItem 3.4 FlatMacLightLaf", UIManager.getIcon("Tree.leafIcon"), (itemMenu) -> updatePanel(frame, FlatMacLightLaf.class)));
        sideMenu.addMenuItemWithSubItems("Menu 3", subItems);

        return sideMenu;
    }

    // Método para criar o conteúdo principal
    private static JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        Color padrao = contentPanel.getBackground();
        MatteBorder borda = new MatteBorder(2, 0, 2, 0, UIUtils.ajustColor(padrao, -10)); // Borda vermelha com espessura 5
        contentPanel.setBorder(borda);
        return contentPanel; // Inicializa um painel vazio
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

    // Método para criar o rodapé
    private static JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(800, 30));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel footerLabel = new JLabel("Rodapé do Aplicativo");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        footerPanel.add(footerLabel);

        return footerPanel;
    }
 

    // Método para mostrar um JPanel como conteúdo
    private static void showContent(JPanel panel) {
    	// Limpar o painel de conteúdo existente
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Adicionar o novo conteúdo ao painel de conteúdo
        contentPanel.add(scrollPane, BorderLayout.CENTER); // Adiciona o painel no centro
        // Atualizar o painel
        contentPanel.revalidate();
        contentPanel.repaint();
        

    }
}