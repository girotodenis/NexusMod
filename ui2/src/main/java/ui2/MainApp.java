package ui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.dsg.ui.util.AppUtilities;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class MainApp {
	
	
    public static void main(String[] args) {
    	
//           mainPanel.addMenuItem("Menu Modal ", UIManager.getIcon("FileView.directoryIcon"), (itemMenu) -> {
           	  
//           });
           // Adicionar um item com sub-itens
    	
    	AppUtilities.builder()
    		.title("teste")
    		.size(1024, 768)
    		.lookAndFeel(FlatDarculaLaf.class)
    		.addMenuItem("Menu 1 Tela Icons", UIManager.getIcon("FileView.directoryIcon"), (mainPanel,itemMenu) -> mainPanel.showContent(createSubItemPanel("Menu 1")))
	    	.addMenuItem("Menu 2 BadgeNumber ", UIManager.getIcon("FileView.directoryIcon"), (mainPanel,itemMenu) -> itemMenu.setBadgeNumber(2))
	    	.addMenuItem("Menu Modal ", UIManager.getIcon("FileView.directoryIcon"), (mainPanel,itemMenu) -> {
	    		JPanel content = new JPanel();
                content.setLayout(new BorderLayout());
                content.add(new JLabel("Este é o conteúdo da modal", SwingConstants.CENTER), BorderLayout.CENTER);
                // Botões do rodapé
                JButton button1 = new JButton("Salvar");
                JButton button2 = new JButton("Cancelar");
                JButton[] footerButtons = null;//{button1, button2};
                // Abre a modal
                mainPanel.toast("Erro", "Erro 123 123 123", UIManager.getIcon("OptionPane.errorIcon"), Color.RED);
	    	})
	    	.addMenuItem("Menu 3","SubItem 3.2 FlatMacDarkLaf", UIManager.getIcon("FileView.floppyDriveIcon"), (mainPanel,itemMenu) -> mainPanel.updateAll(FlatMacDarkLaf.class))
	    	.addMenuItem("Menu 3","SubItem 3.2 FlatDarculaLaf", UIManager.getIcon("FileChooser.homeFolderIcon"), (mainPanel,itemMenu) -> mainPanel.updateAll(FlatDarculaLaf.class))
	    	.addMenuItem("Menu 3","SubItem 3.3 FlatIntelliJLaf", UIManager.getIcon("HelpButton.icon"), (mainPanel,itemMenu) -> mainPanel.updateAll(FlatIntelliJLaf.class))
	    	.addMenuItem("Menu 3","SubItem 3.4 FlatMacLightLaf", UIManager.getIcon("Tree.leafIcon"), (mainPanel,itemMenu) -> mainPanel.updateAll(FlatMacLightLaf.class))
    	.build();
    }
    
    
    
 // Método para criar um painel para subitens
    private static JPanel createSubItemPanel(String content) {
    	JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Usar BorderLayout para adicionar título e tabela

        // Adicionar título
        JLabel titleLabel = new JLabel(content);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Criar um painel para os ícones
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridLayout(0, 3)); // 0 linhas e 5 colunas

        // Adicionar ícones ao painel
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object valor = keys.nextElement();
            if (valor instanceof String) {
                String key = (String) valor;
                Icon icon = UIManager.getIcon(key);
                if (icon != null) {
                    JLabel iconLabel = new JLabel(key, icon, JLabel.CENTER);
//                    iconLabel.setForeground(Color.WHITE);
                    iconLabel.setHorizontalAlignment(JLabel.CENTER);
                    iconPanel.add(iconLabel);
                }
            }
        }

        // Adicionar o painel de ícones ao painel principal
        panel.add(iconPanel, BorderLayout.CENTER);
        
        return panel;
    }

    
}