package br.saogeraldo.view;

import java.sql.SQLException;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.util.FabricaConexao;
import br.saogeraldo.util.Mensagem;

public class Principal {
	private static Logger logger = Logger.getLogger(Principal.class);
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//TelaLogin tela = new TelaLogin();	
            	TelaLogin.usuario = new UsuarioVO(1,"teste");
            	TelaMenu tela = new TelaMenu();
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
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
		}
	}
}
