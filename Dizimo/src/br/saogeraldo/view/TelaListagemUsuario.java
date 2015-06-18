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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ModeloTabela;

public class TelaListagemUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabela;
	private ModeloTabela modelo; 
	private Map<Integer, UsuarioVO> mapa;
		
	public TelaListagemUsuario(TelaMenu telaMenu, List<UsuarioVO> lista, final TelaUsuario telaUsuario){
		super("Click na linha desejada", true, true, false, false);
		telaMenu.addJanela(this);
		modelo = new ModeloTabela(new String[]{ "Nome"});
		mapa = new HashMap<Integer, UsuarioVO>();		
		int index = 0;
		for(UsuarioVO obj: lista){
			modelo.add(transformaToArray(obj));// adiciona o objeto ao relatório
			mapa.put(index, obj);
			index++;
		}	
		
		tabela = new JTable(modelo);
		tabela.setRowHeight(22); // tamanho da linha
		dimensionaColuna(tabela.getColumnModel()); 
		tabela.getTableHeader().setReorderingAllowed(false); // impede que o usuário mova as colunas 
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tabela.setPreferredScrollableViewportSize(new Dimension(450, 100));	
		
		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				trataEvento(telaUsuario);				
			}
		});
		
		InputMap im = tabela.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		im.put(enter, im.get(KeyStroke.getKeyStroke(KeyEvent.VK_GREATER, 0)));
		Action enterAction = new AbstractAction() {		
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				modelo = (ModeloTabela)((JTable) e.getSource()).getModel();
				trataEvento(telaUsuario);
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
	
	public Object[] transformaToArray(UsuarioVO user) {		
		Object[] linha = new Object[1];
		int index = 0;
		linha[index++] = user.getNome();
		return linha;
	}
	
	public void dimensionaColuna(TableColumnModel modelo){
		modelo.getColumn(0).setMinWidth(100);
	}
	
	private void voltar() {
		dispose();		
	}

	private void trataEvento(final TelaUsuario tela) {
		int linha = tabela.getSelectedRow();
		tela.setVisible(true);
		tela.setObjectToTela(mapa.get(linha));
		tela.habilitaBotoes(false);
		voltar();
	}	
}
