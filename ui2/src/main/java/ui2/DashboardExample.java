package ui2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.formdev.flatlaf.FlatDarkLaf;


public class DashboardExample {

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

        // Informações do usuário
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(30, 30, 30));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userName = new JLabel("User Name");
        userName.setForeground(Color.WHITE);
        userName.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel userRole = new JLabel("Admin");
        userRole.setForeground(Color.GRAY);

        userPanel.add(userName);
        userPanel.add(userRole);

        // Menu funcional com submenus
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode dashboardNode = new DefaultMutableTreeNode("Dashboard");
        DefaultMutableTreeNode applicationNode = new DefaultMutableTreeNode("Application");
        DefaultMutableTreeNode staffNode = new DefaultMutableTreeNode("Staff");
        DefaultMutableTreeNode reportNode = new DefaultMutableTreeNode("Report");

        // Submenus para Staff
        DefaultMutableTreeNode item1Node = new DefaultMutableTreeNode("Item1");
        DefaultMutableTreeNode menu02Node = new DefaultMutableTreeNode("Menu02");
        staffNode.add(item1Node);
        staffNode.add(menu02Node);

        // Submenus para Report
        DefaultMutableTreeNode exportNode = new DefaultMutableTreeNode("Export");
        DefaultMutableTreeNode importNode = new DefaultMutableTreeNode("Import");
        DefaultMutableTreeNode settingsNode = new DefaultMutableTreeNode("Settings");
        reportNode.add(exportNode);
        reportNode.add(importNode);
        reportNode.add(settingsNode);

        // Adiciona os menus principais ao menu raiz
        root.add(dashboardNode);
        root.add(applicationNode);
        root.add(staffNode);
        root.add(reportNode);

        JTree menuTree = new JTree(root);
        menuTree.setBackground(new Color(45, 45, 45));
        menuTree.setForeground(Color.WHITE);
        menuTree.setRootVisible(false);
        menuTree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        menuTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    System.out.println("Selecionado: " + selectedNode.getUserObject());
                }
            }
        });

        menuPanel.add(userPanel, BorderLayout.NORTH);
        menuPanel.add(new JScrollPane(menuTree), BorderLayout.CENTER);

        // Rodapé
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(1000, 30));
        footerPanel.setBackground(new Color(45, 45, 45));

        JLabel memoryLabel = new JLabel("Memory Usage: ");
        memoryLabel.setForeground(Color.WHITE);
        footerPanel.add(memoryLabel, BorderLayout.WEST);

        JProgressBar memoryProgressBar = new JProgressBar();
        memoryProgressBar.setForeground(new Color(60, 180, 75));
        memoryProgressBar.setBackground(new Color(30, 30, 30));
        footerPanel.add(memoryProgressBar, BorderLayout.CENTER);

        // Atualiza o uso de memória periodicamente
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

                // Obtém o uso de memória em bytes
                long usedMemoryBytes = heapMemoryUsage.getUsed();
                long maxMemoryBytes = heapMemoryUsage.getMax();

                // Converte bytes para gigabytes
                double usedMemoryGB = usedMemoryBytes / (double) (1024 * 1024 * 1024);
                double maxMemoryGB = maxMemoryBytes / (double) (1024 * 1024 * 1024);

                // Calcula o percentual de uso
                double usagePercent = (usedMemoryGB * 100) / maxMemoryGB;

                // Atualiza a barra de progresso e o texto no Swing
                SwingUtilities.invokeLater(() -> {
                    memoryLabel.setText(String.format("Memory Usage: %.2f GB / %.2f GB (%.0f%%)", usedMemoryGB, maxMemoryGB, usagePercent));
                    memoryProgressBar.setValue((int) usagePercent);
                });
            }
        }, 0, 1000);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setLayout(new BorderLayout());

        JLabel mainContent = new JLabel("Main Content Area", JLabel.CENTER);
        mainContent.setForeground(Color.WHITE);
        mainContent.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(mainContent, BorderLayout.CENTER);

        // Adiciona os componentes ao frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}