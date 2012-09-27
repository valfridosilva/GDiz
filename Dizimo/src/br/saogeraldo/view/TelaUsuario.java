package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.dao.UsuarioDAO;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ValidacaoException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton botaoPesquisarPorNome;
	private JButton botaoSalvar;
	private JButton botaoAlterar;
	private JButton botaoExcluir;
	private JButton botaoLimpar;
	private JLabel labelNome;	
	private JLabel labelSenha;
	private JTextField campoNome;		
	private JPasswordField campoSenha;
	private TelaMenu telaMenu;
	private UsuarioVO usuario;
	private UsuarioDAO usuarioDAO;
	private static final String TIPO_OBJETO = "Usuário";
	private static Logger logger = Logger.getLogger(TelaUsuario.class);
		
	public TelaUsuario(TelaMenu telaMenu){
		super(TIPO_OBJETO, true, true, true, true);
		setVisible(true);
		setLayout(new FlowLayout());
		boolean status = true;
		
		this.telaMenu = telaMenu;
		telaMenu.addJanela(this);
		
		botaoPesquisarPorNome = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/pesquisar.gif")));
		botaoPesquisarPorNome.setToolTipText("Pesquisar por Nome");
		botaoSalvar = new JButtonEnter(Mensagem.LABEL_SALVAR);
		botaoAlterar = new JButtonEnter(Mensagem.LABEL_ALTERAR);
		botaoExcluir = new JButtonEnter(Mensagem.LABEL_EXCLUIR);
		botaoLimpar = new JButtonEnter(Mensagem.LABEL_LIMPAR);
		labelNome = new JLabel("* Nome:");
		labelNome.setToolTipText("Nome");
		labelSenha = new JLabel("* Senha:");
		labelSenha.setToolTipText("Senha");

		campoNome = new JTextField(100);
		campoSenha = new JPasswordField(15);
			
		campoNome.setEditable(status);		
		campoSenha.setEditable(status);

		habilitaBotoes(true);

		JPanel painel = getPanelForm();

		super.add(painel);

		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoSalvar);
		painelBotao.add(botaoAlterar);
		painelBotao.add(botaoExcluir);
		painelBotao.add(botaoLimpar);

		super.add(painelBotao);

		campoNome.requestFocus();

		super.pack();
	}
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, pref, 2dlu, 100px, 2dlu, pref, 2dlu, 40px, 2dlu, pref, 2dlu, 40px, 2dlu, 90px, 2dlu, 90px, 2dlu, 110px, 2dlu",
				"2dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Dados "));
		CellConstraints cellconstraints = new CellConstraints();		
		
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {					
				salvar();
			}
		});
		botaoAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				alterar();
			}
		});
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				excluir();
			}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {				
				limpar();				
			}
		});
		botaoPesquisarPorNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pesquisaPorNome();
			}
		});
				
		jpanel.add(labelNome, cellconstraints.xy(2, 4));
		jpanel.add(campoNome, cellconstraints.xyw(4, 4, 7));	
		jpanel.add(botaoPesquisarPorNome, cellconstraints.xy(12, 4));
		
		jpanel.add(labelSenha, cellconstraints.xy(2, 6));
		jpanel.add(campoSenha, cellconstraints.xyw(4, 6, 3));
	
		return jpanel;
	}
	
	public UsuarioVO getObjectFomTela() throws ValidacaoException{				
		UsuarioVO user = null;
		if (this.usuario != null) {
			user = this.usuario;
		} else {
			user = new UsuarioVO();
		}
		
		if(campoNome.getText().trim().isEmpty()){
			user.setNome(null);
		}else{
			user.setNome(campoNome.getText().trim());
		}
		
		if(campoSenha.getPassword() == null){
			user.setSenha(null);
		}else{
			user.setSenha(String.valueOf(campoSenha.getPassword()));
		}
		
		return user;
	}
	
	public void habilitaBotoes(boolean flag) {
		botaoSalvar.setVisible(flag);
		botaoAlterar.setVisible(!flag);
		botaoExcluir.setVisible(!flag);
	}
	
	public void restaura() {
		this.setVisible(true);
		try {
			this.setIcon(false);
			this.setMaximum(false);
		} catch (PropertyVetoException e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
		}
	}

	private void excluir() {
		try {
			Object[] options = { Mensagem.SIM, Mensagem.NAO };
			int res = JOptionPane.showOptionDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.CONFIRMA_EXCLUSAO, usuario.getNome()), Mensagem.CONFIRMA,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (res == JOptionPane.YES_OPTION) {
				getUsuarioDAO().delete(usuario.getIdUsuario());
				limpar();
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_EXCLUIDO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salvar() {
		try {
			UsuarioVO user = getObjectFomTela();
			valida(user);
			getUsuarioDAO().insert(user);								
			limpar();
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_INSERIDO, TIPO_OBJETO), Mensagem.SUCESSO,
					JOptionPane.INFORMATION_MESSAGE);					
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void alterar() {
		try {
			UsuarioVO user = getObjectFomTela();
			valida(user);
			getUsuarioDAO().update(user);								
			limpar();
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_ALTERADO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);					
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	private void pesquisaPorNome() {
		try {
			List<UsuarioVO> lista = getUsuarioDAO().getUsuarioByName(campoNome.getText());
			if (!lista.isEmpty()) {
				if (lista.size() == 1) {
					setObjectToTela(lista.get(0));
					habilitaBotoes(false);
				} else {
					TelaListagemUsuario tela = new TelaListagemUsuario(telaMenu, lista, this);
					tela.setBounds((int)(telaMenu.ESPACO_ENTRE_JANELA/1.5), (int) (telaMenu.ESPACO_ENTRE_JANELA / 1.5), 
							telaMenu.getDesktop().getWidth() - (int)(telaMenu.ESPACO_ENTRE_JANELA*1.5), telaMenu.getDesktop().getHeight()
							- (int)(telaMenu.ESPACO_ENTRE_JANELA * 2.3));
					telaMenu.getDesktop().moveToFront(tela);
				}
			} else {
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA,
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void valida(UsuarioVO usuario) throws ValidacaoException {
		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			campoNome.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelNome.getToolTipText()));
		}
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			campoSenha.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelSenha.getToolTipText()));
		}
	}

	public void setObjectToTela(UsuarioVO user) throws ValidacaoException{
		campoNome.setText(user.getNome());
		campoSenha.setText(user.getSenha());
		this.usuario = user;
	}
	
	public void limpar(){
		campoNome.setText("");
		campoSenha.setText("");
		campoNome.requestFocus();
		habilitaBotoes(true);
		usuario = null;
	}
	
	public UsuarioDAO getUsuarioDAO() {
		if (usuarioDAO == null) {
			usuarioDAO = new UsuarioDAO();
		}
		return usuarioDAO;
	}
}

