package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.AnoMes;
import br.saogeraldo.util.ItemValue;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;
import br.saogeraldo.util.ValidacaoException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelaPesquisaPagamento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campoAno;
	private JComboBox<ItemValue> comboMes;
	private JLabel rotuloAno;
	private JLabel rotuloMes;
	private JButton botaoPesquisar;	
	private static Logger logger = Logger.getLogger(TelaPesquisaPagamento.class);
	
	public TelaPesquisaPagamento(TelaMenu telaMenu) {
		super("Pagamento", true, true, true, true);
		
		telaMenu.addJanela(this);
		
		setSize(380, 100);
		setLayout(new FlowLayout());
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		rotuloAno = new JLabel("* Ano:");
		rotuloAno.setToolTipText("Ano");
		rotuloMes = new JLabel("* Mês");
		
		campoAno = new JTextField(4);
		comboMes = telaMenu.getComboMes();
		initAnoMes();
		
		botaoPesquisar = new JButtonEnter("Gerar Relatório");
		
		JPanel painel = getPanelForm();

		super.add(painel);

		JPanel painelBotao = new JPanel();

		painelBotao.add(botaoPesquisar);

		super.add(painelBotao);
		
	}	
	
	public JPanel getPanelForm() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, 40px, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, 130px, 2dlu, 130px, 2dlu, 110px, 2dlu",
				"2dlu, 10px, 5dlu, pref, 5dlu, 10px, 5dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Pesquisa "));
		CellConstraints cellconstraints = new CellConstraints();		
		
		botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pesquisar();
			}
		});
				
		jpanel.add(rotuloMes, cellconstraints.xy(4, 4));
		jpanel.add(comboMes, cellconstraints.xy(6, 4));	
		
		jpanel.add(rotuloAno, cellconstraints.xy(12, 4));
		jpanel.add(campoAno, cellconstraints.xy(14, 4));	
		
		jpanel.add(botaoPesquisar, cellconstraints.xy(4, 6));
	
		return jpanel;
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
		if (campoAno.getText().length()!=4){
			campoAno.requestFocus();
			throw new ValidacaoException(String.format(Mensagem.CAMPO_OBRIGATORIO,rotuloAno.getToolTipText()));
		}
	}

	private void initAnoMes(){
		comboMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		campoAno.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	}

	private void pesquisar() {					
		try {				
			valida();
			RelatorioBO relatorio = new RelatorioBO();
			relatorio.runFinanceiro(new AnoMes(Integer.parseInt(campoAno.getText()+((ItemValue)comboMes.getSelectedItem()).getKey())));
		} catch (ValidacaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (RegraDeNegocioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Mensagem.ALERTA, JOptionPane.WARNING_MESSAGE);
		} catch (RelatorioException e) {
			logger.error(Mensagem.ERRO_GERACAO_RELATORIO, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_GERACAO_RELATORIO, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error(Mensagem.ERRO_SISTEMA, e);
			JOptionPane.showMessageDialog(this, Mensagem.ERRO_SISTEMA, Mensagem.ERRO, JOptionPane.ERROR_MESSAGE);
		}		
	}
}
