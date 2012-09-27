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
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.TipoPesquisa;
import br.saogeraldo.util.ValidacaoException;

public class TelaMenu extends JFrame{		

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private Image logo;
	private TelaPesquisa telaPesquisaAniversario;
	private TelaPesquisa telaPesquisaCasamento;
	private TelaDizimista telaDizimista;
	private TelaUsuario telaUsuario;
	private ArquivoBO arquivoBO;
	private DizimistaDAO dizimistaDAO;
	public final Integer ESPACO_ENTRE_JANELA = 120;
	private static Logger logger = Logger.getLogger(TelaMenu.class);
	 
	//método construtor  
	public TelaMenu() {
		super("Controle de Dizimista");	
		
		logo = new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/logo.png")).getImage();
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
		
		JMenuItem todos = new JMenuItem("Pesquisar (Todos)");
		todos.setMnemonic('P');
		
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
		
		JMenuItem relAniversarioTodos = new JMenuItem("Aniversário (Todos)");
		relAniversarioTodos.setMnemonic('S');
		
		JMenuItem relAniversario = new JMenuItem("Aniversário");
		relAniversario.setMnemonic('A');
		
		JMenuItem relCasamento = new JMenuItem("Casamento");
		relCasamento.setMnemonic('C');
		
		JMenuItem relRecadastramento = new JMenuItem("Recadastramento");
		relRecadastramento.setMnemonic('R');
		
		relAniversarioTodos.addActionListener(new ActionListener() {
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
		menuRelatorio.add(relAniversarioTodos);
		menuRelatorio.addSeparator();
		menuRelatorio.add(relCasamento);
		menuRelatorio.add(relRecadastramento);
		
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
		telaPesquisaCasamento.restaura();
		telaPesquisaCasamento.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 1.5), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (int)(ESPACO_ENTRE_JANELA * 2.5));

		desktop.moveToFront(telaPesquisaCasamento);
	}
	
	
	private void reportsAniversario() {
		if (telaPesquisaAniversario == null || telaPesquisaAniversario.isClosed()) {
			telaPesquisaAniversario = new TelaPesquisa(this, TipoPesquisa.ANIVERSARIO);
		}
		telaPesquisaAniversario.restaura();
		telaPesquisaAniversario.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 1.5), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (int)(ESPACO_ENTRE_JANELA * 2.5));

		desktop.moveToFront(telaPesquisaAniversario);
	}
	
	private void searchAll() {
		try{
			List<DizimistaVO> lista = getDizimistaDAO().getDizimistaAll();
			if (!lista.isEmpty()) {
				TelaListagemDizimista tela = new TelaListagemDizimista(this, lista);
				tela.setBounds(this.ESPACO_ENTRE_JANELA / 2, (int) (this.ESPACO_ENTRE_JANELA / 1.5), this.getDesktop().getWidth() - this.ESPACO_ENTRE_JANELA, this.getDesktop()
						.getHeight()
						- (int)(this.ESPACO_ENTRE_JANELA * 1.5));
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
		telaDizimista.restaura();
		telaDizimista.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (int)(ESPACO_ENTRE_JANELA));

		desktop.moveToFront(telaDizimista);	
	}
	
	private void cadastrarUsuario() {
		if (telaUsuario == null || telaUsuario.isClosed()) {
			telaUsuario = new TelaUsuario(this);
		}
		telaUsuario.restaura();
		telaUsuario.setBounds(ESPACO_ENTRE_JANELA / 2, (int) (ESPACO_ENTRE_JANELA / 2), this.desktop.getWidth() - ESPACO_ENTRE_JANELA, this.desktop
				.getHeight()
				- (ESPACO_ENTRE_JANELA)*2);

		desktop.moveToFront(telaUsuario);	
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
				getArquivoBO().exportarArquivo(arquivoSelecionado.getAbsolutePath());
				JOptionPane.showMessageDialog(this, Mensagem.ARQUIVO_EXPORTADO, Mensagem.SUCESSO, JOptionPane.INFORMATION_MESSAGE);
			}
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
		if (fileChooser.showOpenDialog(fileChooser) == javax.swing.JFileChooser.APPROVE_OPTION) {
			try {
				File arquivoSelecionado = fileChooser.getSelectedFile();
				Object[] options = { Mensagem.SIM, Mensagem.NAO };
				int res = JOptionPane.showOptionDialog(this, Mensagem.CONFIRMA_RESTAURAR_BACKUP, Mensagem.CONFIRMA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (res == JOptionPane.YES_OPTION) {
					getArquivoBO().importarArquivo(arquivoSelecionado);
					JOptionPane.showMessageDialog(this, Mensagem.ARQUIVO_IMPORTADO, Mensagem.SUCESSO, JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(this, Mensagem.REINICIAR_APLICACAO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
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