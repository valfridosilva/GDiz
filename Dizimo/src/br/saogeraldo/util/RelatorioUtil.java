package br.saogeraldo.util;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import br.saogeraldo.bean.DizimistaVO;

public class RelatorioUtil{

	public void runRelatorio(String path, Collection<DizimistaVO> lista, Map<String, String> param) throws RelatorioException {
		try {
			InputStream out = getClass().getClassLoader().getResourceAsStream(path);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);	
			JasperPrint jp = JasperFillManager.fillReport(out, param, ds);
			JasperViewer.viewReport(jp, false);
		} catch (Exception e) {
			throw new RelatorioException(e);
		}	
	}	
	
	public void runRelatorio(String path, Map<String, String> param) throws RelatorioException {
		try {
			InputStream out = getClass().getClassLoader().getResourceAsStream(path);
			JasperPrint jp = JasperFillManager.fillReport(out, param);
			JasperViewer.viewReport(jp, false);
		} catch (Exception e) {
			throw new RelatorioException(e);
		}	
	}	
	
}