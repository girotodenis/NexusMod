package com.dsg.ui;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.StyleConstants;

public class LogViewerDialog extends JDialog implements SwingLogAppender.LogListener {
    private static final long serialVersionUID = -7111889344264519445L;
	private JTextPane logTextPane;
    private StyledDocument doc;
    private Map<String, Style> levelStyles = new HashMap<>();
    private JComboBox<String> logLevelFilter;
    private boolean autoScroll = true;

    /**
     * Cria e exibe um diálogo de visualização de logs
     * 
     * @param parent O componente pai (geralmente o JFrame principal)
     */
    public static void showLogViewer(Component parent) {
        Frame parentFrame = parent instanceof Frame ? 
                (Frame) parent : 
                (Frame) SwingUtilities.getWindowAncestor(parent);
        
        LogViewerDialog dialog = new LogViewerDialog(parentFrame);
        dialog.setVisible(true);
    }

    private LogViewerDialog(Frame parent) {
        super(parent, "Visualizador de Logs", false); // não-modal
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        initComponents();
        setupStyles();
        loadRecentLogs();
        
        // Registra como listener de logs
        SwingLogAppender.addListener(this);
        
        // Remove o listener quando a janela for fechada
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingLogAppender.removeListener(LogViewerDialog.this);
            }
        });
        
        // Teste direto no documento
//        try {
//            doc.insertString(doc.getLength(), "=== Visualizador de Logs Iniciado ===\n", null);
//        } catch (BadLocationException ex) {
//            ex.printStackTrace();
//        }
    }

    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Visualizador de Logs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Conteúdo principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Área de texto para logs
        logTextPane = new JTextPane();
        logTextPane.setEditable(false);
        doc = logTextPane.getStyledDocument();
        JScrollPane scrollPane = new JScrollPane(logTextPane);
        
        // Painel de controles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Filtro de nível
        logLevelFilter = new JComboBox<>(new String[]{"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR"});
        logLevelFilter.setSelectedItem("ALL");
        logLevelFilter.addActionListener(e -> reloadLogs());
        
        // Botão limpar
        JButton clearButton = new JButton("Limpar");
        clearButton.addActionListener(e -> clearLogs());
        
        // Checkbox rolagem automática
        JCheckBox autoScrollCheckBox = new JCheckBox("Rolagem Automática", true);
        autoScrollCheckBox.addActionListener(e -> autoScroll = autoScrollCheckBox.isSelected());
        
        // Botão de teste
        JButton testButton = new JButton("Gerar Log Teste");
        testButton.addActionListener(e -> generateTestLogs());
        testButton.setVisible(false);
        // Adiciona componentes ao painel de controle
        controlPanel.add(new JLabel("Filtro:"));
        controlPanel.add(logLevelFilter);
        controlPanel.add(clearButton);
        controlPanel.add(autoScrollCheckBox);
        controlPanel.add(testButton);
        
        // Adiciona componentes ao painel de conteúdo
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
        
        // Footer com botão fechar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        footerPanel.add(closeButton);
        add(footerPanel, BorderLayout.SOUTH);
        
        // Tecla Escape fecha o diálogo
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        // Botão fechar como default
        getRootPane().setDefaultButton(closeButton);
    }

    private void setupStyles() {
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        
        Style infoStyle = logTextPane.addStyle("INFO", defaultStyle);
        StyleConstants.setForeground(infoStyle, Color.BLACK);
        
        Style debugStyle = logTextPane.addStyle("DEBUG", defaultStyle);
        StyleConstants.setForeground(debugStyle, Color.BLUE);
        
        Style warnStyle = logTextPane.addStyle("WARN", defaultStyle);
        StyleConstants.setForeground(warnStyle, Color.ORANGE);
        
        Style errorStyle = logTextPane.addStyle("ERROR", defaultStyle);
        StyleConstants.setForeground(errorStyle, Color.RED);
        StyleConstants.setBold(errorStyle, true);
        
        Style traceStyle = logTextPane.addStyle("TRACE", defaultStyle);
        StyleConstants.setForeground(traceStyle, Color.GRAY);
        
        levelStyles.put("INFO", infoStyle);
        levelStyles.put("DEBUG", debugStyle);
        levelStyles.put("WARN", warnStyle);
        levelStyles.put("ERROR", errorStyle);
        levelStyles.put("TRACE", traceStyle);
    }

    private void loadRecentLogs() {
        for (SwingLogAppender.LogEntry entry : SwingLogAppender.getRecentLogs()) {
            if (shouldDisplayLog(entry)) {
                appendLog(entry);
            }
        }
    }

    private void reloadLogs() {
        clearLogs();
        loadRecentLogs();
    }

    private void clearLogs() {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void generateTestLogs() {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("TestLogger");
        logger.trace("Log de teste TRACE");
        logger.debug("Log de teste DEBUG");
        logger.info("Log de teste INFO");
        logger.warn("Log de teste WARN");
        logger.error("Log de teste ERROR");
        
        // Teste direto no documento
        try {
            doc.insertString(doc.getLength(), "=== Log inserido diretamente ===\n", null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onNewLogEntry(SwingLogAppender.LogEntry entry) {
        //System.out.println("LogViewerDialog.onNewLogEntry: " + entry.getFormattedMessage());
        
        if (shouldDisplayLog(entry)) {
            SwingUtilities.invokeLater(() -> appendLog(entry));
        }
    }

    private boolean shouldDisplayLog(SwingLogAppender.LogEntry entry) {
        String selectedLevel = (String) logLevelFilter.getSelectedItem();
        if ("ALL".equals(selectedLevel)) {
            return true;
        }
        
        String entryLevel = entry.getLevel();
        switch (selectedLevel) {
            case "TRACE": return true;
            case "DEBUG": return !entryLevel.equals("TRACE");
            case "INFO": return !entryLevel.equals("TRACE") && !entryLevel.equals("DEBUG");
            case "WARN": return entryLevel.equals("WARN") || entryLevel.equals("ERROR");
            case "ERROR": return entryLevel.equals("ERROR");
            default: return true;
        }
    }

    private void appendLog(SwingLogAppender.LogEntry entry) {
        try {
            Style style = levelStyles.getOrDefault(entry.getLevel(), 
                StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
            
            doc.insertString(doc.getLength(), entry.getFormattedMessage() + "\n", style);
            
            if (autoScroll) {
                logTextPane.setCaretPosition(doc.getLength());
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        SwingLogAppender.removeListener(this);
        super.dispose();
    }
}