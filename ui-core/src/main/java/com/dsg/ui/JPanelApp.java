package com.dsg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.componente.ItemMenu;
import com.dsg.ui.util.UIUtils;

public class JPanelApp extends JPanel {

	private static final long serialVersionUID = 8898685804729074138L;

	private static final Logger log = LoggerFactory.getLogger(JPanelApp.class);
	
	private JPanel headerPanel;
	private JPanel sideMenuPanel;
	private JPanel contentPanel;
	private JPanel footerPanel;
	private JLabel infoLabel;
	
	CustomSideMenu sideMenu;
	List<ItemMenu> itens = new ArrayList<ItemMenu>();
	ItemMenu lastItemMenu;
	
	public JPanelApp(Class<?> classLook) {
		try {
			log.trace("constructio JPanelApp");
			updateAll(classLook);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JPanelApp(String classLook) {
		try {
			updateAll(classLook);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 // Método para criar o cabeçalho
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Cabeçalho do Aplicativo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        return headerPanel;
    }
    
    public void addMenuItem(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
    	itens.add(new ItemMenu(text, icon, action, null));
    }
    
    public void addMenuItem(String group, String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
    	
    	
    	if(group==null) {
    		addMenuItem(text, icon, action);
			return;
    	}
    	
    	ItemMenu item = null;
    	var op = itens.stream().filter(m->m.getText().equals(group)).findFirst();
    	if(op.isPresent()){
    		item = op.get();
    	}else {
    		item = new ItemMenu(group, UIManager.getIcon("Menu.arrowIcon"), null, null);
    		itens.add(item);
    	}
		item.addSubItems(text,icon,action);
    
    }
    

    // Método para criar o menu lateral
    private JPanel createSideMenuPanel() {
    	if(sideMenu!=null) {
    		sideMenu.removeAll();
    	}
    	sideMenu = new CustomSideMenu();
        return sideMenu;
    }

	public void loadMenu() {
		log.trace("loadMenu");
		if(sideMenu==null) {
			sideMenu = new CustomSideMenu();
    	}
		
    	itens.forEach(item->{
    		sideMenu.addMenuItem(item);
    	});
	}
    
    // Método para criar o conteúdo principal
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        Color padrao = contentPanel.getBackground();
        MatteBorder borda = new MatteBorder(2, 0, 2, 0, UIUtils.ajustColor(padrao, -10)); // Borda vermelha com espessura 5
        contentPanel.setBorder(borda);
        return contentPanel; // Inicializa um painel vazio
    }
    
   
    // Método para criar o rodapé
    private JPanel createFooterPanel() {
    	
    	JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(800, 30));
        footerPanel.setLayout(new BorderLayout());
        
        // Progress Bar (só aparece quando o progresso > 0)
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0); // Inicialmente sem progresso
        progressBar.setStringPainted(true);
        progressBar.setVisible(false); // Inicialmente invisível
        progressBar.setPreferredSize(new Dimension(800, 10)); // Altura reduzida para 10px
        footerPanel.add(progressBar, BorderLayout.NORTH);
        
        // Painel para as labels de informação e versão
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        infoPanel.add(infoLabel, gbc);
        
        JLabel versionLabel = new JLabel("Versão 1.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setForeground(Color.BLUE);
        versionLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        versionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        Font underlinedFont = versionLabel.getFont().deriveFont(
                Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));
        versionLabel.setFont(underlinedFont);
        
        gbc.gridx = 1;
        gbc.weightx = 0.01;
        gbc.anchor = GridBagConstraints.EAST;
        infoPanel.add(versionLabel, gbc);
        
     // Novo link (15% da largura)
        JLabel logLabel = new JLabel("log");
        logLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        logLabel.setForeground(Color.BLUE);
        logLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        logLabel.setFont(underlinedFont);
        
        gbc.gridx = 2;
        gbc.weightx = 0.01;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        infoPanel.add(logLabel, gbc);
        
        footerPanel.add(infoPanel, BorderLayout.CENTER);
        //
        // Método para atualizar o progresso
        //updateProgress(0); // Inicializa com progresso zero
        
        // Adiciona evento de clique para abrir o modal
        versionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showVersionModal();
            }
        });
        
        logLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//            	showLogModal();
            	LogViewerDialog.showLogViewer(infoPanel);
            }
        });
        
        // Inicializa com progresso zero
        return footerPanel;
    }
    
 // Método para atualizar o progresso
    public void updateProgress(int value, String message) {
    	infoLabel.setText(message);
        JProgressBar progressBar = (JProgressBar) ((BorderLayout) footerPanel.getLayout())
                .getLayoutComponent(BorderLayout.NORTH);
        
        if (value > 0) {
            progressBar.setValue(value);
            progressBar.setVisible(true);
        } else {
            progressBar.setVisible(false);
        }
        footerPanel.revalidate();
        footerPanel.repaint();
    }
    
 // Método para mostrar o modal com informações da versão
    private void showVersionModal() {
    	 // Obtém a referência ao JFrame principal
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(footerPanel);
        
        // Cria o diálogo modal vinculado ao frame principal
        JDialog modal = new JDialog(parentFrame, "Informações da Versão", true);
        modal.setSize(800, 600);
        modal.setLocationRelativeTo(parentFrame); // Centraliza em relação ao frame principal
        modal.setLayout(new BorderLayout());
        modal.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Header do modal
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Informações da Versão");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        modal.add(headerPanel, BorderLayout.NORTH);
        
        // Conteúdo principal do modal
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new BorderLayout());
        
        // Aqui você pode adicionar o conteúdo que desejar
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setText("Versão 1.0\n\n" +
                "Desenvolvido por: Sua Empresa\n" +
                "Data de lançamento: 01/01/2023\n\n" +
                "Descrição:\n" +
                "Este é um aplicativo Java Swing desenvolvido para...\n\n" +
                "Novidades desta versão:\n" +
                "- Recurso 1\n" +
                "- Recurso 2\n" +
                "- Correção de bugs");
        
        JScrollPane scrollPane = new JScrollPane(infoArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        modal.add(contentPanel, BorderLayout.CENTER);
        
        // Footer do modal com botão de fechar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> modal.dispose());
        footerPanel.add(closeButton);
        modal.add(footerPanel, BorderLayout.SOUTH);
        
        // Impede redimensionamento (opcional)
        modal.setResizable(false);
        
        // Adiciona tratamento de tecla Escape para fechar o diálogo
        modal.getRootPane().registerKeyboardAction(
                e -> modal.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        // Define o botão de fechar como default (responde ao Enter)
        modal.getRootPane().setDefaultButton(closeButton);
        
        // Exibe o modal
        modal.setVisible(true);
    }
    
 
    
    // Método para mostrar um JPanel como conteúdo do header
    public void showHeader(JPanel panel) {
    	// Limpar o painel de conteúdo existente
    	headerPanel.removeAll();
    	headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
        headerPanel.add(panel);
    	
    	// Atualizar o painel
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    
 

    // Método para mostrar um JPanel como conteúdo
    public void showContent(JPanel panel) {
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
    
    public void updateAll(Class<?> classLook) {
    	updateAll(classLook.getName());
    }
    
    public void updateAll(String classLook) {
    	try {
    		log.trace("updateAll: "+classLook);
    		UIManager.setLookAndFeel(classLook);
    		this.removeAll();
    		setLayout(new BorderLayout());
    		
    		headerPanel = createHeaderPanel();
    		add(headerPanel, BorderLayout.NORTH);
    		
    		// Menu lateral
    		sideMenuPanel = createSideMenuPanel(); // Não precisa mais do frame aqui
    		add(sideMenuPanel, BorderLayout.WEST);

    		// Conteúdo principal
    		contentPanel = createContentPanel(); // Inicializa o painel de conteúdo
    		add(contentPanel, BorderLayout.CENTER);

    		// Rodapé
    		footerPanel = createFooterPanel();
    		add(footerPanel, BorderLayout.SOUTH);
    		
    		loadMenu();
    		revalidate();
    		repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void lastItemMenu(String id) {
    	var op = itens.stream().filter(m->m.getText().equals(id)).findFirst();
    	if(op.isPresent()){
    		lastItemMenu = op.get();
    	}
	}

	public void removeMenu(String item) {
		log.trace("removeMenu: {}", item);
		var delete = new ArrayList<ItemMenu>();
		
		for (ItemMenu itemMenu : itens) {
			if(itemMenu.getId().contains(item)) {
				itemMenu.setEnabled(false);
				delete.add(itemMenu);
			}
			if(itemMenu.getSubItems()!=null) {
				var deleteSub = new ArrayList<ItemMenu>();
				for (ItemMenu subItemMenu : itemMenu.getSubItems()) {
					if(subItemMenu.getId().contains(item)) {
						if(itemMenu.getSubItems().size()<=1) {
							delete.add(itemMenu);
						}
						deleteSub.add(subItemMenu);
					}
				}
				deleteSub.forEach(subItemMenu->{
					sideMenu.removeMenu(subItemMenu);
					itemMenu.getSubItems().remove(subItemMenu);
				});
			}
		}
		delete.forEach(itemMenu->{
			sideMenu.removeMenu(itemMenu);
			itens.remove(itemMenu);
		});
		
		loadMenu();
	}
	

}
