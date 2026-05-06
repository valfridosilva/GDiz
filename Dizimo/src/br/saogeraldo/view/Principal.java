package br.saogeraldo.view;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import br.saogeraldo.util.FabricaConexao;
import br.saogeraldo.util.Mensagem;

public class Principal {

	private static Logger logger = Logger.getLogger(Principal.class);

	// Porta fixa para controlar instância única
	@SuppressWarnings("unused")
	private static ServerSocket lockSocket;

	public static void main(String[] args) {

		try {

			lockSocket = new ServerSocket(45678);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "O programa Dizimo já está em execução.", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			System.exit(0);
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("OptionPane.yesButtonText", "Sim");
				UIManager.put("OptionPane.noButtonText", "Não");
				UIManager.put("OptionPane.cancelButtonText", "Cancelar");
				UIManager.put("OptionPane.titleText", "Selecione uma opção");

				TelaLogin tela = new TelaLogin();
				tela.setVisible(true);
			}
		});
		
		try {

			FabricaConexao.getConexao();

		} catch (SQLException e) {

			logger.error(Mensagem.ERRO_BANCO_DADOS, e);

			JOptionPane.showMessageDialog(null, Mensagem.ERRO_BANCO_DADOS, Mensagem.ERRO,JOptionPane.ERROR_MESSAGE);
		}
	}
}