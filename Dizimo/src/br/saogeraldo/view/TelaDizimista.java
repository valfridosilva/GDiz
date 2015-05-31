package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JRadioButton radioConjugeDizimista;
	private JRadioButton radioConjugeNaoDizimista;
	private ButtonGroup grupo;
	private JLabel labelCodigo;	
	private JLabel labelNome;	
	private JLabel labelDataNascimento;
	private JLabel labelNomeConjuge;
	private JLabel labelEndereco;
	private JLabel labelTelefone;
	private JLabel labelDataCasamento;
	private JLabel labelConjugeDizimista;
	private JLabel labelCodigoConjuge;
	private JTextField campoCodigo;
	private JTextField campoNome;		
	private JTextField campoEndereco;
	private JTextField campoNomeConjuge;
	private JTextField campoCodigoConjuge;
	private JFormattedTextField campoTelefone;
	private JFormattedTextField campoDataNascimento;
	private JFormattedTextField campoDataCasamento;
	private MaskFormatter formatDtNascimento;
	private MaskFormatter formatDtCasamento;	
	private MaskFormatter formatTelefone;	
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
		telaMenu.addJanela(this);
		
		try {
			formatDtNascimento = new MaskFormatter("##/##/####");	
			formatDtCasamento = new MaskFormatter("##/##/####");	
			formatTelefone = new MaskFormatter("(##)####-####");
		} catch (ParseException ex) {
			logger.error("Erro ao criar parser", ex);
		}

		botaoPesquisarPorNome = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("pesquisar.gif")));
		botaoPesquisarPorNome.setToolTipText("Pesquisar por Nome");
		botaoPesquisarPorCodigo = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("pesquisar.gif")));
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
		
		labelEndereco = new JLabel(" Endereço:");
		labelEndereco.setToolTipText("Endereço");
		labelTelefone = new JLabel(" Telefone:");
		labelTelefone.setToolTipText("Telefone");
		labelDataCasamento = new JLabel(" Data Casamento:");
		labelDataCasamento.setToolTipText("Data de Casamento");
		labelNomeConjuge = new JLabel(" Nome do Cônjuge:");
		labelNomeConjuge.setToolTipText("Nome do Cônjuge");
		labelCodigoConjuge = new JLabel(" Código do Cônjuge:");
		labelCodigoConjuge.setToolTipText("Código do Cônjuge");
		labelConjugeDizimista = new JLabel(" Cônjuge Dizimista?");
		labelConjugeDizimista.setToolTipText("Cônjuge é Dizimista");
		radioConjugeDizimista = new JRadioButton("Sim",false);
		radioConjugeDizimista.setIgnoreRepaint(false);
		radioConjugeNaoDizimista = new JRadioButton("Não",false);
		grupo = new ButtonGroup();
		grupo.add(radioConjugeDizimista);
		grupo.add(radioConjugeNaoDizimista);

		campoNome = new JTextField(100);
		campoCodigo = new JTextField(10);
		campoEndereco = new JTextField(100);		
		campoNomeConjuge = new JTextField(100);	
		campoCodigoConjuge = new JTextField(100);
		campoDataNascimento = new JFormattedTextField(formatDtNascimento);
		campoDataNascimento.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoDataCasamento = new JFormattedTextField(formatDtCasamento);
		campoDataCasamento.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoTelefone = new JFormattedTextField(formatTelefone);	
		campoTelefone.setFocusLostBehavior(JFormattedTextField.COMMIT);
			
		campoNome.setEditable(status);		
		campoCodigo.setEditable(status);
		campoEndereco.setEditable(status);
		campoTelefone.setEditable(status);
		campoDataNascimento.setEditable(status);
		campoDataCasamento.setEditable(status);
		campoNomeConjuge.setEditable(!status);		
		campoCodigoConjuge.setEditable(!status);

		habilitaBotoes(true);

		JPanel painel = getPanelForm();

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
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, pref, 2dlu, 100px, 2dlu, pref, 2dlu, 40px, 2dlu, pref, 2dlu, 50px, 2dlu, 70px, 2dlu, 40px, 2dlu, 90px, 2dlu",
				"2dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu, pref, 5dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Dados "));
		CellConstraints cellconstraints = new CellConstraints();		
		campoCodigo.addKeyListener( new SomenteNum());		
		campoCodigoConjuge.addKeyListener( new SomenteNum());		
		
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
		
		radioConjugeDizimista.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				campoCodigoConjuge.setEditable(true);
				campoNomeConjuge.setEditable(false);
				campoNomeConjuge.setText("");
			}
		});
		
		radioConjugeNaoDizimista.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				campoNomeConjuge.setEditable(true);
				campoCodigoConjuge.setEditable(false);
				campoCodigoConjuge.setText("");
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
		
		jpanel.add(labelEndereco, cellconstraints.xy(2, 12));
		jpanel.add(campoEndereco, cellconstraints.xyw(4, 12, 11));
		
		jpanel.add(labelConjugeDizimista, cellconstraints.xy(2, 14));
		jpanel.add(radioConjugeDizimista, cellconstraints.xy(4, 14));	
		jpanel.add(radioConjugeNaoDizimista, cellconstraints.xy(6, 14));	
	
		jpanel.add(labelNomeConjuge, cellconstraints.xy(2, 16));
		jpanel.add(campoNomeConjuge, cellconstraints.xyw(4, 16, 11));	
		
		jpanel.add(labelCodigoConjuge, cellconstraints.xy(2, 18));
		jpanel.add(campoCodigoConjuge, cellconstraints.xyw(4, 18, 3));	
		return jpanel;
	}
	
	public DizimistaVO getObjectFomTela() throws ValidacaoException{				
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
			dz.setNome(campoNome.getText().trim());
		}
		
		dz.setDtNascimento(getDate(campoDataNascimento, labelDataNascimento));
		
		dz.setDtCasamento(getDate(campoDataCasamento, labelDataCasamento));
		
		if(campoTelefone.getText().equals(campoTelefone.getValue())){
			dz.setTelefone(campoTelefone.getText());
		} else {
			if (campoTelefone.getText().trim().equals("(  )    -")) {
				dz.setTelefone(null);
			} else {
				campoTelefone.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoTelefone.getText(), labelTelefone.getToolTipText()));
			}
		}
		if(campoEndereco.getText().trim().isEmpty()){
			dz.setEndereco(null);	
		}else{
			dz.setEndereco(campoEndereco.getText().trim());	
		}
		
		if(campoNomeConjuge.getText().trim().isEmpty()){
			dz.setNomeConjuge(null);
		}else{
			dz.setNomeConjuge(campoNomeConjuge.getText().trim());
		}
		
		if(campoCodigoConjuge.getText().trim().isEmpty()){
			dz.setIdConjugeDizimista(null);
		}else{
			dz.setIdConjugeDizimista(Integer.parseInt(campoCodigoConjuge.getText()));
		}
	
		return dz;
	}
	
	private Date getDate(JFormattedTextField campoData, JLabel labelData) throws ValidacaoException{
		if (campoData.getText().equals(campoData.getValue())) {
			try {
				return DataUtil.convertStringToDate(campoData.getText());
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
			int res = JOptionPane.showOptionDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.CONFIRMA_EXCLUSAO, dizimista.getNome()), Mensagem.CONFIRMA,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (res == JOptionPane.YES_OPTION) {
				getDizimistaDAO().delete(dizimista.getIdDizimista());
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
			DizimistaVO dizimista = getObjectFomTela();
			validaDizimista(dizimista);
			if(!getDizimistaDAO().existeCodigo(dizimista.getIdDizimista())){
				getDizimistaDAO().insert(dizimista);								
				limpar();
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_INSERIDO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);					
			}else{
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_DUPLICADO,TIPO_OBJETO,"Código"),Mensagem.ALERTA,JOptionPane.WARNING_MESSAGE);
				campoCodigo.requestFocus();
				campoCodigo.setText("");
			}	
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
			DizimistaVO dizimista = getObjectFomTela();
			validaDizimista(dizimista);
			getDizimistaDAO().update(dizimista);								
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
			if (!campoNome.getText().isEmpty()) {
				List<DizimistaVO> lista = getDizimistaDAO().getDizimistaByName(campoNome.getText());
				if (!lista.isEmpty()) {
					if (lista.size() == 1) {
						setObjectToTela(lista.get(0));
						habilitaBotoes(false);
					} else {
						TelaListagemDizimista tela = new TelaListagemDizimista(telaMenu, lista, this);
						tela.setBounds(telaMenu.ESPACO_ENTRE_JANELA / 2, (int) (telaMenu.ESPACO_ENTRE_JANELA / 1.5), telaMenu.getDesktop().getWidth() - telaMenu.ESPACO_ENTRE_JANELA, telaMenu.getDesktop()
								.getHeight()
								- (int)(telaMenu.ESPACO_ENTRE_JANELA * 1.5));
						telaMenu.getDesktop().moveToFront(tela);
					}
				} else {
					JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				campoNome.requestFocus();
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.CAMPO_PESQUISA,labelNome.getToolTipText()), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void pesquisaPorCodigo() {
		try {
			if (!campoCodigo.getText().trim().isEmpty()) {
				DizimistaVO dizimista = getDizimistaDAO().getDizimistaByCodigo(Integer.parseInt(campoCodigo.getText()));
				if(dizimista != null){
					setObjectToTela(dizimista);
					habilitaBotoes(false);
				} else {
					JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.NENHUM_REGISTRO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				campoCodigo.requestFocus();
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.CAMPO_PESQUISA,labelCodigo.getToolTipText()), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void validaDizimista(DizimistaVO dizimista) throws ValidacaoException, SQLException {
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
		if(dizimista.getDtCasamento() != null && grupo.getSelection() == null){
			labelConjugeDizimista.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelConjugeDizimista.getToolTipText()));
		}
		if(radioConjugeDizimista.isSelected()){
			if(dizimista.getIdConjugeDizimista() == null){
				campoCodigoConjuge.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelCodigoConjuge.getToolTipText()));
			}
			DizimistaVO dizimistaConjuge = dizimistaDAO.getDizimistaByCodigo(dizimista.getIdConjugeDizimista());
			if(dizimistaConjuge == null){
				campoCodigoConjuge.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CODIGO_DIZIMISTA_INVALIDO,dizimista.getIdConjugeDizimista()));
			}
		}
		if(radioConjugeNaoDizimista.isSelected()){
			if(dizimista.getNomeConjuge() == null){
				campoNomeConjuge.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelNomeConjuge.getToolTipText()));
			}
		}
	}

	public void setObjectToTela(DizimistaVO dz){
		campoNome.setText(dz.getNome());
		campoCodigo.setText(String.valueOf(dz.getIdDizimista()));
		campoDataNascimento.setValue(DataUtil.convertDateToString(dz.getDtNascimento()));
		campoDataCasamento.setValue(DataUtil.convertDateToString(dz.getDtCasamento()));
		campoEndereco.setText(dz.getEndereco());		
		campoTelefone.setValue(dz.getTelefone());
		if(dz.getIdConjugeDizimista() != null){
			radioConjugeDizimista.setSelected(true);
		}
		if(dz.getNomeConjuge() != null && !dz.getNomeConjuge().trim().isEmpty()){
			radioConjugeNaoDizimista.setSelected(true);
		}
		campoNomeConjuge.setText(dz.getNomeConjuge());	
		if(dz.getIdConjugeDizimista()!=null){
			campoCodigoConjuge.setText(String.valueOf(dz.getIdConjugeDizimista()));
		}
		dizimista = dz;
	}
	
	public void limpar(){
		campoNome.setText("");
		campoCodigo.setText("");
		campoDataNascimento.setText("");	
		campoDataCasamento.setText("");
		campoEndereco.setText("");
		campoTelefone.setText("");
		campoCodigo.requestFocus();
		campoNomeConjuge.setText("");
		campoCodigoConjuge.setText("");
		grupo.clearSelection();
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
