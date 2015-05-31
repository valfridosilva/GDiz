package br.saogeraldo.negocio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.saogeraldo.bean.DizimistaVO;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.util.DataUtil;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.RelatorioException;
import br.saogeraldo.util.RelatorioUtil;
 
public class RelatorioBO{
	private List<DizimistaVO> dizimistas;
	private String path;
	private Map<String, String> param;
		
	
	public void runAniversario(String dtInicio, String dtFim)throws RegraDeNegocioException, SQLException, RelatorioException{
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);
		String mesDia1 = DataUtil.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = DataUtil.converteDayMonthBRtoUSA(dtFim);
		dizimistas = new DizimistaDAO().getAniversarioByDay(mesDia1, mesDia2);
		if(dizimistas.isEmpty()){
			throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
		}
		path = getClass().getClassLoader().getResource("relatorios/Rel_Aniversario.jasper").getPath();
		new RelatorioUtil().runRelatorio(path, dizimistas, param);
	}
	
	public void runCasamento(String dtInicio, String dtFim)throws RegraDeNegocioException, SQLException, RelatorioException{
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);		
		String mesDia1 = DataUtil.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = DataUtil.converteDayMonthBRtoUSA(dtFim);
		dizimistas = new DizimistaDAO().getCasamentoByDay(mesDia1, mesDia2);
		if(dizimistas.isEmpty()){
			throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
		}
		path = getClass().getClassLoader().getResource("relatorios/Rel_Casamento.jasper").getPath();
		new RelatorioUtil().runRelatorio(path, dizimistas, param);
	}
	
	public void runAniversarioAll()throws RegraDeNegocioException, SQLException, RelatorioException{
		param = new HashMap<String, String>();
		param.put("title", "Todos");
		dizimistas = new DizimistaDAO().getDizimistaAll();
		if(dizimistas.isEmpty()){
			throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
		}
		path = getClass().getClassLoader().getResource("relatorios/Rel_Aniversario.jasper").getPath();
		new RelatorioUtil().runRelatorio(path, dizimistas, param);
	}	
	 
	public void runRecadastramento()throws RegraDeNegocioException, SQLException, RelatorioException{
		param = new HashMap<String, String>();		
		dizimistas = new DizimistaDAO().getDizimistaAll();
		if(dizimistas.isEmpty()){
			throw new RegraDeNegocioException(Mensagem.NENHUM_REGISTRO);
		}
		path = getClass().getClassLoader().getResource("relatorios/Rel_Recadastramento.jasper").getPath();
		new RelatorioUtil().runRelatorio(path, dizimistas, param);
	}	
}