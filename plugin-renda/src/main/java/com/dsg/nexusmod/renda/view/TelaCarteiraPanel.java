package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.dsg.nexusmod.model.ModelObserver;
import com.dsg.nexusmod.renda.model.CarteiraCommand;
import com.dsg.nexusmod.renda.model.CarteiraCommand.COMMAND;
import com.dsg.nexusmod.renda.model.CarteiraModel;
import com.dsg.nexusmod.renda.model.CarteirasModel;

public class TelaCarteiraPanel extends JPanel implements ModelObserver<List<CarteiraModel>> {

    private static final long serialVersionUID = 1L;
	private final JTable tabela;
    private final DefaultTableModel tableModel;
    private final CarteirasModel carteirasModel;
    
    JButton btnCriarCarteira = new JButton("Criar Carteira");

    public TelaCarteiraPanel(CarteirasModel model) {
        this.carteirasModel = model;
        model.addObserver(this);

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Carteiras");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(titulo, BorderLayout.WEST);

        headerPanel.add(btnCriarCarteira, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        String[] colunas = {"Carteira", "Descrição", "Valor Investido", "Rentabilidade (%)", "Ações"};
        
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabela = new JTable(tableModel);
        tabela.setRowHeight(35);
        tabela.getColumn("Ações").setCellRenderer(new AcaoCellRenderer());
        tabela.getColumn("Ações").setCellEditor(new AcaoCellEditor());

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Inicializar com os dados existentes
        update(model.getModel());
        
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    int row = tabela.rowAtPoint(e.getPoint());
                    // Pega os dados do modelo
                    CarteiraModel carteira = (CarteiraModel) carteirasModel.getModel().get(row);
                    carteira.execute(COMMAND.VIEW);
                }
            }
        });
    }


	@Override
    public void update(List<CarteiraModel> carteiras) {
        tableModel.setRowCount(0); // limpa
        for (CarteiraModel c : carteiras) {
            tableModel.addRow(new Object[]{
                c.getNome(),
                c.getDescricao(),
                c.getValorInvestido(),
                c.getRentabilidade(),
                null // Passa o model para o botão agir sobre ele
            });
        }
    }

    class AcaoCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return criarPainelAcoes(carteirasModel.getModel().get(row));
        }
    }

    class AcaoCellEditor extends AbstractCellEditor implements TableCellEditor {
        
    	private JPanel painelAtual;
    	
    	public AcaoCellEditor() {
		}

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            painelAtual = criarPainelAcoes(carteirasModel.getModel().get(row));
            return painelAtual;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    private JPanel criarPainelAcoes(CarteiraModel carteira) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEditar = new JButton("(E)");
        JButton btnVisualizar = new JButton("(<o>)");

        btnEditar.addActionListener(e -> {
            carteira.execute(CarteiraCommand.COMMAND.EDIT);
        });

        btnVisualizar.addActionListener(e -> {
            carteira.execute(CarteiraCommand.COMMAND.VIEW);
        });

        painel.add(btnEditar);
        painel.add(btnVisualizar);
        return painel;
    }

   
}
