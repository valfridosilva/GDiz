package br.saogeraldo.util;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import br.saogeraldo.bean.DizimistaVO;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

public class RelatorioUtil{

	public void runRelatorio(String path, Collection<DizimistaVO> lista, Map<String, String> param) throws RelatorioException {
	    try {
	        InputStream out = getClass().getClassLoader().getResourceAsStream(path);
	        
	        if (out == null) {
	            throw new RuntimeException("Relatório não encontrado: " + path);
	        }

	        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);    
	        final JasperPrint jp = JasperFillManager.fillReport(out, param, ds);
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                MyViewer myViewer = new MyViewer(jp);
	                myViewer.setVisible(true);
	            }
	        });

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

class MyViewer extends JasperViewer {
	 
	private static final long serialVersionUID = 1L;

	public MyViewer(JasperPrint jasperPrint) {
	    super(jasperPrint, false);
	    Locale locale = viewer.getLocale();
	    ResourceBundle resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", locale);
	 
	    JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resourceBundle);
	    viewer.setSaveContributors(new JRSaveContributor[]{pdf});
	  }
	}