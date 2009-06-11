package br.saogeraldo.view;

import javax.swing.*;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.dao.UsuarioDAO;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botaoLogar;
	private JButton botaoSair;
	private JLabel rotuloUsuario;
	private JLabel rotuloSenha; 
	private JLabel rotulo;
	private JPasswordField campoSenha;
	private JTextField campoUsuario; 
	public static UsuarioVO usuario; 
	
	public Login() {
		super("Sistema Dizimista");
		inicializa();
	}

	private void inicializa() {
		rotulo = new JLabel("Entre com os dados abaixo para acessar o sistema", JLabel.CENTER);
		rotuloUsuario = new JLabel("Usuário:", JLabel.CENTER);
		rotuloSenha = new JLabel("    Senha:", JLabel.CENTER);
		campoSenha = new JPasswordField(20);
		botaoLogar = new JButton();
		botaoSair = new JButton();
		campoUsuario = new JTextField(20);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(340, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new FlowLayout());
		

		botaoLogar.setText("Entrar");
		botaoSair.setText("Sair");
		
		add(rotulo);
		add(rotuloUsuario);
		add(campoUsuario);
		add(rotuloSenha);
		add(campoSenha);
		add(botaoLogar);
		add(botaoSair);

		botaoLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				logar(evt);
			}
		});
		botaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sair(evt);
			}
		});
	}

	private void sair(ActionEvent evt) {
		System.exit(0);
	}

	private void logar(ActionEvent evt) {
		String senha = String.valueOf(campoSenha.getPassword());
		if (campoUsuario.getText().equals("")|| senha.equals("")) {			
			JOptionPane.showMessageDialog(this, "Campo em branco!!","Atenção!", JOptionPane.WARNING_MESSAGE);	
			validate();
			campoUsuario.setText("");
			campoSenha.setText("");			
			campoUsuario.requestFocus();
		} else {
			UsuarioDAO dao = new UsuarioDAO();
			UsuarioVO user = new UsuarioVO();
			user.setNome(campoUsuario.getText().toLowerCase());
			user.setSenha(senha);
			try {
				usuario = dao.getLogin(user);
				if (usuario != null) {
					dispose();
					new Menu();
					
				} else {
					JOptionPane.showMessageDialog(this,"Usuário ou senha incorretos!!", "Atenção!",JOptionPane.ERROR_MESSAGE);
					campoUsuario.setText("");
					campoSenha.setText("");
					validate();
					campoUsuario.requestFocus();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados","Erro!",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

	}

}