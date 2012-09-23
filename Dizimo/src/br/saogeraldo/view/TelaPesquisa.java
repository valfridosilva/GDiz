package br.saogeraldo.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.TipoPesquisa;

public class TelaPesquisa extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private MaskFormatter formatData;
	private JFormattedTextField dataInicial;
	private JFormattedTextField dataFinal;
	private JLabel rotuloDataInicial;
	private JLabel rotuloDataFinal;
	private JButton botao;	
	private TipoPesquisa tipoPesquisa;
	
	public TelaPesquisa(TelaMenu telaMenu, TipoPesquisa tipoPesquisa) {
		super("Casamento", true, true, true, true);
		
		this.tipoPesquisa = tipoPesquisa;
		telaMenu.desktop.add(this);
		
		try {
			formatData = new MaskFormatter("##/##");
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		setSize(380, 100);
		setLayout(new FlowLayout());
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		rotuloDataInicial = new JLabel("Data Inicial:");
		rotuloDataFinal = new JLabel("Data Final:");
		dataInicial = new JFormattedTextField(formatData);
		dataInicial.setFocusLostBehavior(JFormattedTextField.COMMIT);
		dataFinal = new JFormattedTextField(formatData);
		dataFinal.setFocusLostBehavior(JFormattedTextField.COMMIT);
		botao = new JButtonEnter("Gerar Relatório");
		
		dataInicial.setPreferredSize(new Dimension(70, 22));
		dataFinal.setPreferredSize(new Dimension(70, 22));

		add(rotuloDataInicial);
		add(dataInicial);
		add(rotuloDataFinal);
		add(dataFinal);
		add(botao);
		
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pesquisar(evt);
			}
		});
	}	

	public void pesquisar(ActionEvent e) {					
		String data1 = dataInicial.getText();
		String data2 = dataFinal.getText();
		
		if (data1.trim().length() != 5){
			JOptionPane.showMessageDialog(this, "Atenção, campo em branco!","Atenção!",JOptionPane.WARNING_MESSAGE);
			validate();
			dataInicial.requestFocus();
		}else if (data2.trim().length() != 5){
				JOptionPane.showMessageDialog(this, "Atenção, campo em branco!","Atenção!",JOptionPane.WARNING_MESSAGE);
				validate();
				dataFinal.requestFocus();
				}else{
					dispose();						
					try {				
						
						RelatorioBO rel = new RelatorioBO();
						boolean status = false;
						if(TipoPesquisa.ANIVERSARIO.equals(tipoPesquisa)){
							status = rel.runAniversario(data1, data2);
						}
						else if(TipoPesquisa.CASAMENTO.equals(tipoPesquisa)){
							status = rel.runCasamento(data1, data2);
						}
																
						if(!status){
							JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!","Atenção!",JOptionPane.WARNING_MESSAGE);
						}
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, "Data inválida","Erro!",JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
						dispose();
					}			
					
		}
			
	}

}
