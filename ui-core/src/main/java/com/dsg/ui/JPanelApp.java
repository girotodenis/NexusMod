package com.dsg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.componente.ItemMenu;
import com.dsg.ui.util.UIUtils;

public class JPanelApp extends JPanel {

	private static final long serialVersionUID = 8898685804729074138L;

	
	private JPanel headerPanel;
	private JPanel sideMenuPanel;
	private JPanel contentPanel;
	private JPanel footerPanel;
	
	CustomSideMenu sideMenu;
	List<ItemMenu> itens = new ArrayList<ItemMenu>();
	ItemMenu lastItemMenu;
	
	public JPanelApp(Class<?> classLook) {
		try {
			System.out.println("constructio JPanelApp");
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
		System.out.println("loadMenu");
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
    
    public void updateAll(Class<?> classLook) {
    	updateAll(classLook.getName());
    }
    
    public void updateAll(String classLook) {
    	try {
    		System.out.println("updateAll: "+classLook);
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
		System.out.println("removeMenu: "+item);
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
