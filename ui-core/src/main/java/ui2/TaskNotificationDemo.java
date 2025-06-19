package ui2;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class TaskNotificationDemo {
	
	static int count=0;

    public static void main(String[] args) {
        // Configurar o FlatLaf
        FlatMacDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Task Notification Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Painel principal do aplicativo
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            frame.add(mainPanel);

            // Gerenciador de notificações
            TaskNotificationManager notificationManager = new TaskNotificationManager(frame.getLayeredPane());
            // Botão para adicionar notificações
            JButton addNotificationButton = new JButton("Adicionar Notificação");
            addNotificationButton.addActionListener(e -> {
            	count++;
            	
            	
            	if(count % 2 == 0) {
            		notificationManager.addNotification("Aviso", TaskNotificationType.WARN);
            		
            	}else  	if(count % 3 == 0) {
            		
            		notificationManager.addNotification("Erro", TaskNotificationType.ERROR);
            		count = 0;
            	}else {
            		
            		notificationManager.addNotification("Notificação de Tarefa", TaskNotificationType.INFO);
            	}
            
            });

            mainPanel.add(addNotificationButton, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}

// Enum para tipos de notificação
enum TaskNotificationType {
    INFO, WARN, ERROR
}

// Gerenciador de notificações
class TaskNotificationManager {

    private final Queue<JPanel> notificationQueue = new LinkedList<>();
    private final JLayeredPane layeredPane;
    private static final int NOTIFICATION_WIDTH = 300;
    private static final int NOTIFICATION_HEIGHT = 50;
    private static final int MARGIN = 10;

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

// Painel de notificação
class TaskNotification extends JPanel {

    private final Timer timer;

    public TaskNotification(String message, TaskNotificationType type) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var backgroundColor = getBackgroundColor(type);
        setBackground(backgroundColor);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setIcon(getIcon(type));
//        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setForeground(getInverseColor(backgroundColor));
        add(messageLabel, BorderLayout.CENTER);

        // Configurar o timer para esconder a notificação após 3 segundos
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
                return Color.GREEN;
            case WARN:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
            default:
                return Color.GREEN;
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
