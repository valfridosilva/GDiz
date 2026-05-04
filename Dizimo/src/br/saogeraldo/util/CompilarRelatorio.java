package br.saogeraldo.util;


import java.io.File;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

public class CompilarRelatorio {

    public static void main(String[] args) {
        try {
        	File dir = new File("resources/relatorios");

        	if (!dir.exists() || !dir.isDirectory()) {
        	    throw new RuntimeException("Diretório não encontrado: " + dir.getAbsolutePath());
        	}
        	for (File file : dir.listFiles()) {
        	    if (file.getName().endsWith(".jrxml")) {
        	        String input = file.getPath();
        	        String output = input.replace(".jrxml", ".jasper");

        	        JasperReport jasperReport = JasperCompileManager.compileReport(input);
        	        JRSaver.saveObject(jasperReport, output);

        	        System.out.println("Compilado: " + output);
        	    }
        	}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}