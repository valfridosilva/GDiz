package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.dao.UsuarioDAO;
import br.saogeraldo.util.FabricaConexao;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ValidacaoException;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botaoLogar;
	private JButton botaoSair;
	private JLabel rotuloUsuario;
	private JLabel rotuloSenha; 
	private JLabel rotulo;
	private JPasswordField campoSenha;
	private JTextField campoUsuario; 
	public static UsuarioVO usuario; 
	private static Logger logger = Logger.getLogger(TelaLogin.class);
	
	public TelaLogin() {
		super("Sistema Dizimista");
		inicializa();
		
		rotulo = new JLabel("Entre com os dados abaixo para acessar o sistema", JLabel.CENTER);
		rotuloUsuario = new JLabel("Usuário:", JLabel.CENTER);
		rotuloUsuario.setToolTipText("Usuário");
		rotuloSenha = new JLabel("Senha:", JLabel.CENTER);
		rotuloSenha.setToolTipText("Senha");
		campoSenha = new JPasswordField(20);
		campoUsuario = new JTextField(20);
		botaoLogar = new JButtonEnter("Entrar");
		botaoSair = new JButtonEnter("Sair");

		setResizable(false);
		setSize(380, 200);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		
		getRootPane().setDefaultButton(botaoLogar);
		
		JPanel painel = getPanelForm();

		super.add(painel);

		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoLogar);
		painelBotao.add(botaoSair);

		super.add(painelBotao);
		
	}

	private void inicializa() {
		

	}
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
			    "right:pref, 8dlu, 250px",
			    "pref, 5dlu, pref, 5dlu, pref, 10dlu, pref"
			);
		JPanel jpanel = new JPanel(formlayout);		
		CellConstraints cellconstraints = new CellConstraints();		

		botaoLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
		        logar();
			}
		});
		botaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sair();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				sair();
			}
		});
				
		jpanel.add(rotulo, cellconstraints.xywh(1, 1, 3, 1));

		jpanel.add(rotuloUsuario, cellconstraints.xy(1, 3));
		jpanel.add(campoUsuario, cellconstraints.xy(3, 3));

		jpanel.add(rotuloSenha, cellconstraints.xy(1, 5));
		jpanel.add(campoSenha, cellconstraints.xy(3, 5));

		jpanel.add(botaoLogar, cellconstraints.xy(1, 7));
		jpanel.add(botaoSair, cellconstraints.xy(3, 7));
		
	
		return jpanel;
	}

	private void sair() {
		FabricaConexao.close();
		System.exit(0);
	}

	private void logar() {
		String senhaStr = String.valueOf(campoSenha.getPassword());
		String usuarioStr = campoUsuario.getText().toLowerCase();
		try {
			valida(usuarioStr, senhaStr);
			usuario = new UsuarioDAO().getLogin(usuarioStr, senhaStr);
			if (usuario != null) {
				dispose();
				new TelaMenu();
			} else {
				JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!!", Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				campoSenha.setText("");
				campoUsuario.requestFocus();
			}
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(null, Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(null, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void valida(String usuario, String senha) throws ValidacaoException{
		if (usuario.trim().isEmpty()) {			
			campoUsuario.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,rotuloUsuario.getToolTipText()));
		} 
		if(senha.trim().isEmpty()){
			campoSenha.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,rotuloSenha.getToolTipText()));
		}
	}
}