package ui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Classe para o menu lateral
public class CustomSideMenu extends JPanel {
    private static final long serialVersionUID = 670027307508379334L;
	private boolean expanded = true; // Indica se o menu está expandido
    private final int expandedWidth = 300; // Largura do menu expandido
    private final int collapsedWidth = 50; // Largura do menu encolhido
    private final List<MenuItem> menuItems = new ArrayList<>();

    private final JPanel menuContainer; // Contêiner para os itens do menu

    public CustomSideMenu() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(expandedWidth, 600));
//        setBackground(new Color(55, 55, 55));
        setBackground( newColor(getBackground(),-10) );

        // Botão para expandir/encolher o menu
        JButton toggleButton = new JButton("☰");
        toggleButton.setFont(new Font("Arial", Font.BOLD, 16));
        toggleButton.setFocusPainted(false);
//        toggleButton.setBackground(new Color(45, 45, 45));
        toggleButton.setBackground( newColor(toggleButton.getBackground(),-30) );
//        toggleButton.setForeground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setPreferredSize(new Dimension(50, 50));

        toggleButton.addActionListener(e -> toggleMenu());

        // Adiciona o botão no topo
        add(toggleButton, BorderLayout.NORTH);

        // Contêiner para itens de menu
        menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setBackground( newColor(menuContainer.getBackground(),-20) );
//        menuContainer.setBackground(new Color(55, 55, 55));
        menuContainer.setBorder(new EmptyBorder(10, 5, 10, 5));

        JScrollPane scrollPane = new JScrollPane(menuContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }
    
    public Color newColor(Color color, int increment) {
//    	int increment = 100; // Valor a ser somado

        // Converte a cor hexadecimal para o objeto Color
//        Color color = Color.decode(hexColor);

        // Obtém os valores RGB
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Soma o valor ao RGB, limitando entre 0 e 255
        red = Math.min(255, red + increment);
        green = Math.min(255, green + increment);
        blue = Math.min(255, blue + increment);

        // Cria uma nova cor com os novos valores RGB
        return new Color(red, green, blue);
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
    public void addMenuItem(String text, Icon icon, Runnable action) {
        MenuItem item = new MenuItem(text, icon, action);
        menuItems.add(item);
        menuContainer.add(item);
        revalidate();
        repaint();
    }

    // Método para adicionar um item de menu com sub-itens
    public void addMenuItemWithSubItems(String text, List<MenuItem> subItems) {
        MenuItem item = new MenuItem(text, UIManager.getIcon("Menu.arrowIcon"), null);
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
        private static final String TAB = "   ";
		private final JLabel label;
        private final String text;
        private boolean isExpanded = false;
        private Color padrao = null;
        protected MenuItem root;
        private List<CustomSideMenu.MenuItem> filhos = new ArrayList<CustomSideMenu.MenuItem>();

        public MenuItem(String text, Icon icon, Runnable action) {
        	
        	this.text = text;
        	this.padrao = getBackground();
            setLayout(new BorderLayout());
//            setBackground(new Color(65, 65, 65));
            setBackground( newColor(this.padrao,-10) );
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
            setBorder(new EmptyBorder(5, 10, 5, 5));

            label = new JLabel(text, icon, JLabel.LEFT);
//            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setText(expanded ? TAB+text : "");
            
            	
            add(label, BorderLayout.CENTER);

            // Configura ação ao clicar no item principal
            if (action != null) {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                    	for (MenuItem menuItem : menuItems) {
                    		menuItem.setBackground(newColor(padrao,-10));
						}
                    	((MenuItem)e.getSource()).setBackground(padrao);
                        action.run();
                    }
                });
            }
        }

        // Atualiza a visualização do item com base no estado do menu
        public void updateView(boolean expanded) {
        	label.setText(expanded ? TAB+text : "");
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
            label.setText(expanded ? TAB+this.text : "");
            label.setIcon(!isExpanded ? UIManager.getIcon("Menu.arrowIcon"): UIManager.getIcon("Table.descendingSortIcon"));
            revalidate();
            repaint();
        }
        
        
    }
}