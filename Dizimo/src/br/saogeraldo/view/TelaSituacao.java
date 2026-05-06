package br.saogeraldo.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import br.saogeraldo.negocio.RelatorioBO;
import br.saogeraldo.util.JButtonEnter;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;

public class TelaSituacao extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JCheckBox campoInativo;
	private JCheckBox campoFalecido;
	private JLabel rotuloInativo;
	private JLabel rotuloFalecido;
	private JButton botaoPesquisar;	
	private JButton botaoCancelar;	
	private LoadingFrame loadingFrame;
	private static Logger logger = Logger.getLogger(TelaSituacao.class);
	
	public TelaSituacao(TelaMenu telaMenu) {
		super("Pesquisa", true, true, true, true);
		
		telaMenu.addJanela(this);
		

		setSize(380, 100);
		setLayout(new FlowLayout());
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		rotuloInativo = new JLabel("Inativo?");
		rotuloInativo.setToolTipText("Inativo");
		rotuloFalecido = new JLabel("Falecido?");
		rotuloFalecido.setToolTipText("Falecido");
		campoInativo = new JCheckBox();
		campoFalecido = new JCheckBox();
		botaoPesquisar = new JButtonEnter("Gerar Relatório");
		botaoCancelar = new JButtonEnter("Cancelar");
		loadingFrame = new LoadingFrame();
		
		campoInativo.setPreferredSize(new Dimension(70, 22));
		campoFalecido.setPreferredSize(new Dimension(70, 22));
		
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
		        BorderFactory.createTitledBorder("Situação ")
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
		
		campoFalecido.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (campoFalecido.isSelected()) {
		        	campoInativo.setSelected(campoFalecido.isSelected());
		        }
		    }
		});
				
		jpanel.add(rotuloInativo, cellconstraints.xy(4, 4));
		jpanel.add(campoInativo, cellconstraints.xy(6, 4));	
		
		jpanel.add(rotuloFalecido, cellconstraints.xy(10, 4));
		jpanel.add(campoFalecido, cellconstraints.xy(12, 4));	
		
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
	
	private void pesquisar() {					
            loadingFrame.showLoading();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                    	new RelatorioBO().runSituacao(campoInativo.isSelected(), campoFalecido.isSelected());
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
			
	}
}
