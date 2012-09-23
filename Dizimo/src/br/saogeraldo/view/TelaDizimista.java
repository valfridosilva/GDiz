package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.util.DataUtil;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ValidacaoException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelaDizimista extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton botaoPesquisarPorNome;
	private JButton botaoPesquisarPorCodigo;
	private JButton botaoSalvar;
	private JButton botaoAlterar;
	private JButton botaoExcluir;
	private JButton botaoLimpar;
	private JLabel labelCodigo;	
	private JLabel labelNome;	
	private JLabel labelDataNascimento;
	private JLabel labelDataNascimentoConjugue;
	private JLabel labelConjugue;
	private JLabel labelEndereco;
	private JLabel labelTelefone;
	private JLabel labelDataCasamento;
	private JTextField campoCodigo;
	private JTextField campoNome;		
	private JTextField campoConjugue;
	private JTextField campoEndereco;
	private JFormattedTextField campoTelefone;
	private JFormattedTextField campoDataNascimento;
	private JFormattedTextField campoDataNascimentoConjugue;
	private JFormattedTextField campoDataCasamento;
	private MaskFormatter formatDtNascimento;
	private MaskFormatter formatDtCasamento;	
	private MaskFormatter formatTelefone;	
	private MaskFormatter formatDtNascimentoConjugue;
	private DizimistaVO dizimista;
	private DizimistaDAO dizimistaDAO;
	private TelaMenu telaMenu;
	private static final String TIPO_OBJETO = "Dizimista";
	private static Logger logger = Logger.getLogger(TelaDizimista.class);
		
	public TelaDizimista(TelaMenu telaMenu){
		super(TIPO_OBJETO, true, true, true, true);
		setVisible(true);
		setLayout(new FlowLayout());
		boolean status = true;
		
		this.telaMenu = telaMenu;
		telaMenu.desktop.add(this);
		
		try {
			formatDtNascimento = new MaskFormatter("##/##/####");	
			formatDtCasamento = new MaskFormatter("##/##/####");	
			formatTelefone = new MaskFormatter("(##)####-####");
			formatDtNascimentoConjugue = new MaskFormatter("##/##/####");
		} catch (ParseException ex) {
			logger.error("Erro ao criar parser", ex);
		}

		botaoPesquisarPorNome = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/pesquisar.gif")));
		botaoPesquisarPorNome.setToolTipText("Pesquisar por Nome");
		botaoPesquisarPorCodigo = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("br/saogeraldo/util/pesquisar.gif")));
		botaoPesquisarPorCodigo.setToolTipText("Pesquisar por Código");
		botaoSalvar = new JButtonEnter(Mensagem.LABEL_SALVAR);
		botaoAlterar = new JButtonEnter(Mensagem.LABEL_ALTERAR);
		botaoExcluir = new JButtonEnter(Mensagem.LABEL_EXCLUIR);
		botaoLimpar = new JButtonEnter(Mensagem.LABEL_LIMPAR);
		labelNome = new JLabel("* Nome:");
		labelNome.setToolTipText("Nome");
		labelCodigo = new JLabel("* Código:");
		labelCodigo.setToolTipText("Código");
		labelDataNascimento = new JLabel("* Data Nascimento:");
		labelDataNascimento.setToolTipText("Data Nascimento");
		labelDataNascimentoConjugue = new JLabel("D.N. Conjugue:");
		labelDataNascimentoConjugue.setToolTipText("Data Nascimento Cônjugue");
		labelConjugue = new JLabel(" Conjugue:");
		labelConjugue.setToolTipText("Nome do Cônjugue");
		labelEndereco = new JLabel(" Endereço:");
		labelEndereco.setToolTipText("Endereço");
		labelTelefone = new JLabel(" Telefone:");
		labelTelefone.setToolTipText("Telefone");
		labelDataCasamento = new JLabel(" Data Casamento:");
		labelDataCasamento.setToolTipText("Data de Casamento");

		campoNome = new JTextField(100);
		campoCodigo = new JTextField(10);
		campoConjugue = new JTextField(100);		
		campoEndereco = new JTextField(100);		
		campoDataNascimento = new JFormattedTextField(formatDtNascimento);
		campoDataNascimento.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoDataCasamento = new JFormattedTextField(formatDtCasamento);
		campoDataCasamento.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoDataNascimentoConjugue = new JFormattedTextField(formatDtNascimentoConjugue);
		campoDataNascimentoConjugue.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoTelefone = new JFormattedTextField(formatTelefone);	
		campoTelefone.setFocusLostBehavior(JFormattedTextField.COMMIT);
			
		campoNome.setEditable(status);		
		campoCodigo.setEditable(status);
		campoConjugue.setEditable(status);		
		campoEndereco.setEditable(status);
		campoTelefone.setEditable(status);
		campoDataNascimento.setEditable(status);
		campoDataNascimentoConjugue.setEditable(status);
		campoDataCasamento.setEditable(status);

		habilitaBotoes(true);

		JPanel painel = getpanelform();

		super.add(painel);

		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoSalvar);
		painelBotao.add(botaoAlterar);
		painelBotao.add(botaoExcluir);
		painelBotao.add(botaoLimpar);

		super.add(painelBotao);

		campoCodigo.requestFocus();

		super.pack();
	}
	
	public JPanel getpanelform() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, pref, 2dlu, 100px, 2dlu, pref, 2dlu, 40px, 2dlu, pref, 2dlu, 50px, 2dlu, 70px, 2dlu, 40px, 2dlu, 90px, 2dlu",
				"2dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Dados "));
		CellConstraints cellconstraints = new CellConstraints();		
		campoCodigo.addKeyListener( new SomenteNum());		
		
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
		botaoPesquisarPorCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pesquisaPorCodigo();
			}
		});
				
		jpanel.add(labelCodigo, cellconstraints.xy(2, 2));
		jpanel.add(campoCodigo, cellconstraints.xyw(4, 2, 3));
		jpanel.add(botaoPesquisarPorCodigo, cellconstraints.xy(8, 2));
		
		jpanel.add(labelNome, cellconstraints.xy(2, 4));
		jpanel.add(campoNome, cellconstraints.xyw(4, 4, 11));	
		jpanel.add(botaoPesquisarPorNome, cellconstraints.xy(16, 4));
		
		jpanel.add(labelDataNascimento, cellconstraints.xy(2, 6));
		jpanel.add(campoDataNascimento, cellconstraints.xyw(4, 6, 3));
		
		jpanel.add(labelTelefone, cellconstraints.xy(2, 8));
		jpanel.add(campoTelefone, cellconstraints.xyw(4, 8, 3));	
		
		jpanel.add(labelDataCasamento, cellconstraints.xy(2, 10));
		jpanel.add(campoDataCasamento, cellconstraints.xyw(4, 10,3));
		
		jpanel.add(labelConjugue, cellconstraints.xy(2, 12));
		jpanel.add(campoConjugue, cellconstraints.xyw(4, 12, 9));
		
		jpanel.add(labelDataNascimentoConjugue, cellconstraints.xy(2, 14));
		jpanel.add(campoDataNascimentoConjugue, cellconstraints.xyw(4, 14, 3));		
		
		jpanel.add(labelEndereco, cellconstraints.xy(2, 16));
		jpanel.add(campoEndereco, cellconstraints.xyw(4, 16, 9));		
	
		return jpanel;
	}
	
	public DizimistaVO getDizimistaFomTela() throws ValidacaoException{				
		DizimistaVO dz = null;
		if (this.dizimista != null) {
			dz = this.dizimista;
		} else {
			dz = new DizimistaVO();
		}
		if(campoCodigo.getText().trim().isEmpty()){
			dz.setIdDizimista(0);
		}else{
			dz.setIdDizimista(Integer.parseInt(campoCodigo.getText()));
		}
		
		if(campoNome.getText().trim().isEmpty()){
			dz.setNome(null);
		}else{
			dz.setNome(campoNome.getText());
		}
		
		dz.setDtNascimento(getDate(campoDataNascimento, labelDataNascimento));
		
		if(campoConjugue.getText().trim().isEmpty()){
			dz.setNomeConjugue(null);
		}else{
			dz.setNomeConjugue(campoConjugue.getText());
		}
		
		dz.setDtNascimentoConjugue(getDate(campoDataNascimentoConjugue, labelDataNascimentoConjugue));
		dz.setDtCasamento(getDate(campoDataCasamento, labelDataCasamento));
		
		if(campoTelefone.getText().equals(campoTelefone.getValue())){
			dz.setTelefone(campoTelefone.getText());
		} else {
			if (campoTelefone.getText().trim().equals("(  )    -")) {
				dz.setTelefone(null);
			} else {
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoTelefone.getText(), labelTelefone.getToolTipText()));
			}
		}
		if(campoEndereco.getText().trim().isEmpty()){
			dz.setEndereco(null);	
		}else{
			dz.setEndereco(campoEndereco.getText());	
		}
	
		return dz;
	}
	
	private Date getDate(JFormattedTextField campoData, JLabel labelData) throws ValidacaoException{
		if (campoData.getText().equals(campoData.getValue())) {
			try {
				return DataUtil.convertStringToDate(campoData.getText(), DataUtil.PATTERN_DDMMYYYY);
			} catch (ParseException e) {
				campoData.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoData.getText(),labelData.getToolTipText()));
			}
		} else {
			if (campoData.getText().trim().equals("/  /")) {
				return null;
			} else {
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoData.getText(), labelData.getToolTipText()));
			}
		}
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
			int res = JOptionPane.showOptionDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.CONFIRMA_EXCLUSAO, dizimista.getNome()), Mensagem.CONFIRMA,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (res == JOptionPane.YES_OPTION) {
				getDizimistaDAO().delete(dizimista.getIdDizimista());
				limpar();
				JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.REGISTRO_EXCLUIDO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salvar() {
		try {
			DizimistaVO dizimista = getDizimistaFomTela();
			validaDizimista(dizimista);
			if(!getDizimistaDAO().existeCodigo(dizimista.getIdDizimista())){
				getDizimistaDAO().insert(dizimista);								
				limpar();
				JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.REGISTRO_INSERIDO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);					
			}else{
				JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.REGISTRO_DUPLICADO,TIPO_OBJETO),Mensagem.ALERTA,JOptionPane.WARNING_MESSAGE);
				campoCodigo.requestFocus();
				campoCodigo.setText("");
			}	
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void alterar() {
		try {
			DizimistaVO dizimista = getDizimistaFomTela();
			validaDizimista(dizimista);
			getDizimistaDAO().update(dizimista);								
			limpar();
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.REGISTRO_ALTERADO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);					
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	private void pesquisaPorNome() {
		try {
			if (!campoNome.getText().isEmpty()) {
				List<DizimistaVO> lista = getDizimistaDAO().getDizimistaByName(campoNome.getText());
				if (!lista.isEmpty()) {
					if (lista.size() == 1) {
						setDizimistaToTela(lista.get(0));
						habilitaBotoes(false);
					} else {
						TelaListagemDizimista tela = new TelaListagemDizimista(telaMenu, lista, this);
						tela.setBounds(telaMenu.ESPACO_ENTRE_JANELA / 2, (int) (telaMenu.ESPACO_ENTRE_JANELA / 1.5), telaMenu.desktop.getWidth() - telaMenu.ESPACO_ENTRE_JANELA, telaMenu.desktop
								.getHeight()
								- (int)(telaMenu.ESPACO_ENTRE_JANELA * 1.5));
						telaMenu.desktop.moveToFront(tela);
					}
				} else {
					JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				campoNome.requestFocus();
				JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.CAMPO_PESQUISA,labelNome.getToolTipText()), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void pesquisaPorCodigo() {
		try {
			if (!campoCodigo.getText().trim().isEmpty()) {
				DizimistaVO dizimista = getDizimistaDAO().getDizimistaByCodigo(Integer.parseInt(campoCodigo.getText()));
				if(dizimista != null){
					setDizimistaToTela(dizimista);
					habilitaBotoes(false);
				} else {
					JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				campoCodigo.requestFocus();
				JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), String.format(Mensagem.CAMPO_PESQUISA,labelCodigo.getToolTipText()), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.desktop.getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void validaDizimista(DizimistaVO dizimista) throws ValidacaoException {
		if (dizimista.getIdDizimista()==0) {
			campoCodigo.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelCodigo.getToolTipText()));
		}
		if (dizimista.getNome() == null || dizimista.getNome().isEmpty()) {
			campoNome.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelNome.getToolTipText()));
		}
		if (dizimista.getDtNascimento() == null) {
			campoDataNascimento.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelDataNascimento.getToolTipText()));
		}
		if(dizimista.getDtNascimentoConjugue() != null && dizimista.getNomeConjugue() == null){
			campoConjugue.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelConjugue.getToolTipText()));
		}
		if(dizimista.getDtCasamento() != null && dizimista.getNomeConjugue() == null){
			campoConjugue.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelConjugue.getToolTipText()));
		}
	}

	public void setDizimistaToTela(DizimistaVO dz) throws ValidacaoException{
		campoNome.setText(dz.getNome());
		campoCodigo.setText(String.valueOf(dz.getIdDizimista()));
		campoConjugue.setText(dz.getNomeConjugue());		
		try {
			campoDataNascimento.setText(DataUtil.convertDateToString(dz.getDtNascimento(), DataUtil.PATTERN_DDMMYYYY));
		} catch (ParseException e) {
			throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoDataNascimento.getText(), labelDataNascimento.getToolTipText()));
		}
		try {
			campoDataCasamento.setText(DataUtil.convertDateToString(dz.getDtCasamento(), DataUtil.PATTERN_DDMMYYYY));
		} catch (ParseException e) {
			throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoDataCasamento.getText(), labelDataCasamento.getToolTipText()));
		}
		try {
			campoDataNascimentoConjugue.setText(DataUtil.convertDateToString(dz.getDtNascimentoConjugue(), DataUtil.PATTERN_DDMMYYYY));
		} catch (ParseException e) {
			throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoDataNascimentoConjugue.getText(), labelDataNascimentoConjugue.getToolTipText()));
		}
		campoEndereco.setText(dz.getEndereco());		
		campoTelefone.setText(dz.getTelefone());
		dizimista = dz;
	}
	
	public void limpar(){
		campoNome.setText("");
		campoCodigo.setText("");
		campoDataNascimento.setText("");	
		campoDataNascimentoConjugue.setText("");
		campoDataCasamento.setText("");
		campoConjugue.setText("");
		campoEndereco.setText("");
		campoTelefone.setText("");
		campoCodigo.requestFocus();
		habilitaBotoes(true);
		dizimista = null;
	}
	
	public DizimistaDAO getDizimistaDAO() {
		if (dizimistaDAO == null) {
			dizimistaDAO = new DizimistaDAO();
		}
		return dizimistaDAO;
	}
}

// classe que valida os campos pra receberem somente números
class SomenteNum implements KeyListener{
    public void keyTyped(KeyEvent e) {
       char c = e.getKeyChar();	            
         if(!Character.isDigit(c)){
            e.consume();
         }   	            
    }
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}	
}
