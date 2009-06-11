package br.saogeraldo.negocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.saogeraldo.util.Data;
import br.saogeraldo.util.Relatorio;
import br.saogeraldo.dao.DizimistaDAO;
import br.saogeraldo.bean.DizimistaVO;
 
public class RelatorioBO{
	private List<DizimistaVO> dizimistas;
	private String path;
	Map<String, String> param;
		
	
	public boolean runAniversario(String dtInicio, String dtFim)throws Exception{
		boolean flag = false;	
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);
		String mesDia1 = Data.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = Data.converteDayMonthBRtoUSA(dtFim);
		dizimistas = new DizimistaDAO().getAniversarioByDay(mesDia1, mesDia2);
		if(dizimistas.size()> 0){
			flag = true;
			path = "C:/Dizimo/Rel_Aniversario.jasper";
			new Relatorio().runRelatorio(path, dizimistas, param);
		}		
		return flag;
	}
	
	public boolean runCasamento(String dtInicio, String dtFim)throws Exception{
		boolean flag = false;	
		param = new HashMap<String, String>();
		param.put("title", dtInicio+" - "+dtFim);		
		String mesDia1 = Data.converteDayMonthBRtoUSA(dtInicio);
		String mesDia2 = Data.converteDayMonthBRtoUSA(dtFim);
		dizimistas = new DizimistaDAO().getCasamentoByDay(mesDia1, mesDia2);
		if(dizimistas.size()> 0){
			flag = true;
			path = "C:/Dizimo/Rel_Casamento.jasper";
			new Relatorio().runRelatorio(path, dizimistas, param);
		}		
		return flag;
	}
	
	public boolean runAniversarioAll()throws Exception{
		boolean flag = false;
		param = new HashMap<String, String>();
		param.put("title", "Todos");
		dizimistas = new DizimistaDAO().getDizimistaAll();
		if(dizimistas.size()> 0){
			flag = true;
			path = "C:/Dizimo/Rel_Aniversario.jasper";
			new Relatorio().runRelatorio(path, dizimistas, param);
		}		
		return flag;
	}	
	 
	public boolean runRecadastramento()throws Exception{
		boolean flag = false;
		param = new HashMap<String, String>();		
		dizimistas = new DizimistaDAO().getDizimistaAll();
		if(dizimistas.size()> 0){
			flag = true;
			path = "C:/Dizimo/Rel_Recadastramento.jasper";
			new Relatorio().runRelatorio(path, dizimistas, param);
		}		
		return flag;
	}	
	
}