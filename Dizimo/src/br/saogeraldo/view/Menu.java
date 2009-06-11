package br.saogeraldo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.negocio.RelatorioBO;

public class Menu extends JFrame{		

	private static final long serialVersionUID = 1L;
	private JLabel painel;
	private Icon logo;
	 
	//método construtor  
	public Menu() {
		super("Controle de Dizimista");	
		
		// Menu Dizimista //  
		JMenuBar menuBar = new JMenuBar();			
		JMenu menuCliente = new JMenu("Dizimista");
		menuCliente.setMnemonic('D');
		JMenuItem cadastrarCliente = new JMenuItem("Cadastrar");
		cadastrarCliente.setMnemonic('C');
		JMenuItem alterarCliente = new JMenuItem("Alterar");
		alterarCliente.setMnemonic('A');
		JMenuItem excluirCliente = new JMenuItem("Excluir");
		excluirCliente.setMnemonic('E');
		
		cadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cadastrarCliente(evt);
			}

		});
		alterarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				alterarCliente(evt);
			}

		});
		excluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				excluirCliente(evt);
			}

		});
		
		menuCliente.add(cadastrarCliente);
		menuCliente.add(alterarCliente);
		menuCliente.add(excluirCliente);
		
		menuBar.add(menuCliente);
				
		// Menu Pesquisar //
		JMenu menuPesquisar = new JMenu("Pesquisar");
		menuPesquisar.setMnemonic('P');
		
		JMenuItem todos = new JMenuItem("Todos");
		todos.setMnemonic('T');
		JMenuItem dataAniversario = new JMenuItem("Data de Aniversário");
		dataAniversario.setMnemonic('A');
		JMenuItem dataCasamento = new JMenuItem("Data de Casamento");
		dataCasamento.setMnemonic('D');
		JMenuItem nome = new JMenuItem("Por Nome");
		nome.setMnemonic('N');
		JMenuItem codigo = new JMenuItem("Por Código");
		codigo.setMnemonic('C');
		
		todos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				searchAll(evt);
			}			
		});
		
		dataAniversario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}			
		});
		
		dataCasamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});
		
		nome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				searchName(evt);
			}
		});
		codigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				searchCode(evt);
			}
		});
		
		menuPesquisar.add(codigo);		
		menuPesquisar.add(nome);
		menuPesquisar.add(todos);
	//	menuArquivo.add(dataAniversario);
	//	menuArquivo.add(dataCasamento);
		
		menuBar.add(menuPesquisar);
		
		// Menu Relatórios //
		JMenu menuRelatorio = new JMenu("Relatórios");
		menuRelatorio.setMnemonic('R');
		
		JMenuItem relTodos = new JMenuItem("Aniversário - Todos");
		relTodos.setMnemonic('T');
		JMenuItem relAniversario = new JMenuItem("Aniversário");
		relAniversario.setMnemonic('A');
		JMenuItem relCasamento = new JMenuItem("Casamento");
		relCasamento.setMnemonic('C');
		
		JMenuItem relRecadastramento = new JMenuItem("Recadastramento");
		relCasamento.setMnemonic('R');
		
		relTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsAll(evt);
			}			
		});
		relAniversario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsAniversario(evt);
			}			
		});
		relCasamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsCasamento(evt);
			}			
		});
		
		relRecadastramento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsRecadastramento(evt);
			}			
		});
		
		
		menuRelatorio.add(relAniversario);
		menuRelatorio.add(relCasamento);
		menuRelatorio.add(relTodos);
		menuRelatorio.add(relRecadastramento);
		
		menuBar.add(menuRelatorio);
				
		// Menu Sair //
		JMenuItem menuSair = new JMenuItem("Sair");	
		menuSair.setMnemonic('S');	
				
		JMenuItem usuarioLogado = new JMenuItem("Usuário: "+Login.usuario.getNome().toUpperCase());
		usuarioLogado.setEnabled(false);				
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sair(evt);
			}
		});
				
		menuBar.add(menuSair);
		
		JMenuItem aux1 = new JMenuItem("        ");	
		aux1.setEnabled(false);
		menuBar.add(aux1);		
		menuBar.add(usuarioLogado);
	
		logo = new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/logo.png"));
		painel = new JLabel( logo, SwingConstants.CENTER );
		painel.setForeground(Color.WHITE);
		painel.setFont( new Font( "Arial", Font.ITALIC, 55 ) );		
		
		add( painel, BorderLayout.CENTER ); 
		setJMenuBar(menuBar);				
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		setSize(730, 530);		
		setLocationRelativeTo(null);
		setVisible(true);
				
	}		
	//fim do construtor     
	
	private void reportsAll(ActionEvent evt) {
		try{
			new RelatorioBO().runAniversarioAll();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void reportsAniversario(ActionEvent evt) {
		new TelaAniversario().setVisible(true);
	}
	
	private void reportsCasamento(ActionEvent evt) {
		new TelaCasamento().setVisible(true);
	}
	
	private void reportsRecadastramento(ActionEvent evt) {
		try{
			new RelatorioBO().runRecadastramento();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void searchAll(ActionEvent evt) {
		Relatorio rel = new Relatorio();
		if(rel.isAll()){
			rel.montaTabela();
			rel.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this, "Nenhum Registro encontrado!","Atenção!",JOptionPane.WARNING_MESSAGE);
		}
	}

	private void searchName(ActionEvent evt) {		
		String nome = JOptionPane.showInputDialog(null,"Digite o nome do Dizimista:","Pesquisa por Nome",JOptionPane.QUESTION_MESSAGE);	
		List<DizimistaVO> nomes = null;
		
		if (nome == null){
			repaint();
		}else{		
			DizimistaDAO dao = new DizimistaDAO();
			try {
				nomes = dao.getDizimistaByName(nome);					
				if (nomes.size()==1){
					TelaDizimista tela = new TelaDizimista("Pesquisa por Nome");
					tela.go("Consultar",false,nomes.get(0));
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
				}else if (nomes.size()>1){
					Relatorio rel = new Relatorio(nomes);					
					rel.montaTabela();
					rel.setVisible(true);					
				}else{
					JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
				}				
			} catch (SQLException e) {			
				e.printStackTrace();
			}		
		}		
	}
	
	private void searchCode(ActionEvent evt) {
		DizimistaVO cliente;
		TelaDizimista tela = new TelaDizimista("Pesquisa por Código");				
		cliente = getCliente("Código");
		
		if (cliente == null){
			JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
		}else{
			tela.go("Consultar",false,cliente);
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
		}	
	}
	
	// método executado quando clica no botão sair
	private void sair(ActionEvent evt) {		
		int res = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?","Sair",JOptionPane.YES_NO_OPTION);
		if (res == 0){
			System.exit(0);	
		}		
	}

	// chama a tela cliente
	private void cadastrarCliente(ActionEvent evt) {
		TelaDizimista tela = new TelaDizimista("Cadastrar");
		tela.go("Salvar",true, null);
		tela.setLocationRelativeTo(null);
		tela.setVisible(true);		
		
	}
	// método executado quando clica no botão alterar cliente
	private void alterarCliente(ActionEvent evt) {
		DizimistaVO cliente;
		TelaDizimista tela = new TelaDizimista("Alterar dados");				
		cliente = getCliente("Alterar dados");
		
		if (cliente == null){
			JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
		}else{
			tela.go("Alterar",true,cliente);
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
		}	
			
	}
	// método executado quando clica no botão excluir cliente
	private void excluirCliente(ActionEvent evt) {
		DizimistaVO cliente;
		TelaDizimista tela = new TelaDizimista("Registro a ser excluído");				
		cliente = getCliente("Excluir Registro");
		
		if (cliente == null){
			JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
		}else{
			tela.go("Excluir",false,cliente);
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
			}	
	}
	
	private DizimistaVO getCliente(String msg) {
		DizimistaVO cliente = null;	
		String aux = JOptionPane.showInputDialog(null,"Digite o Código do Dizimista:",msg,JOptionPane.QUESTION_MESSAGE);
		Integer cod = null;		
			if(aux != null){
				try{
					cod = Integer.parseInt(aux);	
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(this, "Formato de dados inválido!!","Erro!",JOptionPane.ERROR_MESSAGE);				
				}
			}
			if (cod == null){
				repaint();
			}else{		
				DizimistaDAO dao = new DizimistaDAO();
				try {
					cliente = dao.getDizimistaByCodigo(cod);
				} catch (SQLException e) {			
					e.printStackTrace();
				}		
			}	
		
		return cliente;
	}
	
	/*
	// obtém os dados do cliente do BD através do nome digitado pelo usuário
	private ClienteVO getCliente(String msg) {
		ClienteVO cliente = null;		
		String nome = JOptionPane.showInputDialog(null,"Digite o nome do cliente:",msg,JOptionPane.QUESTION_MESSAGE);	
		List<String> nomes = null;
		
		if (nome == null){
			repaint();
		}else{		
			ClienteDAO dao = new ClienteDAO();
			try {
				nomes = dao.getNomeClientes(nome);
					
				if (nomes.size()==1){
					cliente = dao.getCliente(nome);	
				}else if (nomes.size()>1){
					nome = populaCombo(nomes);
					cliente = dao.getCliente(nome);		
				}					
			} catch (SQLException e) {			
				e.printStackTrace();
			}		
		}		
		
		return cliente;
	}/*
	// obtem os dados do produto do BD através do nome digitado pelo usuário
	private ProdutoVO getProduto(String msg) {
		ProdutoVO produto = null;		
		String nome = JOptionPane.showInputDialog(null,"Digite o nome do produto:",msg,JOptionPane.QUESTION_MESSAGE);	
		List<String> nomes = null;
		
		if (nome == null){
			repaint();
		}else{
			ProdutoDAO dao = new ProdutoDAO();
			try {
				nomes = dao.getNomeProdutos(nome);
					
				if (nomes.size()==1){
					produto = dao.getProduto(nome);	
				}else if (nomes.size()>1){
					nome = populaCombo(nomes);
					produto = dao.getProduto(nome);		
				}					
			} catch (SQLException e) {			
				e.printStackTrace();
			}		
		}		
		
		return produto;
	}*/
	

	/*
	// método executado quando clica no botão excluir produto
	private void excluirProduto(ActionEvent evt) {
		DizimistaVO dz = null;
		TelaDizimista tela = new TelaDizimista("Dados do Dizimista a ser excluído");				
		d = getProduto("Produto a ser excluído");
		
		if (produto == null){
			JOptionPane.showMessageDialog(this, "Atenção, Nenhum produto encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
		}else{
			tela.go("Excluir",false,produto);
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
			}	
		
	}
	// método executado quando clica no botão alterar produto
	private void alterarProduto(ActionEvent evt) {
		DizimistaVO dz;
		TelaDizimista tela = new TelaDizimista("Dados do Dizimista a ser alterado");				
		dz = getProduto("Alteração de produto");
		
		if (produto == null){
			JOptionPane.showMessageDialog(this, "Atenção, Nenhum produto encontrado!!","Atenção",JOptionPane.WARNING_MESSAGE);				
		}else{
			tela.go("Alterar",true,produto);
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
			}	
		
	}	/*
	
	// consulta todos os nomes correspondentes do BD ao que o usuário digitou e popula a combo de escolha
	private String populaCombo(List<String> lista) {		
		
		String nome = "";
		String[] nomes = new String[lista.size()];	
		Iterator<String> it = lista.iterator();
		for(int i =0; it.hasNext(); i++){
			nomes[i] = it.next();			
		}
			
		Object[] combo = new Object[2];
		combo[0] = new JComboBox(nomes);
		((JComboBox)combo[0]).setMaximumRowCount(6);
		combo[1]= "OK";
		
		int res = JOptionPane.showOptionDialog(null, "Lista de nomes encontrados:", "Selecione", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, combo, combo[0]);
			
		if (res == 1){
			nome = ((JComboBox)combo[0]).getSelectedItem().toString();			
			return nome;
		}else{
			return null;
		}
		
	}*/
} 