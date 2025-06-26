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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.dsg.ui.ContextApp;
import com.dsg.ui.util.UIUtils;

// Classe para o menu lateral
public class CustomSideMenu extends JPanel {
    private static final long serialVersionUID = 670027307508379334L;
    
	private boolean expanded = true; // Indica se o menu está expandido
    private final int expandedWidth = 300; // Largura do menu expandido
    private final int collapsedWidth = 65; // Largura do menu encolhido
    private final List<MenuItem> menuItems = new ArrayList<>();

//    private final ContextMenu app; // Contêiner para os itens do menu
    private final JPanel menuContainer; // Contêiner para os itens do menu
    private final Color padrao; 
    private final Color menuContainerColor; 
    private final Color itemMenuColor; 
    private final Color toggleMenuColor; 

    public CustomSideMenu() {
    	
    	this.padrao = getBackground();
    	this.menuContainerColor = UIUtils.ajustColor(padrao,-20);
    	this.itemMenuColor = UIUtils.ajustColor(this.padrao,-10);
    	this.toggleMenuColor = UIUtils.ajustColor(this.padrao,-30);
    	
    	setLayout(new BorderLayout());
        setPreferredSize(new Dimension(expandedWidth, 600));
        setBackground( this.itemMenuColor );

        // Botão para expandir/encolher o menu
        JButton toggleButton = new JButton("☰ ");
        toggleButton.setFont(new Font("Arial", Font.BOLD, 26));
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground( this.toggleMenuColor );
        toggleButton.setBorderPainted(false);
        toggleButton.setPreferredSize(new Dimension(50, 50));
        
        toggleButton.setHorizontalTextPosition(SwingConstants.LEFT); // Texto à esquerda
        toggleButton.setHorizontalAlignment(SwingConstants.RIGHT);

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
    private void addMenuItem(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
       
    	if(menuItems.stream().anyMatch(item -> item.getText().equals(text) )) {
    		return;
    	}
    	
    	MenuItem item = new MenuItem(new ItemMenu(text, icon, action, null));
        menuItems.add(item);
        menuContainer.add(item);
        revalidate();
        repaint();
    }

    // Método para adicionar um item de menu com sub-itens
    private void addMenuItemWithSubItems(String text, List<MenuItem> subItems) {
    	
    	if(menuItems.stream().anyMatch(item -> item.getText().equals(text) )) {
    		return;
    	}
        MenuItem item = new MenuItem(new ItemMenu(text, UIManager.getIcon("Menu.arrowIcon"), null, null));
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
    
    public void addMenuItem(ItemMenu item) {
    	if(item.getAction() != null) {
			addMenuItem(item.getText(), item.getIcon(), item.getAction());
		}else {
			List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
			item.getSubItems().forEach(subitem->{
				subItems.add(new MenuItem(subitem));
			});
			addMenuItemWithSubItems(item.getText(), subItems);
		}
	}
    
    public void removeMenu(ItemMenu itemMenu) {
		itemMenu.setEnabled(false);
		var delete = new ArrayList<MenuItem>();
		for (MenuItem menuItem : menuItems) {
			if(menuItem.getId().equals(itemMenu.getId())){
				menuItem.setEnabled(false);
				menuItem.setVisible(false);
				menuItem.setBadgeNumber(0);
				delete.add(menuItem);
				System.out.println("menu removeEvent: "+menuItem.getId()+".badgeNumber");
				System.out.println("menu removeEvent: "+menuItem.getId()+".visible");
				ContextApp.getInstance().removeEvent(menuItem.getId()+".badgeNumber" );
				ContextApp.getInstance().removeEvent(menuItem.getId()+".visible");
			}
		}
		delete.forEach(menuItem->{
			menuContainer.remove(menuItem);
			menuItems.remove(menuItem);
		});
	}

    // Classe interna para representar um item de menu
    public class MenuItem extends JPanel {
		
    	private static final long serialVersionUID = 5652164465879606302L;
		
    	private final ItemMenu item;
    	private final LabelWithBadge labelIcon;
		private final JLabel label;
        private boolean isExpanded = false;
        private Color padrao = null;
        protected MenuItem root;
        private List<CustomSideMenu.MenuItem> filhos = new ArrayList<CustomSideMenu.MenuItem>();

        public MenuItem(ItemMenu item) {
        	this.item = item;
        	
        	this.padrao = getBackground();
            setLayout(new BorderLayout());
            setBackground( itemMenuColor );
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
            setBorder(new EmptyBorder(5, 10, 5, 5));

            labelIcon = new LabelWithBadge(this.item.getIcon(), 0);
            labelIcon.setFont(new Font("Arial", Font.PLAIN, 14));
            
            label = new JLabel(this.item .getText(), JLabel.LEFT);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setText(expanded ? this.item.getText() : "");
            
            	
            add(labelIcon, BorderLayout.WEST);
            add(label, BorderLayout.CENTER);
            
            ContextApp.getInstance().registerEvent(this.item.getId()+".badgeNumber", (date)-> {
            	this.setBadgeNumber((int)date) ;
            	labelIcon.setVisible(true);
            });
            
            ContextApp.getInstance().registerEvent(this.item.getId()+".visible", (date)-> {
            	this.setEnabled((boolean)date); 
            	this.setVisible((boolean)date) ;
            	this.item.setEnabled((boolean)date);
            });

            // Configura ação ao clicar no item principal
            if (this.item.getAction() != null) {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                    	for (MenuItem menuItem : menuItems) {
                    		menuItem.setBackground( itemMenuColor );
						}
                    	((MenuItem)e.getSource()).setBackground(padrao);
                    	item.getAction().accept(CustomSideMenu.MenuItem.this);
                    }
                });
            }
        }

        // Atualiza a visualização do item com base no estado do menu
        public void updateView(boolean expanded) {
        	label.setText(expanded ? this.item.getText() : "");
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
            filhos.forEach(menu->  menu.setVisible(isExpanded && menu.isEnabled()));
            ResizableIcon icon = new ResizableIcon(!isExpanded ? UIManager.getIcon("Menu.arrowIcon"): UIManager.getIcon("Table.descendingSortIcon"), 15, 15);
            labelIcon.setIcon(icon);
            label.setText(expanded ? this.item.getText() : "");
            revalidate();
            repaint();
        }
        
        public void setBadgeNumber(int badgeNumber) {
        	labelIcon.setBadgeNumber(badgeNumber);
        }

		public String getText() {
			return this.item.getText();
		}

		public Icon getIcon() {
			return this.item.getIcon();
		}

		public Consumer<CustomSideMenu.MenuItem> getAction() {
			return this.item.getAction();
		}

		public String getId() {
			return this.item.getId();
		}
		
    }

}