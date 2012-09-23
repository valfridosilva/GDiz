package br.saogeraldo.util;

import br.saogeraldo.bean.DizimistaVO;
import java.util.Collection;
import java.util.Map;
import java.io.FileInputStream;
import java.io.InputStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.view.*;

public class RelatorioUtil{

	public void runRelatorio(String path, Collection<DizimistaVO> lista, Map<String, String> param) throws Exception {
		
		InputStream out = new FileInputStream(path);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);			
		JasperPrint jp = JasperFillManager.fillReport(out, param, ds);        
		JasperViewer.viewReport(jp, false);			 
	}	
	
}