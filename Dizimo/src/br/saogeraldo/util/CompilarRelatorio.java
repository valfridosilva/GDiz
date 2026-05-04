package br.saogeraldo.util;


import java.io.InputStream;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

public class CompilarRelatorio {

    public static void main(String[] args) {
        try {
        	String nomeRelatorio = "Rel_Registro_status";
        	
            InputStream jrxmlStream = CompilarRelatorio.class
                .getClassLoader()
                .getResourceAsStream("relatorios/"+nomeRelatorio+".jrxml");

            if (jrxmlStream == null) {
                throw new RuntimeException("Arquivo não encontrado no classpath!");
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            String basePath = System.getProperty("user.dir");
            String output = basePath + "/resources/relatorios/"+nomeRelatorio+".jasper";
            JRSaver.saveObject(jasperReport, output);

            System.out.println("Relatório "+nomeRelatorio+" compilado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}