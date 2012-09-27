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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

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
	}

	private void inicializa() {
		rotulo = new JLabel("Entre com os dados abaixo para acessar o sistema", JLabel.CENTER);
		rotuloUsuario = new JLabel("Usuário:", JLabel.CENTER);
		rotuloUsuario.setToolTipText("Usuário");
		rotuloSenha = new JLabel("    Senha:", JLabel.CENTER);
		rotuloSenha.setToolTipText("Senha");
		campoSenha = new JPasswordField(20);
		campoSenha.setText("123");
		campoSenha.setToolTipText("Senha");
		campoUsuario = new JTextField(20);
		campoUsuario.setText("valfrido");
		campoUsuario.setToolTipText("Usuário");
		botaoLogar = new JButtonEnter("Entrar");
		botaoSair = new JButtonEnter("Sair");

		setResizable(false);
		setSize(340, 150);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		
		add(rotulo);
		add(rotuloUsuario);
		add(campoUsuario);
		add(rotuloSenha);
		add(campoSenha);
		add(botaoLogar);
		add(botaoSair);

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