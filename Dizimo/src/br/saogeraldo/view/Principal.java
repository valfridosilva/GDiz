package br.saogeraldo.view;

import javax.swing.UIManager;

public class Principal {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TelaLogin tela = new TelaLogin();		
            	tela.setVisible(true);	       
            	UIManager.put ("OptionPane.yesButtonText","Sim"); 
        		UIManager.put ("OptionPane.noButtonText","N�o"); 
        		UIManager.put ("OptionPane.cancelButtonText","Cancelar");
        		UIManager.put ("OptionPane.titleText","Selecione uma op��o");
            }
        }); 
	}
}
