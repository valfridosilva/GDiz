package br.saogeraldo.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import br.saogeraldo.negocio.*;

public class TelaAniversario extends JFrame {

	private static final long serialVersionUID = 1L;
	private MaskFormatter format;
	private JFormattedTextField dataInicial;
	private JFormattedTextField dataFinal;
	private JLabel rotuloDataInicial;
	private JLabel rotuloDataFinal;
	private JButton botao;	
	
	public TelaAniversario() {
		super("Aniversário");
		
		try {
			format = new MaskFormatter("##/##");
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		setSize(380, 100);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		rotuloDataInicial = new JLabel("Data Inicial:");
		rotuloDataFinal = new JLabel("Data Final:");
		dataInicial = new JFormattedTextField(format);
		dataFinal = new JFormattedTextField(format);
		botao = new JButton("Gerar Relatório");
		
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

	@SuppressWarnings("deprecation")
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
						boolean status = rel.runAniversario(data1, data2);										
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
