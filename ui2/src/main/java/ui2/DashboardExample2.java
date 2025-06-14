package ui2;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardExample2 {

    static boolean isMenuCollapsed = false;

    public static void main(String[] args) {
        // Configura o tema FlatLaf Dark
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Dashboard - FlatLaf Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Header (cabeçalho)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1000, 50));
        headerPanel.setBackground(new Color(45, 45, 45));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel headerTitle = new JLabel("Dashboard", JLabel.LEFT);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.WEST);

        JPanel headerActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerActions.setOpaque(false);

        JButton notificationsButton = new JButton("🔔");
        JButton messagesButton = new JButton("✉️");
        notificationsButton.setFocusPainted(false);
        messagesButton.setFocusPainted(false);

        headerActions.add(notificationsButton);
        headerActions.add(messagesButton);
        headerPanel.add(headerActions, BorderLayout.EAST);

        // Menu lateral
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(250, 600));
        menuPanel.setBackground(new Color(45, 45, 45));

        // Botão de encolher/expandir menu
        JButton toggleMenuButton = new JButton("⬅");
        toggleMenuButton.setFocusPainted(false);
        toggleMenuButton.setBackground(new Color(30, 30, 30));
        toggleMenuButton.setForeground(Color.WHITE);

        // Informações do nome do usuário
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(30, 30, 30));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userIcon = new JLabel(new ImageIcon("user_icon.png")); // Ícone do usuário
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userName = new JLabel("User Name");
        userName.setForeground(Color.WHITE);
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userRole = new JLabel("Admin");
        userRole.setForeground(Color.GRAY);
        userRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        userPanel.add(userIcon);
        userPanel.add(userName);
        userPanel.add(userRole);

        // Painel de itens do menu
        JPanel menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBackground(new Color(45, 45, 45));

        // Itens do menu com ícones e atalhos
        JButton dashboardButton = createMenuButton("Dashboard", "🏠", KeyStroke.getKeyStroke("ctrl D"));
        JButton applicationButton = createMenuButton("Application", "📂", KeyStroke.getKeyStroke("ctrl A"));
        JButton staffButton = createMenuButton("Staff", "👥", KeyStroke.getKeyStroke("ctrl S"));
        JButton item1Button = createSubMenuButton("Item1", "🔹", staffButton);
        JButton menu02Button = createSubMenuButton("Menu02", "🔹", staffButton);
        JButton reportButton = createMenuButton("Report", "📊", KeyStroke.getKeyStroke("ctrl R"));

        // Adiciona os botões ao painel do menu
        menuItemsPanel.add(dashboardButton);
        menuItemsPanel.add(applicationButton);
        menuItemsPanel.add(staffButton);
        menuItemsPanel.add(item1Button);
        menuItemsPanel.add(menu02Button);
        menuItemsPanel.add(reportButton);

        // Adiciona os painéis ao menu
        menuPanel.add(toggleMenuButton, BorderLayout.NORTH);
        menuPanel.add(userPanel, BorderLayout.CENTER);
        menuPanel.add(menuItemsPanel, BorderLayout.SOUTH);

        // Adiciona ação para encolher/expandir o menu
        toggleMenuButton.addActionListener(e -> {
            isMenuCollapsed = !isMenuCollapsed;

            if (isMenuCollapsed) {
                menuPanel.setPreferredSize(new Dimension(80, 600));
                toggleMenuButton.setText("➡");
                userName.setVisible(false);
                userRole.setVisible(false);
                dashboardButton.setText("");
                applicationButton.setText("");
                staffButton.setText("");
                reportButton.setText("");
            } else {
                menuPanel.setPreferredSize(new Dimension(250, 600));
                toggleMenuButton.setText("⬅");
                userName.setVisible(true);
                userRole.setVisible(true);
                dashboardButton.setText("Dashboard");
                applicationButton.setText("Application");
                staffButton.setText("Staff");
                reportButton.setText("Report");
            }

            frame.revalidate();
        });

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setLayout(new BorderLayout());

        JLabel mainContent = new JLabel("Main Content Area", JLabel.CENTER);
        mainContent.setForeground(Color.WHITE);
        mainContent.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(mainContent, BorderLayout.CENTER);

        // Adiciona os painéis ao frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Cria um botão de menu principal
    private static JButton createMenuButton(String text, String icon, KeyStroke shortcut) {
        JButton button = new JButton(icon + " " + text);
        button.setFocusPainted(false);
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        if (shortcut != null) {
            button.setToolTipText("Shortcut: " + shortcut.toString());
        }

        return button;
    }

    // Cria um botão de submenu
    private static JButton createSubMenuButton(String text, String icon, JButton parentButton) {
        JButton button = new JButton(icon + " " + text);
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setVisible(false); // Submenu inicialmente oculto

        parentButton.addActionListener(e -> button.setVisible(!button.isVisible()));

        return button;
    }
}