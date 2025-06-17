package com.dsg.ui.componente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.dsg.ui.util.UIUtils;

// Classe para o menu lateral
public class CustomSideMenu extends JPanel {
    private static final long serialVersionUID = 670027307508379334L;
    
	private boolean expanded = true; // Indica se o menu está expandido
    private final int expandedWidth = 300; // Largura do menu expandido
    private final int collapsedWidth = 50; // Largura do menu encolhido
    private final List<MenuItem> menuItems = new ArrayList<>();

    private final ContextMenu app; // Contêiner para os itens do menu
    private final JPanel menuContainer; // Contêiner para os itens do menu
    private final Color padrao; 
    private final Color menuContainerColor; 
    private final Color itemMenuColor; 
    private final Color toggleMenuColor; 

    public CustomSideMenu(ContextMenu app) {
    	
    	this.app = app;
    	this.padrao = getBackground();
    	this.menuContainerColor = UIUtils.ajustColor(padrao,-20);
    	this.itemMenuColor = UIUtils.ajustColor(this.padrao,-10);
    	this.toggleMenuColor = UIUtils.ajustColor(this.padrao,-30);
    	
    	setLayout(new BorderLayout());
        setPreferredSize(new Dimension(expandedWidth, 600));
        setBackground( this.itemMenuColor );

        // Botão para expandir/encolher o menu
        JButton toggleButton = new JButton("☰");
        toggleButton.setFont(new Font("Arial", Font.BOLD, 16));
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground( this.toggleMenuColor );
        toggleButton.setBorderPainted(false);
        toggleButton.setPreferredSize(new Dimension(50, 50));

        toggleButton.addActionListener(e -> toggleMenu());

        // Adiciona o botão no topo
        add(toggleButton, BorderLayout.NORTH);

        // Contêiner para itens de menu
        menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setBackground( this.menuContainerColor );
        menuContainer.setBorder(new EmptyBorder(2, 2, 2, 2));

        JScrollPane scrollPane = new JScrollPane(menuContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }
    
  
    
    // Método para alternar entre expandir e encolher o menu
    private void toggleMenu() {
        expanded = !expanded;
        setPreferredSize(new Dimension(expanded ? expandedWidth : collapsedWidth, getHeight()));
        revalidate();
        repaint();

        // Atualiza os itens de menu
        for (MenuItem item : menuItems) {
            item.updateView(expanded);
        }
    }

    // Método para adicionar um item de menu
    public void addMenuItem(String id, String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
        MenuItem item = new MenuItem(id, text, icon, action);
        menuItems.add(item);
        menuContainer.add(item);
        revalidate();
        repaint();
    }

    // Método para adicionar um item de menu com sub-itens
    public void addMenuItemWithSubItems(String text, List<MenuItem> subItems) {
        MenuItem item = new MenuItem(text, text, UIManager.getIcon("Menu.arrowIcon"), null);
        item.setSubItems(subItems, item);
        menuItems.add(item);
        menuContainer.add(item);
        
        for (MenuItem menuItem : subItems) {
        	 menuItems.add(menuItem);
             menuContainer.add(menuItem);
		}
        
        revalidate();
        repaint();
    }

    // Classe interna para representar um item de menu
    public class MenuItem extends JPanel {
		
    	private static final long serialVersionUID = 5652164465879606302L;
		
    	private final String id;
    	private final LabelWithBadge labelIcon;
		private final JLabel label;
        private final String text;
        private boolean isExpanded = false;
        private Color padrao = null;
        protected MenuItem root;
        private List<CustomSideMenu.MenuItem> filhos = new ArrayList<CustomSideMenu.MenuItem>();
        protected Icon icon;
        Consumer<CustomSideMenu.MenuItem> action;
        
        public MenuItem(String id, String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
        	this.id = id;
        	System.out.println("MenuItem ID: " + id);
        	this.text = text;
        	this.icon = icon;
        	this.action = action;
        	this.padrao = getBackground();
            setLayout(new BorderLayout());
            setBackground( itemMenuColor );
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
            setBorder(new EmptyBorder(5, 10, 5, 5));

            labelIcon = new LabelWithBadge(icon, 0);
            labelIcon.setFont(new Font("Arial", Font.PLAIN, 14));
            
            label = new JLabel(text, JLabel.LEFT);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setText(expanded ? text : "");
            
            	
            add(labelIcon, BorderLayout.WEST);
            add(label, BorderLayout.CENTER);

            // Configura ação ao clicar no item principal
            if (action != null) {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                    	for (MenuItem menuItem : menuItems) {
                    		menuItem.setBackground( itemMenuColor );
						}
                    	((MenuItem)e.getSource()).setBackground(padrao);
                        action.accept(CustomSideMenu.MenuItem.this);
                        CustomSideMenu.this.app.lastItemMenu(CustomSideMenu.MenuItem.this.getText());
                    }
                });
            }
        }

        // Atualiza a visualização do item com base no estado do menu
        public void updateView(boolean expanded) {
        	label.setText(expanded ? text : "");
        }

        // Define os sub-itens do menu
        public void setSubItems(List<MenuItem> subItems, MenuItem item ) {
            for (MenuItem subItem : subItems) {
            	subItem.root = item;
            	subItem.setVisible(false);
                subItem.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
                filhos.add(subItem);
            }
            item.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    toggleSubMenu(); // Exibe/oculta sub-itens ao clicar
                }
            });
        }

		// Alterna a exibição dos sub-itens
        private void toggleSubMenu() {
            isExpanded = !isExpanded;
            filhos.forEach(menu-> menu.setVisible(isExpanded));
            ResizableIcon icon = new ResizableIcon(!isExpanded ? UIManager.getIcon("Menu.arrowIcon"): UIManager.getIcon("Table.descendingSortIcon"), 15, 15);
            labelIcon.setIcon(icon);
            label.setText(expanded ? this.text : "");
            revalidate();
            repaint();
        }
        
        public void setBadgeNumber(int badgeNumber) {
        	labelIcon.setBadgeNumber(badgeNumber);
        }

		public String getText() {
			return text;
		}

		public Icon getIcon() {
			return icon;
		}

		public Consumer<CustomSideMenu.MenuItem> getAction() {
			return action;
		}

		public String getId() {
			return id;
		}
		
    }
}