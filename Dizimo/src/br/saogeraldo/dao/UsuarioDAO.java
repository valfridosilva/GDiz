package br.saogeraldo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.saogeraldo.bean.UsuarioVO;
import br.saogeraldo.util.FabricaConexao;

public class UsuarioDAO {
	private Connection con;
	private PreparedStatement ps;
	/**
	 * Verifica se o usuário está cadastrado no banco
	 * @param UsuarioVO
	 * @return UsuarioVO
	 * @throws SQLException
	 */	 
	public UsuarioVO getLogin(String usuario, String senha)throws SQLException{				
		con = FabricaConexao.getConexao();
		String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, usuario);
		ps.setString(2, senha);
		ResultSet rs = ps.executeQuery();
		UsuarioVO u = null;
		if (rs.next()){
			u = new UsuarioVO();
			u.setIdUsuario(rs.getInt("IDUSUARIO"));
			u.setNome(rs.getString("NOME"));			
		}
		ps.close();
		
		return u;
	}		
	
	public void insert(UsuarioVO user) throws SQLException {		
		con = FabricaConexao.getConexao();				
		String sql = "INSERT INTO usuario(nome, senha) VALUES(?,?)";
		ps = con.prepareStatement(sql);
		ps.setString(1, user.getNome());		
		ps.setString(2, user.getSenha());		
		ps.execute();
		ps.close();
	}	
	
	public void update(UsuarioVO user) throws SQLException {		
		con = FabricaConexao.getConexao();		
		String sql = "UPDATE usuario SET nome = ?, senha = ? WHERE idUsuario = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, user.getNome());				
		ps.setString(2, user.getSenha());				
		ps.setInt(3, user.getIdUsuario());

		ps.executeUpdate();
		ps.close();
	}	
	
	public void delete(int id) throws SQLException {
		con = FabricaConexao.getConexao();
		String sql = "DELETE FROM usuario WHERE idUsuario = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);

		ps.execute();
		ps.close();
	}
	
	public List<UsuarioVO> getUsuarioByName(String nome) throws SQLException {
		con = FabricaConexao.getConexao();
		List<UsuarioVO> lista = new ArrayList<UsuarioVO>();
		String sql = "SELECT * FROM usuario WHERE nome LIKE ? and idUsuario <> 1";
		ps = con.prepareStatement(sql);
		ps.setString(1, nome+"%");
		UsuarioVO user = null;		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){		
			user = new UsuarioVO();
			user.setIdUsuario(rs.getInt("idUsuario"));
			user.setNome(rs.getString("nome"));			
			user.setSenha(rs.getString("senha"));	
			lista.add(user);
		}
		ps.close();
		return lista;		
	}
	
	public boolean existeNome(String nome) throws SQLException {
		boolean status = false;
		con = FabricaConexao.getConexao();
		String sql = "SELECT idUsuario FROM usuario WHERE nome = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, nome);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			status = true;
		}
		ps.close();
		return status;
	}
}
