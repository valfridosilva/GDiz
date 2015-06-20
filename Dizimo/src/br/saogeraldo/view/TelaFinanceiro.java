package br.saogeraldo.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.bean.FinanceiroVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.dao.FinanceiroDAO;
import br.saogeraldo.util.AnoMes;
import br.saogeraldo.util.ItemValue;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.ModeloTabela;
import br.saogeraldo.util.TextFieldMoedaReal;
import br.saogeraldo.util.Util;
import br.saogeraldo.util.ValidacaoException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelaFinanceiro extends JInternalFrame implements TelaListagem{

	private static final long serialVersionUID = 1L;
	private JButton botaoPesquisarPorNome;
	private JButton botaoPesquisarPorCodigo;
	private JButton botaoSalvar;
	private JButton botaoExcluir;
	private JButton botaoLimpar;
	private JLabel labelCodigo;	
	private JLabel labelNome;	
	private JLabel labelMes;
	private JLabel labelValor;
	private JLabel labelAno;
	private JTextField campoCodigo;
	private JTextField campoNome;	
	private JTextField campoAno;
	private JComboBox<ItemValue> comboMes;
	private TextFieldMoedaReal campoValor;
	private JTable tabelaHistorico;
	private ModeloTabela modeloTabela; 
	private FinanceiroDAO financeiroDAO;
	private DizimistaDAO dizimistaDAO;
	private TelaMenu telaMenu;
	private static final String TIPO_OBJETO = "Pagamento";
	private static Logger logger = Logger.getLogger(TelaFinanceiro.class);
	private JPanel panelHistorico;
	private Map<Integer, FinanceiroVO> mapa;
		
	public TelaFinanceiro(TelaMenu telaMenu){
		super(TIPO_OBJETO, true, true, true, true);
		setLayout(new FlowLayout());
		setVisible(true);
		boolean status = true;
		
		this.telaMenu = telaMenu;
		telaMenu.addJanela(this);
		
		modeloTabela = new ModeloTabela(new String[]{ "Referência", "Valor", "Data", "Excluir"});
		tabelaHistorico = new JTable(modeloTabela);
		
		redimensionaColuma(100);
		alinhaTextoColuna();
		
		tabelaHistorico.setRowHeight(22); // tamanho da linha
		tabelaHistorico.getTableHeader().setReorderingAllowed(false); // impede que o usuário mova as colunas 
		tabelaHistorico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tabelaHistorico.setPreferredScrollableViewportSize(new Dimension(610,300));	

		botaoPesquisarPorNome = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("imagens/pesquisar.gif")));
		botaoPesquisarPorNome.setToolTipText("Pesquisar por Nome");
		botaoPesquisarPorCodigo = new JButtonEnter(new ImageIcon(getClass().getClassLoader().getResource("imagens/pesquisar.gif")));
		botaoPesquisarPorCodigo.setToolTipText("Pesquisar por Código");
		botaoSalvar = new JButtonEnter(Mensagem.LABEL_SALVAR);
		botaoExcluir = new JButtonEnter(Mensagem.LABEL_EXCLUIR);
		botaoLimpar = new JButtonEnter(Mensagem.LABEL_LIMPAR);
		labelNome = new JLabel("* Nome:");
		labelNome.setToolTipText("Nome");
		labelCodigo = new JLabel("* Código:");
		labelCodigo.setToolTipText("Código");
		labelMes = new JLabel("* Mês referência:");
		labelMes.setToolTipText("Mês referência");
		labelValor = new JLabel("* Valor:");
		labelValor.setToolTipText("Valor");
		labelAno = new JLabel("* Ano:");
		labelAno.setToolTipText("Ano");
		
		campoNome = new JTextField(100);
		campoCodigo = new JTextField(10);
		campoAno = new JTextField(4);
		comboMes = telaMenu.getComboMes();
		initAnoMes();
		campoValor = new TextFieldMoedaReal();
			
		campoNome.setEditable(status);		
		campoCodigo.setEditable(status);
		campoValor.setEditable(status);


		JPanel painel = getPanelForm();

		super.add(painel);
		
		panelHistorico = getPanelHistorico();
		super.add(panelHistorico);

		habilitaBotoes(true,false);
		
		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoSalvar);
		painelBotao.add(botaoExcluir);
		painelBotao.add(botaoLimpar);

		super.add(painelBotao);
		
		campoCodigo.requestFocus();

		super.pack();
	}

	private void alinhaTextoColuna() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tabelaHistorico.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tabelaHistorico.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tabelaHistorico.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
	}

	private void redimensionaColuma(int value) {
		tabelaHistorico.getColumnModel().getColumn(0).setMinWidth(value);
		tabelaHistorico.getColumnModel().getColumn(1).setMinWidth(value);
		tabelaHistorico.getColumnModel().getColumn(2).setMinWidth(value);
		tabelaHistorico.getColumnModel().getColumn(3).setMinWidth(value);
	}
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, pref, 2dlu, 130px, 2dlu, pref, 2dlu, 40px, 2dlu, pref, 2dlu, 50px, 2dlu, 70px, 2dlu, 40px, 2dlu, 90px, 2dlu",
				"2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Dados "));
		
		CellConstraints cellconstraints = new CellConstraints();		
		campoCodigo.addKeyListener( new SomenteNum());		
		campoAno.addKeyListener( new SomenteNum());
		
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {					
				salvar();
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
		
		jpanel.add(labelMes, cellconstraints.xy(2, 6));
		jpanel.add(comboMes, cellconstraints.xyw(4, 6, 3));
		
		jpanel.add(labelAno, cellconstraints.xy(8, 6));
		jpanel.add(campoAno, cellconstraints.xyw(10, 6, 3));
		
		jpanel.add(labelValor, cellconstraints.xy(2, 8));
		jpanel.add(campoValor, cellconstraints.xyw(4, 8, 3));
		
		return jpanel;
	}
	
	public JPanel getPanelHistorico() {
		FormLayout formlayout = new FormLayout(
				"2dlu, 100px, 2dlu, 130px, 2dlu, pref, 2dlu, 40px, 2dlu, pref, 2dlu, 50px, 2dlu, 70px, 2dlu, 40px, 2dlu, 90px, 2dlu",
				"2dlu, 111px, 5dlu");

		JPanel jpanelHistorico = new JPanel(formlayout);
		
		jpanelHistorico.setBorder(BorderFactory.createTitledBorder("Histórico de Lançamentos "));
		CellConstraints cellconstraints = new CellConstraints();
		
		JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
		add(scrollPane);
		
		jpanelHistorico.add(scrollPane, cellconstraints.xyw(2, 2, 16));
		jpanelHistorico.setVisible(false);
		return jpanelHistorico;
		
	}
	
	public FinanceiroVO getObjectFomTela() throws ValidacaoException{				
		FinanceiroVO financeiro = new FinanceiroVO();
		
		if(campoCodigo.getText().trim().isEmpty()){
			financeiro.setIdDizimista(0);
		}else{
			financeiro.setIdDizimista(Integer.parseInt(campoCodigo.getText()));
		}
		
		financeiro.setDataLancamento(new Date());
		
		 if (campoValor.getNumber() != null) {                   
			 financeiro.setValor(campoValor.getNumber().doubleValue());                    
	     }else{
	    	 financeiro.setValor(null);
	     }
		 if(campoAno.getText().length() != 4){
			 financeiro.setAnoMesReferencia(null);
		 }else{
			 financeiro.setAnoMesReferencia(new AnoMes(Integer.parseInt(campoAno.getText()+((ItemValue)comboMes.getSelectedItem()).getKey())));
		 }
		 
	
		return financeiro;
	}
	
	public void habilitaBotoes(boolean flagSalvar, boolean flagExcluir) {
		botaoSalvar.setVisible(flagSalvar);
		botaoExcluir.setVisible(flagExcluir);
	}
	
	public void restaura(boolean isMaximum) {
		this.setVisible(true);
		try {
			this.setIcon(false);
			this.setMaximum(isMaximum);
		} catch (PropertyVetoException e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
		}
	}

	private void excluir() {
		try {
			List<Integer> financeirosSelecionados = getFinanceirosSelecionados();
			if(financeirosSelecionados.isEmpty()){
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.HISTORICO_SELECAO, Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
				return;
			}
			Object[] options = { Mensagem.SIM, Mensagem.NAO };
			int res = JOptionPane.showOptionDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.CONFIRMA_EXCLUSAO_HISTORICO, financeirosSelecionados.size()), Mensagem.CONFIRMA,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (res == JOptionPane.YES_OPTION) {
				getFinanceiroDAO().delete(financeirosSelecionados);
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
	
	private List<Integer> getFinanceirosSelecionados(){
		int cont = 0;
		FinanceiroVO f = null;
		List<Integer> pagamentos = new ArrayList<Integer>();
		while(cont < tabelaHistorico.getRowCount() ){
			boolean selecionado = (Boolean) tabelaHistorico.getValueAt(cont, 3);
			if (selecionado){				
				f = mapa.get(cont);
				pagamentos.add(f.getIdFinanceiro());
			}			
			cont++;
		}
		return pagamentos;
	}

	private void salvar() {
		try {
			FinanceiroVO pagamento = getObjectFomTela();
			validaPagamento(pagamento);
			if(!getFinanceiroDAO().existePagamento(pagamento.getIdDizimista(), pagamento.getAnoMesReferencia())){
				getFinanceiroDAO().insert(pagamento);								
				limpar();
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_INSERIDO, TIPO_OBJETO), Mensagem.SUCESSO,
						JOptionPane.INFORMATION_MESSAGE);					
			}else{
				JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.REGISTRO_DUPLICADO,TIPO_OBJETO,"Ano/Mês de referência"),Mensagem.ALERTA,JOptionPane.WARNING_MESSAGE);
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
	
	private void pesquisaPorNome() {
		try {
			if (!campoNome.getText().isEmpty()) {
				List<DizimistaVO> lista = getDizimistaDAO().getDizimistaByName(campoNome.getText());
				if (!lista.isEmpty()) {
					if (lista.size() == 1) {
						setObjectToTela(lista.get(0));
					} else {
						TelaListagemDizimista tela = new TelaListagemDizimista(telaMenu, lista, this);
						tela.setBounds((int)(telaMenu.ESPACO_ENTRE_JANELA), (int) (telaMenu.ESPACO_ENTRE_JANELA / 1.5), telaMenu.getDesktop().getWidth() - (telaMenu.ESPACO_ENTRE_JANELA*2), telaMenu.getDesktop()
								.getHeight() - (int)(telaMenu.ESPACO_ENTRE_JANELA * 2));
						
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
				} else {
					JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), String.format(Mensagem.NENHUM_DIZIMISTA_ENCONTRADO,campoCodigo.getText()), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
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
	
	
	private void validaPagamento(FinanceiroVO financeiro) throws ValidacaoException {
		if (financeiro.getIdDizimista()==0) {
			campoCodigo.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelCodigo.getToolTipText()));
		}
		if(financeiro.getAnoMesReferencia() == null){
			campoAno.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelAno.getToolTipText()));
		}
		if (financeiro.getValor() == null) {
			campoValor.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,labelValor.getToolTipText()));
		}
	}
	
	private void populaHistorico(SortedSet<FinanceiroVO> financeiros){
		mapa = new HashMap<Integer, FinanceiroVO>();
		int index = 0;
		modeloTabela.clean();
		for(FinanceiroVO f: financeiros){
			modeloTabela.add(transformaToArray(f));
			mapa.put(index++, f);
		}
		modeloTabela.fireTableRowsInserted(0, financeiros.size());
	}
	
	private Object[] transformaToArray(FinanceiroVO f){
		Object[] object = new Object[4];
		object[0] = f.getAnoMesReferencia().toString();
		object[1] = "R$ "+Util.convertValor(f.getValor());
		object[2] = Util.convertDateToString(f.getDataLancamento());
		object[3] = false;
		return object;
	}

	private void setObjectToTela(SortedSet<FinanceiroVO> financeiros){
		FinanceiroVO ultimoFinanceiro = financeiros.first();
		if(ultimoFinanceiro == null){
			habilitaPainelHistorico(false);
			return;
		}
		campoValor.requestFocus();
		//campoValor.setNumber(new BigDecimal(String.valueOf(ultimoFinanceiro.getValor())).setScale(2)); 
		if(ultimoFinanceiro.getAnoMesReferencia().getMes() == 12){
			comboMes.setSelectedIndex(0);
			campoAno.setText(String.valueOf(ultimoFinanceiro.getAnoMesReferencia().getAno()+1));
		}else{
			comboMes.setSelectedIndex(ultimoFinanceiro.getAnoMesReferencia().getMes());
			campoAno.setText(String.valueOf(ultimoFinanceiro.getAnoMesReferencia().getAno()));
		}
		habilitaPainelHistorico(true);
		populaHistorico(financeiros);
		
	}
	
	private void habilitaPainelHistorico(boolean flag){
		panelHistorico.setVisible(flag);
		habilitaBotoes(true,flag);
	}
	
	public void setObjectToTela(DizimistaVO dizimista){
		try{
			limpar();
			campoNome.setText(dizimista.getNome());
			campoCodigo.setText(String.valueOf(dizimista.getIdDizimista()));
			
			SortedSet<FinanceiroVO> financeiros = getFinanceiroDAO().getLastPagamentoByCodigoDizimista(dizimista.getIdDizimista());
			if(!financeiros.isEmpty()){
				setObjectToTela(financeiros);
			}
			
		} catch (SQLException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(telaMenu.getDesktop().getSelectedFrame(), Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limpar(){
		campoNome.setText("");
		campoCodigo.setText("");
		campoValor.setText("");
		initAnoMes();
		habilitaPainelHistorico(false);
		campoCodigo.requestFocus();
	}
	
	private void initAnoMes(){
		comboMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		campoAno.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	}
	
	public FinanceiroDAO getFinanceiroDAO() {
		if (financeiroDAO == null) {
			financeiroDAO = new FinanceiroDAO();
		}
		return financeiroDAO;
	}
	
	public DizimistaDAO getDizimistaDAO() {
		if (dizimistaDAO == null) {
			dizimistaDAO = new DizimistaDAO();
		}
		return dizimistaDAO;
	}

}
