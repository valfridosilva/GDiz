package br.saogeraldo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FabricaConexao {	
	private static Connection conexao;
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	public static Connection getConexao() throws SQLException {
		if(conexao == null){
			ResourceBundle rb = ResourceBundle.getBundle("br.saogeraldo.util.banco");  
	        driver = rb.getString("driver");
	        url = rb.getString("url");
	        user = rb.getString("user"); 
	        password = rb.getString("password"); 
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conexao = DriverManager.getConnection(url, user, password);			
		}			
		return conexao;		
	}	
	
	public static void close(){
		if(conexao != null){
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
