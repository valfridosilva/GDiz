package br.saogeraldo.util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataUtil { 
	public static final String PATTERN_DDMMYYYY = "dd/MM/yyyy";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	/*
	 * Transforma a data do padrão BR para o USA
	 */
//	public static Date converteBRtoUSA(String data){
//		if(data == null || data.trim().isEmpty() || data.length() <1){
//			return null;
//		}else{
//			String datas[] = data.split("/");
//			String dia = datas[0];
//			String mes = datas[1];
//			String ano = datas[2];			
//			
//			String res = ano+"-"+mes+"-"+dia;				
//			return Date.valueOf(res);			
//		}
//	}
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
	/*
	 * Transforma a data do padrão USA para BR.
	 * o parâmetro booleano diz se a data vai ter ou não a "/".
	 */
//	public static String converteUSAtoBR(java.util.Date data, boolean barra){
//		String res = null;
//		if(data != null){
//			String datas[] = data.toString().split("-");
//			String dia = datas[2];
//			String mes = datas[1];
//			String ano = datas[0];	
//			
//			if(barra){
//				res = dia+"/"+mes+"/"+ano;	
//			}else{
//				res = dia+mes+ano;	
//			}
//		}else{
//			res = "";
//		}		
//		return res;
//	}	
	
	public static Date convertStringToDate(String data, String pattern) throws ParseException {
		if(data == null || data.isEmpty()){
			return null;
		}
		sdf.applyPattern(pattern);
		sdf.setLenient(false);
		return sdf.parse(data);
	}	
	
	public static String convertDateToString(Date data, String pattern) throws ParseException {
		if(data == null){
			return "";
		}
		sdf.applyPattern(pattern); 
		sdf.setLenient(false);
		return sdf.format(data);
	}
}
