package br.saogeraldo.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.TipoPesquisa;

public class TelaMenu extends JFrame{		

	private static final long serialVersionUID = 1L;
	JDesktopPane desktop;
	private Image logo;
	private TelaPesquisa telaPesquisaAniversario;
	private TelaPesquisa telaPesquisaCasamento;
	private TelaDizimista telaDizimista;
	public final Integer ESPACO_ENTRE_JANELA = 120;
	private static Logger logger = Logger.getLogger(TelaMenu.class);
	 
	//método construtor  
	public TelaMenu() {
		super("Controle de Dizimista");	
		
		logo = new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/logo.png")).getImage();
		desktop = new BackgroundedDesktopPane(logo);	
		add(desktop);
		
		// Menu Dizimista //  
		JMenuBar menuBar = new JMenuBar();			
		JMenu menuCadastro = new JMenu("Cadastro");
		menuCadastro.setMnemonic('D');
		
		JMenuItem manterCliente = new JMenuItem("Dizimista");
		manterCliente.setMnemonic('C');
		
		manterCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cadastrarDizimista();
			}
		});
		
		menuCadastro.add(manterCliente);
		
		menuBar.add(menuCadastro);
				
		// Menu Pesquisar //
		JMenu menuPesquisar = new JMenu("Pesquisar");
		menuPesquisar.setMnemonic('P');
		
		JMenuItem todos = new JMenuItem("Todos");
		todos.setMnemonic('T');
		
		JMenuItem dataAniversario = new JMenuItem("Data de Aniversário");
		dataAniversario.setMnemonic('A');
		
		JMenuItem dataCasamento = new JMenuItem("Data de Casamento");
		dataCasamento.setMnemonic('D');
		
		todos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				searchAll();
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
		
		menuPesquisar.add(todos);
		
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
				reportsAll();
			}			
		});
		relAniversario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsAniversario();
			}			
		});
		relCasamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsCasamento();
			}			
		});
		
		relRecadastramento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsRecadastramento();
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
				
		JMenuItem usuarioLogado = new JMenuItem("Usuário: "+TelaLogin.usuario.getNome().toUpperCase());
		usuarioLogado.setEnabled(false);				
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sair();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				sair();
			}
		});
				
		menuBar.add(menuSair);
		
		JMenuItem aux1 = new JMenuItem("        ");	
		aux1.setEnabled(false);
		menuBar.add(aux1);		
		menuBar.add(usuarioLogado);
	
		setJMenuBar(menuBar);				
		setSize(730, 530);		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
				
	}		
	//fim do construtor     
	
	private void reportsAll() {
		try{
			new RelatorioBO().runAniversarioAll();
		}catch(Exception ex){
			logger.error("Erro ao executar o relátorio.",ex);
		}
	}
	
	private void reportsRecadastramento() {
		try{
			new RelatorioBO().runRecadastramento();
		}catch(Exception ex){
			logger.error("Erro ao executar o relátorio.",ex);
		}
	}
	
	private void reportsCasamento() {
		if (telaPesquisaCasamento == null || telaPesquisaCasamento.isClosed()) {
			telaPesquisaCasamento = new TelaPesquisa(this, TipoPesquisa.CASAMENTO);
		}
		telaPesquisaCasamento.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 1.5), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (ESPACO_ENTRE_JANELA * 3));

		desktop.moveToFront(telaPesquisaCasamento);
	}
	
	
	private void reportsAniversario() {
		if (telaPesquisaAniversario == null || telaPesquisaAniversario.isClosed()) {
			telaPesquisaAniversario = new TelaPesquisa(this, TipoPesquisa.ANIVERSARIO);
		}
		telaPesquisaAniversario.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 1.5), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (ESPACO_ENTRE_JANELA * 3));

		desktop.moveToFront(telaPesquisaAniversario);
	}
	
	private void searchAll() {
//		TelaListagemDizimista rel = new TelaListagemDizimista();
//		if(rel.isAll()){
//			rel.montaTabela();
//			rel.setVisible(true);
//		}else{
//			JOptionPane.showMessageDialog(this, "Nenhum Registro encontrado!","Atenção!",JOptionPane.WARNING_MESSAGE);
//		}
	}
	
	// chama a tela cadastrar
	private void cadastrarDizimista() {
		if (telaDizimista == null || telaDizimista.isClosed()) {
			telaDizimista = new TelaDizimista(this);
		}
		telaDizimista.restaura();
		telaDizimista.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (int)(ESPACO_ENTRE_JANELA));

		desktop.moveToFront(telaDizimista);	
		
	}
	
	// método executado quando clica no botão sair
	private void sair() {		
		int res = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?","Sair",JOptionPane.YES_NO_OPTION);
		if (res == 0){
			System.exit(0);	
		}		
	}
	
} 
class BackgroundedDesktopPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private Image img;

	public BackgroundedDesktopPane(Image image) {
		img = image;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null){
			g.drawImage(img, 100, 50, 500, 300, this);
		}else{
			g.drawString("Logo não encontrada", 50, 50);
		}
	}
}