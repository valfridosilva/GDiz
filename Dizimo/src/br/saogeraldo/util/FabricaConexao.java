package br.saogeraldo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class FabricaConexao {	
	public static String caminhoBD;
	private static Connection conexao;
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	private static Logger logger = Logger.getLogger(FabricaConexao.class);
	
	public static Connection getConexao() throws SQLException {
		if(conexao == null || conexao.isClosed()){
			carregaBD();			
		}			
		return conexao;		
	}	
	
	private static void carregaBD() throws SQLException {
		ResourceBundle rb = ResourceBundle.getBundle("banco");
		driver = rb.getString("driver");
		url = rb.getString("url");
		caminhoBD = rb.getString("caminho");
		user = rb.getString("user");
		password = rb.getString("password");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(Mensagem.ERRO_BANCO_DADOS, e);
		}
		conexao = DriverManager.getConnection(url + caminhoBD+rb.getString("shutdown"), user, password);
	}
	
	public static void close(){
		if(conexao != null){
			try {
				conexao.close();
			} catch (SQLException e) {
				logger.error(Mensagem.ERRO_BANCO_DADOS, e);
			}finally{
				conexao=null;
			}
		}
	}
}
