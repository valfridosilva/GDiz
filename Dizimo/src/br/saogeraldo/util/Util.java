package br.saogeraldo.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util { 
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static DecimalFormat df = new DecimalFormat("#,###.00"); 
	
	static{
		sdf.applyPattern("dd/MM/yyyy");
		sdf.setLenient(false);
	}

	/*
	 * Converte o Dia/Mes BR para Mes/Dia USA
	 */
	public static String converteDayMonthBRtoUSA(String data){
		String res = "";
		if(!data.equals("") && data.length() >1){			
			String datas[] = data.split("/");
			String dia = datas[0];
			String mes = datas[1];			
			
			res = mes+"-"+dia;			
		}
		
		return res;
	}

	public static Date convertStringToDate(String data) throws ParseException {
		if(data == null || data.isEmpty()){
			return null;
		}
		
		return sdf.parse(data);
	}	
	
	public static String convertDateToString(Date data) {
		if(data == null){
			return "";
		}
		return sdf.format(data);
	}
	
	public static String convertValor(Double value) {
		if(value == null){
			return "";
		}
		return df.format(value);
	}
}
