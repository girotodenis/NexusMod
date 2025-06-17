package com.dsg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.ui.componente.ContextMenu;
import com.dsg.ui.componente.CustomModal;
import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.componente.CustomSideMenu.MenuItem;
import com.dsg.ui.componente.ItemMenu;
import com.dsg.ui.util.UIUtils;

public class JPanelApp extends JPanel {

	private static final long serialVersionUID = 8898685804729074138L;

	private static final ContextApp context = new ContextAppImp();
	private JPanel headerPanel;
	private JPanel sideMenuPanel;
	private JPanel contentPanel;
	private JPanel footerPanel;
	private Class<?> classLook;
	
	private CustomModal modalPanel; // Painel que funciona como a modal

	CustomSideMenu sideMenu;
	List<ItemMenu> itens = new ArrayList<ItemMenu>();
	ItemMenu lastItemMenu;
	
	public JPanelApp(Class<?> classLook) {
		try {
			this.classLook = classLook;
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
    
    public String addMenuItem(String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
    	ItemMenu itemMenu = new ItemMenu(text, icon, action, null);
		itens.add(itemMenu);
    	return itemMenu.getId();
    }
    
    public String addMenuItem(String group, String text, Icon icon, Consumer<CustomSideMenu.MenuItem> action) {
    	
    	ItemMenu item = null;
    	var op = itens.stream().filter(m->m.getText().equals(group)).findFirst();
    	if(op.isPresent()){
    		item = op.get();
    	}else {
    		System.out.println("criou item "+group);
    		item = new ItemMenu(group, UIManager.getIcon("Menu.arrowIcon"), null, null);
    		itens.add(item);
    	}
		
		return item.addSubItems(text,icon,action);
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
		
		if(sideMenu==null) {
			sideMenu = new CustomSideMenu();
    	}
		
    	itens.forEach(item->{
    		if(item.getAction() != null) {
    			var itemMenu = sideMenu.addMenuItem(item.getId(), item.getText(), item.getIcon(), item.getAction());
    			registarEventosMenu(item.getId(), itemMenu);
    		}else {
    			List<CustomSideMenu.MenuItem> subItems = new ArrayList<>();
    			item.getSubItems().forEach(subitem->{
    				MenuItem itemMenu = sideMenu.new MenuItem(subitem.getId(), subitem.getText(), subitem.getIcon(), subitem.getAction() );
					subItems.add(itemMenu);
					registarEventosMenu(item.getId(), itemMenu);
    			});
    			sideMenu.addMenuItemWithSubItems(item.getText(), subItems);
    		}
    	});
	}

	private void registarEventosMenu(String id, MenuItem itemMenu) {
		if( !context.contens(id+".badgeNumber")){
			System.out.println("Registra evento badgeNumber: " + id+".badgeNumber");
			context.registerEvent(id+".badgeNumber", (date)-> {
				Timer timer = new Timer(10, e -> {
					System.out.println("Registra evento badgeNumber: " + id+".badgeNumber " + date);
					itemMenu.setBadgeNumber((int)date);
					sideMenu.revalidate();
					sideMenu.repaint();
				});
				timer.setRepeats(false); // Garantir que o Timer execute apenas uma vez
				timer.start();
			});
		}
		if( !context.contens(id+".visible")){
			System.out.println("Registra evento visible: " + id+".visible");
			context.registerEvent(id+".visible", (date)-> itemMenu.setVisible((boolean)date) );
		}
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
    
   
    
    public void showContent(JPanel panel) {
    	contentPanel.removeAll();
    	contentPanel.setLayout(new BorderLayout());
    	System.out.println("showContent: contentPanel " + contentPanel.getBackground());
    	System.out.println("showContent: " + panel.getBackground());
    	JScrollPane scrollPane = new JScrollPane(panel);
    	scrollPane.setBorder(null);
    	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    	// Adicionar o novo conteúdo ao painel de conteúdo
    	contentPanel.add(scrollPane, BorderLayout.WEST); // Adiciona o painel no centro
    	// Atualizar o painel
    	contentPanel.revalidate();
    	contentPanel.repaint();
    }
    
    public void updateAll(Class<?> classLook) {
    	this.classLook = classLook;
    	Timer timer = new Timer(10, e -> {
			updateAll(classLook.getName());
		});
		timer.setRepeats(false); // Garantir que o Timer execute apenas uma vez
		timer.start();
    }
    
    public void updateAll(String classLook) {
    	try {
    		UIManager.setLookAndFeel(classLook);
    		this.removeAll();
    		setLayout(new BorderLayout());
    		
    		headerPanel = createHeaderPanel();
    		add(headerPanel, BorderLayout.NORTH);
    		
    		// Menu lateral
    		sideMenuPanel = createSideMenuPanel(); // Não precisa mais do frame aqui
    		add(sideMenuPanel, BorderLayout.WEST);

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
    
    public void toast(String title, String content, Icon icon, Color color) {
    	JPanel contentToast = new JPanel();
    	contentToast.setLayout(new BorderLayout());
    	JLabel comp = new JLabel(content, icon, SwingConstants.CENTER);
    	comp.setFont(new Font("Arial", Font.BOLD, 14));
    	comp.setForeground(Color.WHITE);
		contentToast.add(comp, BorderLayout.CENTER);
		contentToast.setBackground(color);
    	openModal(title, contentToast, new Dimension(300, 180), null, "top-right", color);
    	
    	try {
    		// Definir um Timer para remover o painel após 4 segundos
    		Timer timer = new Timer(3000, e -> {
    			closeModal();
    		});
    		timer.setRepeats(false); // Garantir que o Timer execute apenas uma vez
    		timer.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void openModal(String title, JPanel content, Dimension size, JButton[] footerButtons, String positionn, Color color) {
    	
    	SwingUtilities.invokeLater(() -> {
    		// Modal (escondido inicialmente)
    		remove(this.headerPanel);
    		remove(this.sideMenuPanel);
    		remove(this.footerPanel);
    		remove(this.contentPanel);
    		modalPanel = createModalPanel(color);
    		
    		add(modalPanel, BorderLayout.CENTER); // Adiciona o modal ao painel principal
    		
    		add(this.contentPanel, BorderLayout.CENTER); // Adiciona o modal ao painel principal
    		add(this.footerPanel, BorderLayout.SOUTH);
    		add(this.sideMenuPanel, BorderLayout.WEST);
    		add(this.headerPanel, BorderLayout.NORTH);
    		
    		
    		modalPanel.openModal(title, content, size, footerButtons, positionn);
    		revalidate();
    		repaint();
    	});
    }
    
    public void closeModal() {
    	modalPanel.removeAll();
    	remove(modalPanel);
    	modalPanel = null;
    	add(contentPanel, BorderLayout.CENTER);
    	revalidate();
		repaint();
    }
    
    private CustomModal createModalPanel(Color color) {
		CustomModal customModal = new CustomModal(color);
        customModal.setBounds(0, 0, getWidth(), getHeight()); // Ocupa toda a área da janela
        customModal.setVisible(true); // Inicialmente invisível
        return customModal;
	}

	public void lastItemMenu(String id) {
    	var op = itens.stream().filter(m->m.getText().equals(id)).findFirst();
    	if(op.isPresent()){
    		lastItemMenu = op.get();
    	}
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public ContextApp getContext() {
		return context;
	}
	
	

}
