package br.saogeraldo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.util.Data;

public class Relatorio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabela;
	private DizimistaDAO dao;
	private Modelo modelo; 
	private List<DizimistaVO> lista;
		
	public Relatorio(List<DizimistaVO> lista){
		this.lista = lista;
	}
	public Relatorio(){
		
	}
	
	public boolean isAll(){
		dao = new DizimistaDAO();		
		// obtem a lista de produtos de banco de dados
		try {						
			lista = dao.getDizimistaAll();			
			
		} catch (SQLException e) {			
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados","Erro!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return (lista.size()!=0);
	}
		
	public void montaTabela() {		
		// constrói a tabela			
		
		modelo = new Modelo();
		Iterator<DizimistaVO> it = lista.listIterator();
		
		// enquanto tiver elemento na lista faça
		while(it.hasNext()){
			DizimistaVO cliente = it.next();
			modelo.add(transforma(cliente));// adiciona o objeto cliente ao relatório
		}		
		
		tabela = new JTable(modelo);
		tabela.setRowHeight(22); // tamanho da linha
		dimensionaColuna(tabela.getColumnModel()); 
		tabela.getTableHeader().setReorderingAllowed(false); // impede que o usuário mova as colunas 
		tabela.setPreferredScrollableViewportSize(new Dimension(800,300));		
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		JButton botaoVoltar = new JButton("Voltar");
					
		botaoVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voltar(evt);				
			}			
		});

		JScrollPane scrollPane = new JScrollPane(tabela);
		c.add(scrollPane);
		c.add(BorderLayout.SOUTH,botaoVoltar);			
		setSize(850, 400);
		setTitle("Relação de Dizimistas cadastrados");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
	}

	public Object[] transforma(DizimistaVO dz) {		
		Object[] linha = new Object[8];		
		linha[0] = dz.getIdDizimista();
		linha[1] = dz.getNome();
		linha[2] = Data.converteUSAtoBR(dz.getDtNascimento(), true);		
		linha[3] = dz.getNomeConjugue();			
		linha[4] = Data.converteUSAtoBR(dz.getDtNascimentoConjugue(), true);		
		linha[5] = dz.getEndereco();
		linha[6] = dz.getTelefone();		
		linha[7] = Data.converteUSAtoBR(dz.getDtCasamento(), true);
		
		return linha;
	}
	
	public void dimensionaColuna(TableColumnModel modelo){
		modelo.getColumn(0).setPreferredWidth(1);		
		modelo.getColumn(2).setPreferredWidth(25);
		modelo.getColumn(4).setPreferredWidth(25);
		modelo.getColumn(6).setPreferredWidth(38);	
		modelo.getColumn(7).setPreferredWidth(25);			
	}
	
	private void voltar(ActionEvent evt) {
		dispose();		
	}	
	
}

// modelo do relatório de clientes
class Modelo extends AbstractTableModel {
	
	public static String tipo;
	private static final long serialVersionUID = 1L;	
	private String[] colunas = { "Cod", "Nome", "Dt Nasc.", "Conjugue", "Dt N. Conj", "Endereço", "Telefone" , "Dt Casam."};
	private ArrayList<Linha> dados;
	int cont = 0;

	public Modelo() {
		dados = new ArrayList<Linha>();
	}

	public void add(Object[] linha) {
		dados.add(new Linha(linha));
	}

	public int getColumnCount() {
		return colunas.length;
	}	

	public int getRowCount() {
		return dados.size();
	}

	public String getColumnName(int col) {
		return colunas[col];
	}

	public Object getValueAt(int row, int col) {
		return ((Linha) dados.get(row)).getCelula(col);
	}

	public Class<? extends Object> getColumnClass(int c) {
		return !dados.isEmpty() ? ((Linha) dados.get(0)).getCelula(c)
				.getClass() : null;
	}

	public void setValueAt(Object value, int row, int col) {
		((Linha) dados.get(row)).setCelula(value, col);
	}

	public boolean isCellEditable(int row, int col) {		
		return false;
	}

}

class Linha {

	private Object[] linha;

	public Linha(Object[] linha) {
		this.linha = linha;
	}

	public Object getCelula(int coluna) {
		if(linha[coluna] == null){
			return "";
		}
		return linha[coluna];
	}

	public void setCelula(Object valor, int coluna) {
		if(valor == null){
			linha[coluna] = "";
		}else{
			linha[coluna] = valor;
		}
		
	}
}
