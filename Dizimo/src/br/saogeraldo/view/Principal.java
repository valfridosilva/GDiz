package br.saogeraldo.view;

import java.sql.SQLException;

import javax.swing.UIManager;

import br.saogeraldo.util.FabricaConexao;

public class Principal {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TelaLogin tela = new TelaLogin();		
            	tela.setVisible(true);	       
            	UIManager.put ("OptionPane.yesButtonText","Sim"); 
        		UIManager.put ("OptionPane.noButtonText","Não"); 
        		UIManager.put ("OptionPane.cancelButtonText","Cancelar");
        		UIManager.put ("OptionPane.titleText","Selecione uma opção");
            }
        }); 
		try {
			FabricaConexao.getConexao();
		} catch (SQLException e) {
		}
	}
}
