package br.saogeraldo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.util.Util;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ModeloTabela;

public class TelaListagemDizimista extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabela;
	private ModeloTabela modelo; 
	private Map<Integer, DizimistaVO> mapa;
	
	public TelaListagemDizimista(TelaMenu telaMenu, List<DizimistaVO> lista){
		super("Listagem", true, true, true, true);
		telaMenu.addJanela(this);
		
		modelo = new ModeloTabela(new String[]{ "Cod", "Nome", "Data Nascimento"});
		mapa = new HashMap<Integer, DizimistaVO>();		
		int index = 0;
		for(DizimistaVO obj: lista){
			modelo.add(transformaToArray(obj));// adiciona o objeto ao relatório
			mapa.put(index, obj);
			index++;
		}	
		
		tabela = new JTable(modelo);
		tabela.setAutoCreateRowSorter(true);
		tabela.setRowHeight(22); // tamanho da linha
		dimensionaColuna(tabela.getColumnModel()); 
		tabela.getTableHeader().setReorderingAllowed(false); // impede que o usuário mova as colunas 
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tabela.setPreferredScrollableViewportSize(new Dimension(630,200));	
		
		setLayout(new FlowLayout());
		
		JButton botaoVoltar = new JButton(Mensagem.LABEL_CANCELAR);
					
		botaoVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voltar();				
			}			
		});

		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
		add(BorderLayout.SOUTH,botaoVoltar);			
		setSize(630, 300);
		setVisible(true);
	}
	
	public TelaListagemDizimista(TelaMenu telaMenu, List<DizimistaVO> lista, final TelaListagem telaListagem){
		super("Click na linha desejada", true, true, false, false);
		telaMenu.addJanela(this);
		modelo = new ModeloTabela(new String[]{ "Cod", "Nome", "Data Nascimento"});
		mapa = new HashMap<Integer, DizimistaVO>();		
		int index = 0;
		for(DizimistaVO obj: lista){
			modelo.add(transformaToArray(obj));// adiciona o objeto ao relatório
			mapa.put(index, obj);
			index++;
		}	
		
		tabela = new JTable(modelo);
		tabela.setAutoCreateRowSorter(true);
		tabela.setRowHeight(22); // tamanho da linha
		dimensionaColuna(tabela.getColumnModel()); 
		tabela.getTableHeader().setReorderingAllowed(false); // impede que o usuário mova as colunas 
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tabela.setPreferredScrollableViewportSize(new Dimension(480,200));	
		
		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				trataEvento(telaListagem);				
			}
		});
		
		InputMap im = tabela.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		im.put(enter, im.get(KeyStroke.getKeyStroke(KeyEvent.VK_GREATER, 0)));
		Action enterAction = new AbstractAction() {		
			private static final long serialVersionUID = -1L;
			public void actionPerformed(ActionEvent e) {
				modelo = (ModeloTabela)((JTable) e.getSource()).getModel();
				trataEvento(telaListagem);
			}
		};
		tabela.getActionMap().put(im.get(enter), enterAction);
				
		setLayout(new FlowLayout());
		
		JButton botaoVoltar = new JButton(Mensagem.LABEL_CANCELAR);
					
		botaoVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voltar();				
			}			
		});

		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
		add(BorderLayout.SOUTH,botaoVoltar);			
		setVisible(true);
	}
	
	public Object[] transformaToArray(DizimistaVO dz) {		
		Object[] linha = new Object[3];
		int index = 0;
		linha[index++] = dz.getIdDizimista();
		linha[index++] = dz.getNome();
		linha[index++] = Util.convertDateToString(dz.getDtNascimento());
		return linha;
	}
	
	public void dimensionaColuna(TableColumnModel modelo){
		modelo.getColumn(0).setMinWidth(50);
		modelo.getColumn(0).setMaxWidth(50);	
		modelo.getColumn(2).setMinWidth(120);
		modelo.getColumn(2).setMaxWidth(120);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		modelo.getColumn(2).setCellRenderer(centerRenderer);
	}
	
	private void voltar() {
		dispose();		
	}

	private void trataEvento(final TelaListagem tela) {
		int linha = tabela.getSelectedRow();
		tela.setVisible(true);
		tela.setObjectToTela(mapa.get(linha));
		voltar();
	}	
}
