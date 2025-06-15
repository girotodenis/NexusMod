package com.dsg.ui.componente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.dsg.ui.componente.CustomSideMenu.MenuItem;
import com.dsg.ui.util.UIUtils;

public class JPanelApp extends JPanel {

	private static final long serialVersionUID = 8898685804729074138L;

	private JPanel headerPanel;
	private JPanel sideMenuPanel;
	private JPanel contentPanel;
	private JPanel footerPanel;
	
//	private CustomModal modalPanel; // Painel que funciona como a modal

	CustomSideMenu sideMenu = new CustomSideMenu();
	List<ItemMenu> itens = new ArrayList<ItemMenu>();
	
	public JPanelApp(Class classLook) {
		try {
			updatePanel(classLook);
			
//			UIManager.setLookAndFeel(classLook.getName());
//			
//			setLayout(new BorderLayout());
//			
//			// Cabeçalho
//			headerPanel = createHeaderPanel();
//			add(headerPanel, BorderLayout.NORTH);
//			
//			// Menu lateral
//			sideMenuPanel = createSideMenuPanel(); // Não precisa mais do frame aqui
//			add(sideMenuPanel, BorderLayout.WEST);
//			
//			// Conteúdo principal
//			contentPanel = createContentPanel(); // Inicializa o painel de conteúdo
//			add(contentPanel, BorderLayout.CENTER);
//			
//			// Modal (escondido inicialmente)
//            modalPanel = createModalPanel();
//            add(modalPanel, BorderLayout.CENTER); // Adiciona o modal ao painel principal
//			
//			// Rodapé
//			footerPanel = createFooterPanel();
//			add(footerPanel, BorderLayout.SOUTH);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private CustomModal createModalPanel() {
		CustomModal customModal = new CustomModal();
        customModal.setBounds(0, 0, 800, 600); // Ocupa toda a área da janela
        customModal.setVisible(false); // Inicialmente invisível
        return customModal;
	}
	
	
	 // Método para criar o cabeçalho
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(new Color(45, 45, 45));
        headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Cabeçalho do Aplicativo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        return headerPanel;
    }
    
    public void addMenuItem(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
    	itens.add(new ItemMenu(text, icon, action, null));
    	sideMenu.addMenuItem(text, icon, action);
    }
    
    public void addMenuItemWithSubItems(String text, List<CustomSideMenu.MenuItem> subItems) {
    	List<ItemMenu> list = new ArrayList<ItemMenu>();
    	subItems.forEach(sub->{
    		list.add(new ItemMenu(sub.getText(), sub.getIcon(), sub.getAction(), null));
    	});
    	itens.add(new ItemMenu(text, null, null, list));
    	sideMenu.addMenuItemWithSubItems(text, subItems);
    }

    // Método para criar o menu lateral
    private JPanel createSideMenuPanel() {
    	
    	if(sideMenu!=null) {
    		sideMenu.removeAll();
    	}
    	
    	sideMenu = new CustomSideMenu();
    	
    	itens.forEach(item->{
    		if(item.getAction() != null) {
    			sideMenu.addMenuItem(item.getText(), item.getIcon(), item.getAction());
    		}else {
    			List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
    			item.getSubItems().forEach(subitem->{
    				System.out.println(subitem.getIcon());
    				subItems.add(sideMenu.new MenuItem(subitem.getText(), subitem.getIcon(), subitem.getAction() ));
    			});
    			sideMenu.addMenuItemWithSubItems(item.getText(), subItems);
    		}
    	});
    	
        return sideMenu;
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
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel footerLabel = new JLabel("Rodapé do Aplicativo");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        return footerPanel;
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
    
    public void updatePanel(Class classLook) {
    	try {
//    		FlatIntelliJLaf.setup() ;
    		UIManager.setLookAndFeel(classLook.getName());
    		this.removeAll();
    		setLayout(new BorderLayout());
    		
    		JPanel headerPanel = createHeaderPanel();
    		add(headerPanel, BorderLayout.NORTH);
    		
    		// Menu lateral
    		JPanel sideMenuPanel = createSideMenuPanel(); // Não precisa mais do frame aqui
    		add(sideMenuPanel, BorderLayout.WEST);

    		// Conteúdo principal
    		contentPanel = createContentPanel(); // Inicializa o painel de conteúdo
    		add(contentPanel, BorderLayout.CENTER);
    		
    		// Modal (escondido inicialmente)
//            modalPanel = createModalPanel();
//            add(modalPanel, BorderLayout.CENTER); // Adiciona o modal ao painel principal

    		// Rodapé
    		JPanel footerPanel = createFooterPanel();
    		add(footerPanel, BorderLayout.SOUTH);
    		
    		revalidate();
    		repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//    public void openModal(String title, JPanel content, Dimension size, JButton[] footerButtons, String position) {
//    	modalPanel.openModal(title, content, size, footerButtons, position);
//    }

}
