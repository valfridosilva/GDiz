package br.saogeraldo.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;
import br.saogeraldo.util.TipoPesquisa;
import br.saogeraldo.util.ValidacaoException;

public class TelaPesquisa extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private MaskFormatter formatData;
	private JFormattedTextField campoDataInicial;
	private JFormattedTextField campoDataFinal;
	private JLabel rotuloDataInicial;
	private JLabel rotuloDataFinal;
	private JButton botaoPesquisar;	
	private JButton botaoCancelar;	
	private TipoPesquisa tipoPesquisa;
	private LoadingFrame loadingFrame;
	private static Logger logger = Logger.getLogger(TelaPesquisa.class);
	
	public TelaPesquisa(TelaMenu telaMenu, TipoPesquisa tipoPesquisa) {
		super(tipoPesquisa.getDescricao(), true, true, true, true);
		
		this.tipoPesquisa = tipoPesquisa;
		telaMenu.addJanela(this);
		
		try {
			formatData = new MaskFormatter("##/##");
		} catch (ParseException ex) {
			logger.error("Erro ao criar parser", ex);
		}

		setSize(380, 100);
		setLayout(new FlowLayout());
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		rotuloDataInicial = new JLabel("Dia/Mês Inicial:");
		rotuloDataInicial.setToolTipText("Dia/Mês Inicial");
		rotuloDataFinal = new JLabel("Dia/Mês Final:");
		rotuloDataFinal.setToolTipText("Dia/Mês Final");
		campoDataInicial = new JFormattedTextField(formatData);
		campoDataInicial.setFocusLostBehavior(JFormattedTextField.COMMIT);
		campoDataFinal = new JFormattedTextField(formatData);
		campoDataFinal.setFocusLostBehavior(JFormattedTextField.COMMIT);
		botaoPesquisar = new JButtonEnter("Gerar Relatório");
		botaoCancelar = new JButtonEnter("Cancelar");
		loadingFrame = new LoadingFrame();
		
		campoDataInicial.setPreferredSize(new Dimension(70, 22));
		campoDataFinal.setPreferredSize(new Dimension(70, 22));
		
		JPanel painel = getPanelForm();

		super.add(painel);

		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoPesquisar);
		painelBotao.add(botaoCancelar);

		super.add(painelBotao);
		
		
	}	
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, 20px, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, 90px, 2dlu, 70px, 2dlu, 70px, 2dlu",
				"2dlu, 10px, 5dlu, pref, 5dlu, 10px, 5dlu, 10px, 5dlu");
		JPanel jpanel = new JPanel(formlayout);	
		jpanel.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createEmptyBorder(40, 10, 20, 10), // top, left, bottom, right
		        BorderFactory.createTitledBorder("Pesquisa ")
		    ));
		CellConstraints cellconstraints = new CellConstraints();		
		
		botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pesquisar();
			}
		});
		
		botaoCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				fechar();
			}
		});
				
		jpanel.add(rotuloDataInicial, cellconstraints.xy(4, 4));
		jpanel.add(campoDataInicial, cellconstraints.xy(6, 4));	
		
		jpanel.add(rotuloDataFinal, cellconstraints.xy(10, 4));
		jpanel.add(campoDataFinal, cellconstraints.xy(12, 4));	
		
		jpanel.add(botaoPesquisar, cellconstraints.xy(4, 6));
		jpanel.add(botaoCancelar, cellconstraints.xy(6, 6));
		
		return jpanel;
	}
	
	private void fechar() {
	    this.dispose();
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
	
	private void valida() throws ValidacaoException {
		if(!campoDataInicial.getText().equals(campoDataInicial.getValue())){
			if (campoDataInicial.getValue()==null) {
				campoDataInicial.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO, rotuloDataInicial.getToolTipText()));
			} else {
				campoDataInicial.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoDataInicial.getText(), rotuloDataInicial.getToolTipText()));
			}
		}
		validaData(campoDataInicial, rotuloDataInicial);
		if(!campoDataFinal.getText().equals(campoDataFinal.getValue())){
			if (campoDataFinal.getValue()==null) {
				campoDataFinal.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO, rotuloDataFinal.getToolTipText()));
			} else {
				campoDataFinal.requestFocus();
				throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campoDataFinal.getText(), rotuloDataFinal.getToolTipText()));
			}
		}
		validaData(campoDataFinal,rotuloDataFinal);
		
	}
	
	private void validaData(JFormattedTextField campo, JLabel label) throws ValidacaoException{
		String[] data = campo.getText().split("/");
		if(Integer.parseInt(data[0]) > 31 || Integer.parseInt(data[1]) > 12){
			campo.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_INVALIDO, campo.getText(), label.getToolTipText()));
		}
	}

	private void pesquisar() {	
        
		try {				
			valida();
			loadingFrame.showLoading();
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	if(TipoPesquisa.ANIVERSARIO.equals(tipoPesquisa)){
	                		new RelatorioBO().runAniversario(campoDataInicial.getText(), campoDataFinal.getText());
	        			}
	        			else if(TipoPesquisa.CASAMENTO.equals(tipoPesquisa)){
	        				new RelatorioBO().runCasamento(campoDataInicial.getText(), campoDataFinal.getText());
	        			}
	                } catch (RegraDeNegocioException e) {
	        			JOptionPane.showMessageDialog(null, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
	        		} catch (RelatorioException e) {
	        			logger.error(Mensagem.ERRO_GERACAO_RELATORIO, e);
	        			JOptionPane.showMessageDialog(null, Mensagem.ERRO_GERACAO_RELATORIO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
	        		} catch (Exception e) {
	        			logger.error(Mensagem.ERRO_SISTEMA, e);
	        			JOptionPane.showMessageDialog(null, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
	                } finally {
	                    SwingUtilities.invokeLater(new Runnable() {
	                        @Override
	                        public void run() {
	                        	loadingFrame.closeLoading();
	                        }
	                    });
	                }
	            }

	        }).start();
			
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} 
	}
	
}
