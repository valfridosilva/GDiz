package br.saogeraldo.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import br.saogeraldo.bean.FinanceiroVO;
import br.saogeraldo.util.AnoMes;
import br.saogeraldo.util.FabricaConexao;
import br.saogeraldo.util.Util;

public class FinanceiroDAO {
	private Connection con;
	private PreparedStatement ps;
	/**  
	 * Insere novo pagamento
	 * @param FinanceiroVO
	 * @throws SQLException
	 */
	public void insert(FinanceiroVO financeiro) throws SQLException {		
		con = FabricaConexao.getConexao();				
		String sql = "INSERT INTO financeiro(idDizimista, valor, dtLancamento, anoMesreferencia) VALUES(?,?,?,?)";
		ps = con.prepareStatement(sql);
		int cont = 1;
		ps.setInt(cont++, financeiro.getIdDizimista());		
		ps.setDouble(cont++, financeiro.getValor());		
		ps.setDate(cont++, new java.sql.Date(financeiro.getDataLancamento().getTime()));
		ps.setInt(cont++, financeiro.getAnoMesReferencia().getAnoMes());
		
		ps.execute();
		ps.close();
	}	
	/**
	 * Deleta o pagamento
	 * @param Set<Integer> idPagamento
	 * @throws SQLException
	 */
	public void delete(List<Integer> financeirosAExcluir) throws SQLException {
		con = FabricaConexao.getConexao();
		StringBuilder sql = new StringBuilder("DELETE FROM financeiro WHERE idFinanceiro IN (");
		for(int i=1; i<=financeirosAExcluir.size(); i++){
			sql.append(financeirosAExcluir.get(i-1));
			if(financeirosAExcluir.size()!=i){
				sql.append(",");
			}
		}
		sql.append(")");
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql.toString());
		stmt.close();
	}
	/**	 
	 * Consulta o 12 últimos pagamentos pelo código do dizimista	 
	 * @param idDizimista		  
	 * @return SortedSet<FinanceiroVO>
	 * @throws SQLException
	 */
	public SortedSet<FinanceiroVO> getLastPagamentoByCodigoDizimista(int idDizimista) throws SQLException {
		con = FabricaConexao.getConexao();
		String sql = "SELECT LIMIT 0 12 * FROM financeiro WHERE iddizimista = ? ";
		ps = con.prepareStatement(sql);
		ps.setInt(1, idDizimista);
		FinanceiroVO financeiro = null;		
		SortedSet<FinanceiroVO> financeiros = new TreeSet<FinanceiroVO>();
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			financeiro = new FinanceiroVO();	
			financeiro.setIdFinanceiro(rs.getInt("idFinanceiro"));
			financeiro.setIdDizimista(rs.getInt("idDizimista"));
			financeiro.setDataLancamento(rs.getDate("dtLancamento"));
			financeiro.setValor(rs.getDouble("valor"));
			financeiro.setAnoMesReferencia(new AnoMes(rs.getInt("anoMesReferencia")));
			financeiros.add(financeiro);
		}
		ps.close();
		return financeiros;		
	}
	
	public boolean existePagamento(int idDizimista, AnoMes anoMesReferencia) throws SQLException {
		boolean status = false;
		con = FabricaConexao.getConexao();
		String sql = "SELECT idDizimista FROM financeiro WHERE idDizimista = ? AND anoMesReferencia = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, idDizimista);
		ps.setInt(2, anoMesReferencia.getAnoMes());

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			status = true;
		}
		ps.close();
		return status;
	}
	public List<String> getTotalPagamentoPorMes(AnoMes anoMes) throws SQLException {
		con = FabricaConexao.getConexao();
		String sql = "SELECT count(*) as qtdDizimista, sum(valor) as totalRecebido, avg(valor) as mediaValor FROM financeiro WHERE anomesreferencia = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, anoMes.getAnoMes());
		ResultSet rs = ps.executeQuery();
		List<String> result = new ArrayList<String>();
		if(rs.next() && rs.getInt("qtdDizimista") != 0){		
			result.add(String.valueOf(rs.getInt("qtdDizimista")));
			result.add(Util.convertValor(rs.getDouble("totalRecebido")));
			result.add(Util.convertValor(rs.getDouble("mediaValor")));
		}
		ps.close();
		return result;
	}
	
}
