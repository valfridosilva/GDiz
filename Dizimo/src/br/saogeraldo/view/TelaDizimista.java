package br.saogeraldo.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.util.Data;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelaDizimista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botaoSalvar;
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
	private MaskFormatter format;
	private MaskFormatter format2;	
	private MaskFormatter format3;	
	private MaskFormatter format4;
		
	public TelaDizimista(String titulo){
		super(titulo);
	}
	
	public void go(String labelBotao, boolean status, DizimistaVO dizimista) {		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new FlowLayout());
		setResizable(false);
				
		try {
			format = new MaskFormatter("##/##/####");	
			format2 = new MaskFormatter("##/##/####");	
			format3 = new MaskFormatter("(##)####-####");
			format4 = new MaskFormatter("##/##/####");
					
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		botaoSalvar = new JButton(labelBotao);
		botaoLimpar = new JButton("Limpar");
		labelNome = new JLabel("* Nome:");
		labelCodigo = new JLabel("* Código:");
		labelDataNascimento = new JLabel("* Data Nascimento:");
		labelDataNascimentoConjugue = new JLabel("D.N. Conjugue:");
		labelConjugue = new JLabel(" Conjugue:");
		labelEndereco = new JLabel(" Endereço:");
		labelTelefone = new JLabel(" Telefone:");
		labelDataCasamento = new JLabel(" Data Casamento:");

		campoNome = new JTextField(100);
		campoCodigo = new JTextField(10);
		campoConjugue = new JTextField(100);		
		campoEndereco = new JTextField(100);		
		campoDataNascimento = new JFormattedTextField(format);
		campoDataCasamento = new JFormattedTextField(format2);
		campoDataNascimentoConjugue = new JFormattedTextField(format4);
		campoTelefone = new JFormattedTextField(format3);		
			
		campoNome.setEditable(status);		
		campoCodigo.setEditable(status);
		campoConjugue.setEditable(status);		
		campoEndereco.setEditable(status);
		campoTelefone.setEditable(status);
		campoDataNascimento.setEditable(status);
		campoDataNascimentoConjugue.setEditable(status);
		campoDataCasamento.setEditable(status);

		super.add(getpanelform());
				
		if (labelBotao.equals("Alterar")){
			campoCodigo.setEditable(false);
			setTela(dizimista);
		}else if (labelBotao.equals("Excluir")){
			botaoLimpar.setVisible(false);
			setTela(dizimista);
		}else if (labelBotao.equals("Consultar")){
			botaoLimpar.setVisible(false);
			botaoSalvar.setVisible(false);
			setTela(dizimista);
		}
		super.pack();

	}		

	public JPanel getpanelform() {
		
		FormLayout formlayout = new FormLayout(
				"2dlu, pref, 2dlu, 100px, 2dlu, pref, 2dlu, 90px, 2dlu, pref, 2dlu, 70px, 2dlu",
				"2dlu, top:pref, 2dlu, top:pref, 2dlu, top:pref, 2dlu, pref, 2dlu, pref, 8dlu, pref, 8dlu");
		JPanel jpanel = new JPanel(formlayout);		
		jpanel.setBorder(BorderFactory.createTitledBorder("Dados "));
		CellConstraints cellconstraints = new CellConstraints();		
		campoCodigo.addKeyListener( new SomenteNum());		
		
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {					
				if (botaoSalvar.getText().equals("Alterar")){					
					alterar(evt);					
				}else if (botaoSalvar.getText().equals("Excluir")){
					excluir(evt);	
				}else{
					salvar(evt);
				}
			}

		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {				
					limpar();				
			}

		});
				
		jpanel.add(labelCodigo, cellconstraints.xy(2, 2));
		jpanel.add(campoCodigo, cellconstraints.xy(4, 2));
		jpanel.add(labelDataNascimento, cellconstraints.xy(6, 2));
		jpanel.add(campoDataNascimento, cellconstraints.xyw(8, 2, 3));
		jpanel.add(labelNome, cellconstraints.xy(2, 4));
		jpanel.add(campoNome, cellconstraints.xyw(4, 4, 9));	
		jpanel.add(labelConjugue, cellconstraints.xy(2, 6));
		jpanel.add(campoConjugue, cellconstraints.xyw(4, 6, 3));		
		jpanel.add(labelDataNascimentoConjugue, cellconstraints.xy(8, 6));
		jpanel.add(campoDataNascimentoConjugue, cellconstraints.xyw(10, 6, 3));			
		jpanel.add(labelEndereco, cellconstraints.xy(2, 8));
		jpanel.add(campoEndereco, cellconstraints.xyw(4, 8, 9));		
		jpanel.add(labelTelefone, cellconstraints.xy(2, 10));
		jpanel.add(campoTelefone, cellconstraints.xy(4, 10));		
		jpanel.add(labelDataCasamento, cellconstraints.xy(6, 10));
		jpanel.add(campoDataCasamento, cellconstraints.xyw(8, 10,3));		
		jpanel.add(botaoSalvar,cellconstraints.xy(6, 12));
		jpanel.add(botaoLimpar,cellconstraints.xy(8, 12));
		
		return jpanel;

	}
	
	public DizimistaVO getTela(){				
		DizimistaVO dz = new DizimistaVO();	
		dz.setIdDizimista(Integer.parseInt(campoCodigo.getText()));
		dz.setNome(campoNome.getText());			
		dz.setDtNascimento(Data.converteBRtoUSA(campoDataNascimento.getText()));
		if(campoConjugue.getText().equals("")){
			dz.setNomeConjugue(null);
		}else{
			dz.setNomeConjugue(campoConjugue.getText());
		}
		
		if(campoDataNascimentoConjugue.getText().trim().length() != 10){
			dz.setDtNascimentoConjugue(Data.converteBRtoUSA(""));
		}else{
			dz.setDtNascimentoConjugue(Data.converteBRtoUSA(campoDataNascimentoConjugue.getText()));
		}	
		
		if(campoDataCasamento.getText().trim().length() != 10){
			dz.setDtCasamento(Data.converteBRtoUSA(""));
		}else{
			dz.setDtCasamento(Data.converteBRtoUSA(campoDataCasamento.getText()));
		}	
		
		if(campoTelefone.getText().trim().length() != 13){
			dz.setTelefone(null);
		}else{
			dz.setTelefone(campoTelefone.getText());
		}
		if(campoEndereco.getText().equals("")){
			dz.setEndereco(null);	
		}else{
			dz.setEndereco(campoEndereco.getText());	
		}
	
		return dz;
	}

	private void excluir(ActionEvent evt) {
		DizimistaVO d = getTela();
		try {
			new DizimistaDAO().delete(d.getIdDizimista());
			dispose();
			JOptionPane.showMessageDialog(this, "Dizimista excluído com sucesso!!","sucesso",JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}

	private void salvar(ActionEvent evt) {
		DizimistaVO d;
		DizimistaDAO dao = new DizimistaDAO();
		if (validaCampos()){
			if(validaRegra()){
				d = getTela();
				try {				
					if(!dao.existeCodigo(d.getIdDizimista())){
						dao.insert(d);								
						dispose();
						JOptionPane.showMessageDialog(this, "Dizimista cadastrado com sucesso!!","sucesso",JOptionPane.INFORMATION_MESSAGE);					
					}else{
						JOptionPane.showMessageDialog(this, "Este código de Dizimista já existe!!","Atenção!",JOptionPane.WARNING_MESSAGE);
						campoCodigo.requestFocus();
						campoCodigo.setText("");
					}				
					
				} catch (SQLException e) {			
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, "Preencha o nome do Conjugue!!!!","Atenção",JOptionPane.WARNING_MESSAGE);
				pack();
			}			
		}else{
			JOptionPane.showMessageDialog(this, "Campo(s) em branco!!!!","Atenção",JOptionPane.WARNING_MESSAGE);
			pack();
		}		
	
	}
	
	private void alterar(ActionEvent evt) {
		DizimistaVO dz;
		if (validaCampos()){
			if(validaRegra()){
				dz = getTela();
				try {
					new DizimistaDAO().update(dz);
					dispose();
					JOptionPane.showMessageDialog(this, "Dizimista alterado com sucesso!!","sucesso",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e) {			
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, "Preencha o nome do Conjugue!!!!","Atenção",JOptionPane.WARNING_MESSAGE);
				pack();
			}			
		}else{
			JOptionPane.showMessageDialog(this, "Campo(s) em branco!!!!","Atenção",JOptionPane.WARNING_MESSAGE);
			pack();
		}		
		
	}	

	public boolean validaCampos() {
		boolean flag = true;		
				
		if (campoNome.getText().equals("")) {
			flag = false;
		}else if (campoCodigo.getText().equals("")) {
			flag = false;
		}else if (campoDataNascimento.getText().trim().length() != 10) {
			flag = false;		
		}		
		return flag;		
	}
	
	private boolean validaRegra(){
		boolean flag = true;		
		if(campoDataNascimentoConjugue.getText().trim().length() == 10 && campoConjugue.getText().equals("")){
			campoConjugue.requestFocus();
			flag = false;
		}
		if(campoDataCasamento.getText().trim().length() == 10 && campoConjugue.getText().equals("")){
			campoConjugue.requestFocus();
			flag = false;
		}
		return flag;
	}
	
	public void setTela(DizimistaVO dz){
		campoNome.setText(dz.getNome());
		campoCodigo.setText(String.valueOf(dz.getIdDizimista()));
		campoConjugue.setText(dz.getNomeConjugue());		
		campoDataNascimento.setText(Data.converteUSAtoBR(dz.getDtNascimento(), false));	
		campoDataCasamento.setText(Data.converteUSAtoBR(dz.getDtCasamento(), false));
		campoDataNascimentoConjugue.setText(Data.converteUSAtoBR(dz.getDtNascimentoConjugue(), false));
		campoEndereco.setText(dz.getEndereco());		
		campoTelefone.setText(dz.getTelefone());		
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
