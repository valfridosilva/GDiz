package br.saogeraldo.negocio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.dao.FinanceiroDAO;
import br.saogeraldo.util.AnoMes;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;
import br.saogeraldo.util.RelatorioUtil;
import br.saogeraldo.util.Util;
 
public class RelatorioBO{
	private List<DizimistaVO> dizimistas;
	private Map<String, String> param;
		
	
	public void runAniversario(String dtInicio, String dtFim)throws RegraDeNegocioException, RelatorioException{
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);
		String mesDia1 = Util.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = Util.converteDayMonthBRtoUSA(dtFim);
		try{
			dizimistas = new DizimistaDAO().getAniversarioByDay(mesDia1, mesDia2);
			if(dizimistas.isEmpty()){
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
		} catch (SQLException e) {
			throw new RelatorioException(e);
		}
		new RelatorioUtil().runRelatorio("relatorios/Rel_Aniversario.jasper", dizimistas, param);
	}
	
	public void runCasamento(String dtInicio, String dtFim)throws RegraDeNegocioException, RelatorioException{
		String mesDia1 = Util.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = Util.converteDayMonthBRtoUSA(dtFim);
		try{
			dizimistas = new DizimistaDAO().getCasamentoByDay(mesDia1, mesDia2);
			if(dizimistas.isEmpty()){
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
		} catch (SQLException e) {
			throw new RelatorioException(e);
		}
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);		
		new RelatorioUtil().runRelatorio("relatorios/Rel_Casamento.jasper", dizimistas, param);
	}
	
	public void runAniversarioAll() throws RegraDeNegocioException, RelatorioException {
		try {
			dizimistas = new DizimistaDAO().getDizimistaAll();
			if (dizimistas.isEmpty()) {
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
		} catch (SQLException e) {
			throw new RelatorioException(e);
		}
		param = new HashMap<String, String>();
		param.put("title", "Todos");
		new RelatorioUtil().runRelatorio("relatorios/Rel_Aniversario.jasper", dizimistas, param);
	}
	
	public void runFinanceiro(AnoMes anoMes) throws RegraDeNegocioException, RelatorioException {
		try {
			List<String> totalPagamento = new FinanceiroDAO().getTotalPagamentoPorMes(anoMes);
			if (totalPagamento.isEmpty()) {
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
			param = new HashMap<String, String>();
			param.put("mesReferencia", anoMes.toString());
			param.put("total", totalPagamento.get(0));
			param.put("valor", totalPagamento.get(1));
			param.put("media", totalPagamento.get(2));
		} catch (SQLException e) {
			throw new RelatorioException(e);
		}
		new RelatorioUtil().runRelatorio("relatorios/Rel_Financeiro.jasper", param);
	}
	 
	public void runRecadastramento()throws RegraDeNegocioException, RelatorioException{
		param = new HashMap<String, String>();		
		try{
			dizimistas = new DizimistaDAO().getDizimistaAll();
			if(dizimistas.isEmpty()){
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
		}catch(SQLException e){
			throw new RelatorioException(e);
		}
		new RelatorioUtil().runRelatorio("relatorios/Rel_Recadastramento.jasper", dizimistas, param);
	}
	
	public void runFalecido()throws RegraDeNegocioException, RelatorioException{
		param = new HashMap<String, String>();		
		try{
			dizimistas = new DizimistaDAO().getDizimistaFalecidoAll();
			if(dizimistas.isEmpty()){
				throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
			}
		}catch(SQLException e){
			throw new RelatorioException(e);
		}
		new RelatorioUtil().runRelatorio("relatorios/Rel_Falecido.jasper", dizimistas, param);
	}
}