package br.saogeraldo.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.util.FabricaConexao;

public class DizimistaDAO {
	private Connection con;
	private PreparedStatement ps;
	/**  
	 * Insere novo Dizimista
	 * @param DizimistaVO
	 * @throws SQLException
	 */
	public void insert(DizimistaVO dz) throws SQLException {		
		con = FabricaConexao.getConexao();				
		String sql = "INSERT INTO dizimista(idDizimista, nome, dtNascimento, endereco, telefone, dtCasamento, nomeConjuge, idConjugeDizimista, falecido) VALUES(?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql);
		int cont = 1;
		ps.setInt(cont++, dz.getIdDizimista());		
		ps.setString(cont++, dz.getNome());		
		ps.setDate(cont++, new java.sql.Date(dz.getDtNascimento().getTime()));
		ps.setString(cont++, dz.getEndereco());
		ps.setString(cont++, dz.getTelefone());
		if(dz.getDtCasamento() == null){
			ps.setNull(cont++, java.sql.Types.DATE);
		}else{
			ps.setDate(cont++, new java.sql.Date(dz.getDtCasamento().getTime()));
		}
		ps.setString(cont++, dz.getNomeConjuge());
		if(dz.getIdConjugeDizimista() == null){
			ps.setNull(cont++, java.sql.Types.INTEGER);
		}else{
			ps.setInt(cont++, dz.getIdConjugeDizimista());
		}
		ps.setBoolean(cont++, dz.isFalecido());

		ps.execute();
		ps.close();
	}	
	/**
	 * Atualiza os dados do Dizimista
	 * @param DizimistaVO
	 * @throws SQLException
	 */
	public void update(DizimistaVO dz) throws SQLException {		
		con = FabricaConexao.getConexao();		
		String sql = "UPDATE dizimista SET nome = ?, dtNascimento = ?, endereco = ?, telefone = ?, dtCasamento = ?, nomeConjuge = ?, idConjugeDizimista = ?, falecido = ? WHERE idDizimista = ?";
		ps = con.prepareStatement(sql);
		int cont = 1;
		ps.setString(cont++, dz.getNome());				
		ps.setDate(cont++, new java.sql.Date(dz.getDtNascimento().getTime()));
		ps.setString(cont++, dz.getEndereco());		
		ps.setString(cont++, dz.getTelefone());
		if(dz.getDtCasamento() == null){
			ps.setNull(cont++, java.sql.Types.DATE);
		}else{
			ps.setDate(cont++, new java.sql.Date(dz.getDtCasamento().getTime()));
		}
		ps.setString(cont++, dz.getNomeConjuge());	
		if(dz.getIdConjugeDizimista() == null){
			ps.setNull(cont++, java.sql.Types.INTEGER);
		}else{
			ps.setInt(cont++, dz.getIdConjugeDizimista());
		}
		ps.setBoolean(cont++, dz.isFalecido());
		
		ps.setInt(cont++, dz.getIdDizimista());

		ps.executeUpdate();
		ps.close();
	}		
	/**
	 * Deleta o Dizimista
	 * @param int idDizimista
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		con = FabricaConexao.getConexao();
		String sql = "DELETE FROM dizimista WHERE idDizimista = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);

		ps.execute();
		ps.close();
	}
	/**	 
	 * Consulta um Dizimista pelo código	 
	 * @param idDizimista		  
	 * @return DizimistaVO
	 * @throws SQLException
	 */
	public DizimistaVO getDizimistaByCodigo(int id) throws SQLException {
		con = FabricaConexao.getConexao();
		String sql = "SELECT d.*, c.nome as nomeConjugeDizimista FROM dizimista d left join dizimista c on d.idconjugedizimista = c.iddizimista WHERE idDizimista = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		DizimistaVO dz = null;		
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			dz = new DizimistaVO();						
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setFalecido(rs.getBoolean("falecido"));
			dz.setNome(rs.getString("nome"));			
			dz.setDtNascimento(rs.getDate("dtNascimento"));
			dz.setEndereco(rs.getString("endereco"));
			dz.setTelefone(rs.getString("telefone"));
			dz.setDtCasamento(rs.getDate("dtCasamento"));			
			dz.setNomeConjuge(rs.getString("nomeConjugeDizimista"));	
			if(rs.wasNull()){
				dz.setNomeConjuge(rs.getString("nomeConjuge"));
			}
			dz.setIdConjugeDizimista(rs.getInt("idConjugeDizimista"));
			if(rs.wasNull()){
				dz.setIdConjugeDizimista(null);
			}
		}
		ps.close();
		return dz;		
	}
	/**	 
	 * Consulta um Dizimista por nome e retorna todas 
	 * os Dizimistas com nomes relacionados
	 * @param Nome ou parte do nome do Dizimista		  
	 * @return DizimistaVO
	 * @throws SQLException
	 */
	public List<DizimistaVO> getDizimistaByName(String nome) throws SQLException {
		con = FabricaConexao.getConexao();
		List<DizimistaVO> lista = new ArrayList<DizimistaVO>();
		String sql = "SELECT d.*, c.nome as nomeConjugeDizimista FROM dizimista d left join dizimista c on d.idconjugedizimista = c.iddizimista WHERE nome LIKE ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, nome+"%");
		DizimistaVO dz = null;		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){		
			dz = new DizimistaVO();
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setFalecido(rs.getBoolean("falecido"));
			dz.setNome(rs.getString("nome"));			
			dz.setDtNascimento(rs.getDate("dtNascimento"));
			dz.setEndereco(rs.getString("endereco"));
			dz.setTelefone(rs.getString("telefone"));			
			dz.setDtCasamento(rs.getDate("dtCasamento"));	
			dz.setNomeConjuge(rs.getString("nomeConjugeDizimista"));	
			if(rs.wasNull()){
				dz.setNomeConjuge(rs.getString("nomeConjuge"));
			}	
			dz.setIdConjugeDizimista(rs.getInt("idConjugeDizimista"));
			if(rs.wasNull()){
				dz.setIdConjugeDizimista(null);
			}
			lista.add(dz);
		}
		ps.close();
		return lista;		
	}
	
	/**
	 * Consulta todos os Aniversariantes
	 * @param dtInicio
	 * @param dtFim
	 * @return
	 * @throws SQLException
	 */
	public List<DizimistaVO> getAniversarioByDay(String dtInicio, String dtFim) throws SQLException {
		con = FabricaConexao.getConexao();
		List<DizimistaVO> lista = new ArrayList<DizimistaVO>();
		String sql = "SELECT idDizimista, nome, dtNascimento, telefone FROM dizimista where falecido = false AND SUBSTRING(dtNascimento,6) BETWEEN ? AND ? ORDER BY SUBSTRING(dtNascimento,6)";
		ps = con.prepareStatement(sql);
		ps.setString(1, dtInicio);
		ps.setString(2, dtFim);	
		DizimistaVO dz = null;		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){		
			dz = new DizimistaVO();	
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setNome(rs.getString("nome"));	
			dz.setTelefone(rs.getString("telefone"));
			dz.setDtNascimento(rs.getDate("dtNascimento"));			
			lista.add(dz);
		}
		ps.close();
		return lista;
	}
	/**
	 * Consulta todos os Aniversariantes de Casamento
	 * @param dtInicio
	 * @param dtFim
	 * @return
	 * @throws SQLException
	 */
	public List<DizimistaVO> getCasamentoByDay(String dtInicio, String dtFim) throws SQLException {
		con = FabricaConexao.getConexao();
		List<DizimistaVO> lista = new ArrayList<DizimistaVO>();
		String sql = "SELECT d.iddizimista, d.nome, d.dtCasamento, d.nomeConjuge, d.idConjugeDizimista, c.nome as nomeConjugeDizimista FROM dizimista d left join dizimista c on d.idconjugedizimista = c.iddizimista "+
				"WHERE d.falecido = false AND d.dtCasamento IS NOT NULL AND SUBSTRING(d.dtCasamento,6) BETWEEN ? AND ? ORDER BY SUBSTRING(d.dtCasamento,6)";
		ps = con.prepareStatement(sql);
		ps.setString(1, dtInicio);
		ps.setString(2, dtFim);		
		DizimistaVO dz = null;		
		ResultSet rs = ps.executeQuery();
		HashMap<Integer, DizimistaVO> mapa = new HashMap<Integer, DizimistaVO>();
		while(rs.next()){		
			dz = new DizimistaVO();	
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setNome(rs.getString("nome"));	
			dz.setDtCasamento(rs.getDate("dtCasamento"));				
			dz.setNomeConjuge(rs.getString("nomeConjugeDizimista"));	
			if(rs.wasNull()){
				dz.setNomeConjuge(rs.getString("nomeConjuge"));
			}
			dz.setIdConjugeDizimista(rs.getInt("idConjugeDizimista"));
			if(rs.wasNull()){
				dz.setIdConjugeDizimista(null);
			}
			if(!mapa.containsKey(dz.getIdDizimista())){
				if(dz.getIdConjugeDizimista()!= null){
					mapa.put(dz.getIdConjugeDizimista(), dz);
				}else{
					mapa.put(dz.getIdDizimista(), dz);
				}
			}
		}
		ps.close();
		lista.addAll(mapa.values());		
		return lista;
	}
	
	/**
	 * Lista todos os dizimistas cadastrados
	 * @return List
	 * @throws SQLException
	 */
	public List<DizimistaVO> getDizimistaAll() throws SQLException{
		con = FabricaConexao.getConexao();
		String sql = "SELECT d.*, c.nome as nomeConjugeDizimista FROM dizimista d left join dizimista c on d.idconjugedizimista = c.iddizimista ORDER BY d.nome";
		ps = con.prepareStatement(sql);		
		List<DizimistaVO> todos = new ArrayList<DizimistaVO>();		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			DizimistaVO dz = new DizimistaVO();		
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setFalecido(rs.getBoolean("falecido"));
			dz.setNome(rs.getString("nome"));			
			dz.setDtNascimento(rs.getDate("dtNascimento"));
			dz.setEndereco(rs.getString("endereco"));
			dz.setTelefone(rs.getString("telefone"));
			dz.setDtCasamento(rs.getDate("dtCasamento"));		
			dz.setNomeConjuge(rs.getString("nomeConjugeDizimista"));	
			if(rs.wasNull()){
				dz.setNomeConjuge(rs.getString("nomeConjuge"));	
			}
			dz.setIdConjugeDizimista(rs.getInt("idConjugeDizimista"));
			if(rs.wasNull()){
				dz.setIdConjugeDizimista(null);
			}
			todos.add(dz);
		}
		ps.close();
		return todos;		
		
	}
/**
 * Verifica se já existe algum Dizimista cadastrado com este código
 * @param codigo
 * @return boolean
 * @throws SQLException
 */
	public boolean existeCodigo(int codigo) throws SQLException {
		boolean status = false;
		con = FabricaConexao.getConexao();
		String sql = "SELECT idDizimista FROM dizimista WHERE idDizimista = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			status = true;
		}
		ps.close();
		return status;
	}
	/**
	 * Recupera todos os dizimistas falecidos
	 * @return
	 */
	public List<DizimistaVO> getDizimistaFalecidoAll() throws SQLException {
		con = FabricaConexao.getConexao();
		List<DizimistaVO> lista = new ArrayList<DizimistaVO>();
		String sql = "SELECT * FROM dizimista WHERE falecido = true ORDER BY nome";
		ps = con.prepareStatement(sql);
		DizimistaVO dz = null;		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){		
			dz = new DizimistaVO();	
			dz.setIdDizimista(rs.getInt("idDizimista"));
			dz.setNome(rs.getString("nome"));	
			lista.add(dz);		
		}
		ps.close();
		return lista;
	}

}
