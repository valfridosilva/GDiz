package br.saogeraldo.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.negocio.ArquivoBO;
import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.ItemValue;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;
import br.saogeraldo.util.TipoPesquisa;
import br.saogeraldo.util.ValidacaoException;

public class TelaMenu extends JFrame{		

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private Image logo;
	private TelaPesquisa telaPesquisaAniversario;
	private TelaPesquisa telaPesquisaCasamento;
	private TelaPesquisaPagamento telaPesquisaPagamento;
	private TelaDizimista telaDizimista;
	private TelaFinanceiro telaFinanceiro;
	private TelaUsuario telaUsuario;
	private ArquivoBO arquivoBO;
	private DizimistaDAO dizimistaDAO;
	public final Integer ESPACO_ENTRE_JANELA = 85;
	private static Logger logger = Logger.getLogger(TelaMenu.class);
	 
	//método construtor  
	public TelaMenu() {
		super("Controle de Dizimista");	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		logo = new ImageIcon(getClass().getClassLoader().getResource("imagens/logo.png")).getImage();
		desktop = new BackgroundedDesktopPane(logo);	
		add(desktop);
		
		JMenuBar menuBar = new JMenuBar();	
		
		// Sistema
		JMenu sistema = new JMenu("Sistema");
		sistema.setMnemonic('T');

		JMenuItem gerarBackup = new JMenuItem("Gerar Backup");
		gerarBackup.setMnemonic('G');
		gerarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				gerarBackup();
			}
		});
		sistema.add(gerarBackup);

		JMenuItem restaurarBackup = new JMenuItem("Restaurar Backup");
		restaurarBackup.setMnemonic('R');
		restaurarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				restaurarBackup();
			}
		});
		sistema.add(restaurarBackup);
		sistema.addSeparator();

		JMenuItem sobre = new JMenuItem("Sobre");
		sobre.setMnemonic('B');
		sobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sobre();
			}
		});
		sistema.add(sobre);
		sistema.addSeparator();

		JMenuItem sair = new JMenuItem("Sair");
		sair.setMnemonic('S');
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sair();
			}
		});
		sistema.add(sair);
		menuBar.add(sistema);
		
		// Menu Dizimista //  
		JMenu menuCadastro = new JMenu("Dizimista");
		menuCadastro.setMnemonic('D');
		
		JMenuItem manterCliente = new JMenuItem("Cadastro");
		manterCliente.setMnemonic('C');
		
		manterCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cadastrarDizimista();
			}
		});
		
		menuCadastro.add(manterCliente);
		
		JMenuItem financeiro = new JMenuItem("Financeiro");
		financeiro.setMnemonic('F');
		
		financeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				registrarPagamento();
			}			
		});
		menuCadastro.add(financeiro);
		menuCadastro.addSeparator();
		
		JMenuItem todos = new JMenuItem("Listar todos");
		todos.setMnemonic('L');
		
		todos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				searchAll();
			}			
		});
		menuCadastro.add(todos);
		
		menuBar.add(menuCadastro);
		
		// Menu Usuário
		JMenu menuUsuario = new JMenu("Usuário");
		menuUsuario.setMnemonic('U');
		
		JMenuItem manterUsuario = new JMenuItem("Cadastro");
		manterUsuario.setMnemonic('C');
		
		manterUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cadastrarUsuario();
			}
		});
		
		menuUsuario.add(manterUsuario);
		menuBar.add(menuUsuario);
				
		// Menu Relatórios //
		JMenu menuRelatorio = new JMenu("Relatórios");
		menuRelatorio.setMnemonic('R');
				
		JMenuItem relAniversario = new JMenuItem("Aniversário");
		relAniversario.setMnemonic('A');
		
		JMenuItem relFinanceiro = new JMenuItem("Financeiro");
		relFinanceiro.setMnemonic('F');
		
		JMenuItem relCasamento = new JMenuItem("Casamento");
		relCasamento.setMnemonic('C');
		
		JMenuItem relRecadastramento = new JMenuItem("Recadastramento");
		relRecadastramento.setMnemonic('R');
		
		JMenuItem relFalecido = new JMenuItem("Falecido");
		relFalecido.setMnemonic('F');
		
		relFinanceiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsFinanceiro();
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
		
		relFalecido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reportsFalecido();
			}			
		});
		
		menuRelatorio.add(relAniversario);
		menuRelatorio.add(relFinanceiro);
		menuRelatorio.addSeparator();
		menuRelatorio.add(relCasamento);
		menuRelatorio.add(relRecadastramento);
		menuRelatorio.addSeparator();
		menuRelatorio.add(relFalecido);
		
		menuBar.add(menuRelatorio);
				
		JMenuItem usuarioLogado = new JMenuItem("Usuário: "+TelaLogin.usuario.getNome().toUpperCase());
		usuarioLogado.setEnabled(false);				
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				sair();
			}
		});
				
		JMenuItem aux1 = new JMenuItem("        ");	
		aux1.setEnabled(false);
		menuBar.add(aux1);		
		menuBar.add(usuarioLogado);
	
		setJMenuBar(menuBar);				
		setSize(750, 550);		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
				
	}		
	//fim do construtor     
	
	private void sobre() {
		JOptionPane.showMessageDialog(this, "Versão: 1.1 (2015)\n Desenvolvido por: Valfrido","Sobre", JOptionPane.INFORMATION_MESSAGE, null);
		
	}

	private void reportsFinanceiro() {
		if (telaPesquisaPagamento == null || telaPesquisaPagamento.isClosed()) {
			telaPesquisaPagamento = new TelaPesquisaPagamento(this);
		}
		telaPesquisaPagamento.restaura();
		// Bounds(Left, Top, Right e Bottom)
		telaPesquisaPagamento.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA * 3.3));

		desktop.moveToFront(telaPesquisaPagamento);
	}
	
	private void reportsRecadastramento() {
		try{
			new RelatorioBO().runRecadastramento();
		} catch (RegraDeNegocioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (RelatorioException e) {
			logger.error(Mensagem.ERRO_GERACAO_RELATORIO, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_GERACAO_RELATORIO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}catch(Exception ex){
			logger.error("Erro ao executar o relátorio.",ex);
			JOptionPane.showMessageDialog(this, ex.getMessage(), Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void reportsFalecido() {
		try{
			new RelatorioBO().runFalecido();
		} catch (RegraDeNegocioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (RelatorioException e) {
			logger.error(Mensagem.ERRO_GERACAO_RELATORIO, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_GERACAO_RELATORIO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}catch(Exception ex){
			logger.error("Erro ao executar o relátorio.",ex);
			JOptionPane.showMessageDialog(this, ex.getMessage(), Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void reportsCasamento() {
		if (telaPesquisaCasamento == null || telaPesquisaCasamento.isClosed()) {
			telaPesquisaCasamento = new TelaPesquisa(this, TipoPesquisa.CASAMENTO);
		}
		telaPesquisaCasamento.restaura();
		// Bounds(Left, Top, Right e Bottom)
		telaPesquisaCasamento.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA * 3.3));

		desktop.moveToFront(telaPesquisaCasamento);
	}
	
	
	private void reportsAniversario() {
		if (telaPesquisaAniversario == null || telaPesquisaAniversario.isClosed()) {
			telaPesquisaAniversario = new TelaPesquisa(this, TipoPesquisa.ANIVERSARIO);
		}
		telaPesquisaAniversario.restaura();
		// Bounds(Left, Top, Right e Bottom)
		telaPesquisaAniversario.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA * 3.3));

		desktop.moveToFront(telaPesquisaAniversario);
	}
	
	private void searchAll() {
		try{
			List<DizimistaVO> lista = getDizimistaDAO().getDizimistaAll();
			if (!lista.isEmpty()) {
				TelaListagemDizimista tela = new TelaListagemDizimista(this, lista);
				// Bounds(Left, Top, Right e Bottom)
				tela.setBounds(this.ESPACO_ENTRE_JANELA / 2, (int) (this.ESPACO_ENTRE_JANELA / 2), this.getDesktop().getWidth() - this.ESPACO_ENTRE_JANELA, this.getDesktop()
						.getHeight()
						- (int)(this.ESPACO_ENTRE_JANELA * 2));
				this.getDesktop().moveToFront(tela);
			}else{
				JOptionPane.showMessageDialog(this, Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// chama a tela cadastrar
	private void cadastrarDizimista() {
		if (telaDizimista == null || telaDizimista.isClosed()) {
			telaDizimista = new TelaDizimista(this);
		}
		telaDizimista.restaura(false);
		// Bounds(Left, Top, Right e Bottom)
		telaDizimista.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA));

		desktop.moveToFront(telaDizimista);	
	}
	
	// chama a tela registrar
	private void registrarPagamento() {
		if (telaFinanceiro == null || telaFinanceiro.isClosed()) {
			telaFinanceiro = new TelaFinanceiro(this);
		}
		telaFinanceiro.restaura(false);
		// Bounds(Left, Top, Right e Bottom)
		telaFinanceiro.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA));

		desktop.moveToFront(telaFinanceiro);	
	}
	
	
	private void cadastrarUsuario() {
		if (telaUsuario == null || telaUsuario.isClosed()) {
			telaUsuario = new TelaUsuario(this);
		}
		telaUsuario.restaura();
		// Bounds(Left, Top, Right e Bottom)
		telaUsuario.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight() - (int)(ESPACO_ENTRE_JANELA * 3));

		desktop.moveToFront(telaUsuario);	
	}
	
	JComboBox<ItemValue> getComboMes(){
		JComboBox<ItemValue> comboMes = new JComboBox<ItemValue>();
		comboMes.addItem(new ItemValue("01", "Janeiro"));
		comboMes.addItem(new ItemValue("02", "Fevereiro"));
		comboMes.addItem(new ItemValue("03", "Março"));
		comboMes.addItem(new ItemValue("04", "Abril"));
		comboMes.addItem(new ItemValue("05", "Maio"));
		comboMes.addItem(new ItemValue("06", "Junho"));
		comboMes.addItem(new ItemValue("07", "Julho"));
		comboMes.addItem(new ItemValue("08", "Agosto"));
		comboMes.addItem(new ItemValue("09", "Setembro"));
		comboMes.addItem(new ItemValue("10", "Outubro"));
		comboMes.addItem(new ItemValue("11", "Novembro"));
		comboMes.addItem(new ItemValue("12", "Dezembro"));
		return comboMes;
	}
	
	private void gerarBackup() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new MyFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setSelectedFile(new File("backup-dizimo"+ArquivoBO.EXTENSION_FILE));
		try {
			fileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
			if (fileChooser.showSaveDialog(fileChooser) == javax.swing.JFileChooser.APPROVE_OPTION) {
				File arquivoSelecionado = fileChooser.getSelectedFile();
				File arquivoBackup = new File(arquivoSelecionado.getAbsolutePath());
				if(arquivoBackup.exists()){
					Object[] options = { Mensagem.SIM, Mensagem.NAO };
					int res = JOptionPane.showOptionDialog(this.getDesktop().getSelectedFrame(), Mensagem.ARQUIVO_JA_EXISTENTE, Mensagem.CONFIRMA,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if (res != JOptionPane.YES_OPTION) {
							return;
						}
				}else{
					arquivoBackup.createNewFile();
				}
				getArquivoBO().exportarArquivo(arquivoBackup);
				JOptionPane.showMessageDialog(this, Mensagem.ARQUIVO_EXPORTADO, Mensagem.SUCESSO, JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			logger.error(Mensagem.ERRO_IMPORTACAO_ARQUIVO, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_IMPORTACAO_ARQUIVO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private void restaurarBackup() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new MyFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setSelectedFile(new File("backup-dizimo"+ArquivoBO.EXTENSION_FILE));
		try {
			fileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
			if (fileChooser.showOpenDialog(fileChooser) == javax.swing.JFileChooser.APPROVE_OPTION) {
				
					File arquivoSelecionado = fileChooser.getSelectedFile();
					Object[] options = { Mensagem.SIM, Mensagem.NAO };
					int res = JOptionPane.showOptionDialog(this, Mensagem.CONFIRMA_RESTAURAR_BACKUP, Mensagem.CONFIRMA,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (res == JOptionPane.YES_OPTION) {
						getArquivoBO().importarArquivo(arquivoSelecionado);
						JOptionPane.showMessageDialog(this, Mensagem.ARQUIVO_IMPORTADO, Mensagem.SUCESSO, JOptionPane.INFORMATION_MESSAGE);
					}
			}
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			logger.error(Mensagem.ERRO_IMPORTACAO_ARQUIVO, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_IMPORTACAO_ARQUIVO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// método executado quando clica no botão sair
	private void sair() {		
		int res = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?","Sair",JOptionPane.YES_NO_OPTION);
		if (res == 0){
			System.exit(0);	
		}		
	}
	
	public void addJanela(JInternalFrame janela) {
		desktop.add(janela);
	}
	
	public JDesktopPane getDesktop() {
		return desktop;
	}
	
	private ArquivoBO getArquivoBO(){
		if(arquivoBO == null){
			arquivoBO = new ArquivoBO();
		}
		return arquivoBO;
	}
	private DizimistaDAO getDizimistaDAO() {
		if (dizimistaDAO == null) {
			dizimistaDAO = new DizimistaDAO();
		}
		return dizimistaDAO;
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

class MyFilter extends javax.swing.filechooser.FileFilter {
	
	public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(ArquivoBO.EXTENSION_FILE);
	}

	public String getDescription() {
		return "*" + ArquivoBO.EXTENSION_FILE;
	}
}