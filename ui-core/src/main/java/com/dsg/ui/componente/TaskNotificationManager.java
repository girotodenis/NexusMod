package com.dsg.ui.componente;


import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.dsg.nexusmod.ui.TaskNotificationType;


// Gerenciador de notificações
public class TaskNotificationManager {

    private final Queue<JPanel> notificationQueue = new LinkedList<>();
    private final JLayeredPane layeredPane;
    static final int NOTIFICATION_WIDTH = 300;
    static final int NOTIFICATION_HEIGHT = 150;
    static final int MARGIN = 10;

    public TaskNotificationManager(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
    }

    public void addNotification(String message, TaskNotificationType type) {
        TaskNotification notification = new TaskNotification(message, type);
        notification.setSize(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        notification.setLocation(
                layeredPane.getWidth() - NOTIFICATION_WIDTH - MARGIN,
                MARGIN + (notificationQueue.size() * (NOTIFICATION_HEIGHT + MARGIN))
        );

        // Adicionar ao painel e à fila
        layeredPane.add(notification, JLayeredPane.POPUP_LAYER);
        notificationQueue.add(notification);

        // Mostrar notificação e configurar para desaparecer
        notification.showNotification(() -> {
            layeredPane.remove(notification);
            notificationQueue.remove(notification);
            updateNotificationPositions();
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    private void updateNotificationPositions() {
        int y = MARGIN;
        for (JPanel panel : notificationQueue) {
            panel.setLocation(layeredPane.getWidth() - NOTIFICATION_WIDTH - MARGIN, y);
            y += NOTIFICATION_HEIGHT + MARGIN;
        }
    }
}

class TaskNotification extends JPanel {

    private final Timer timer;

    public TaskNotification(String message, TaskNotificationType type) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var backgroundColor = getBackgroundColor(type);
        setBackground(backgroundColor);

        // Cria um JTextArea para o texto com suporte a quebra de linha
        JLabel ico = new JLabel(getTitulo(type), getIcon(type), 0);
        ico.setForeground(getInverseColor(backgroundColor));
    	add(ico, BorderLayout.NORTH);
        JTextArea messageText = new JTextArea(message);
        messageText.setFont(new Font("Arial", Font.BOLD, 16));
        messageText.setEditable(false); // Impede edição do texto
        messageText.setWrapStyleWord(true); // Quebra palavras corretamente
        messageText.setLineWrap(true); // Ativa quebra de linha
        messageText.setOpaque(false); // Para o fundo ser transparente
        messageText.setFocusable(false); // Desabilita foco
        messageText.setForeground(getInverseColor(backgroundColor)); // Cor do texto

        // Calcula o tamanho ideal do JTextArea baseando-se no texto
        int textWidth = TaskNotificationManager.NOTIFICATION_WIDTH - 20; // Considera margens (10px de cada lado)
        messageText.setSize(textWidth, Short.MAX_VALUE); // Define um tamanho inicial
        int textHeight = messageText.getPreferredSize().height; // Calcula a altura necessária para o texto
        
        // Ajusta o tamanho do painel com base no texto
        int notificationHeight = Math.max(TaskNotificationManager.NOTIFICATION_HEIGHT, textHeight + 20); // Inclui padding
        setSize(TaskNotificationManager.NOTIFICATION_WIDTH, notificationHeight);

        // Adiciona o JTextArea ao painel
        add(messageText, BorderLayout.CENTER);

        // Configura o timer para esconder a notificação após 7 segundos
        timer = new Timer(7000, e -> setVisible(false));
        timer.setRepeats(false);
    }

    private Icon getIcon(TaskNotificationType type) {
        switch (type) {
            case INFO:
                return UIManager.getIcon("OptionPane.informationIcon");
            case WARN:
                return UIManager.getIcon("OptionPane.warningIcon");
            case ERROR:
                return UIManager.getIcon("OptionPane.errorIcon");
            default:
                return null; // Sem ícone para outros casos
        }
    }

    private Color getInverseColor(Color color) {
        int red = 255 - color.getRed();
        int green = 255 - color.getGreen();
        int blue = 255 - color.getBlue();
        return new Color(red, green, blue);
    }

    public void showNotification(Runnable onDismiss) {
        setVisible(true);
        timer.start();

        // Adicionar animação para desaparecer
        new Timer(70, e -> {
            float opacity = getOpacity();
            if (opacity > 0) {
                setOpacity(Math.max(0.0f, opacity - 0.02f));
            } else {
                ((Timer) e.getSource()).stop();
                onDismiss.run();
            }
        }).start();
    }

    private Color getBackgroundColor(TaskNotificationType type) {
        switch (type) {
            case INFO:
                return new Color(125, 255, 125);
            case WARN:
                return new Color(255, 255, 200);
            case ERROR:
            	/*Hex: #FFD2D2 | RGB: 255, 210, 210*/
                return new Color(255, 210, 210);
            default:
                return Color.GREEN;
        }
    }
    private String getTitulo(TaskNotificationType type) {
    	switch (type) {
    	case INFO:
    		return "Sucesso";
    	case WARN:
    		return "Alerta";
    	case ERROR:
    		return "Erro";
    	default:
    		return "";
    	}
    }
    
    private float opacity = 1.0f;

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Define o nível de transparência (0.0f = totalmente transparente, 1.0f = opaco)
        float alpha = opacity; // Use a variável `opacity` que já controla a transparência
        if (alpha > 1.0f) alpha = 1.0f;
        if (alpha < 0.0f) alpha = 0.0f;

        // Aplica o AlphaComposite para a transparência
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Desenha o fundo do painel
        super.paintComponent(g2d);

        g2d.dispose();
    }
}

