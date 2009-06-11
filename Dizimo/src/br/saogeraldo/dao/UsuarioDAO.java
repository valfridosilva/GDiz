package br.saogeraldo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.util.FabricaConexao;

public class UsuarioDAO {
	/**
	 * Verifica se o usuário está cadastrado no banco
	 * @param UsuarioVO
	 * @return UsuarioVO
	 * @throws SQLException
	 */	 
	public UsuarioVO getLogin(UsuarioVO usuario)throws SQLException{				
		Connection con = FabricaConexao.getConexao();
		String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getSenha());
		ResultSet rs = stmt.executeQuery();
		UsuarioVO u = null;
		if (rs.next()){
			u = new UsuarioVO();
			u.setIdUsuario(rs.getInt("IDUSUARIO"));
			u.setNome(rs.getString("NOME"));			
		}
		stmt.close();
		
		return u;
	}		
}
