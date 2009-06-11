package br.saogeraldo.util;

import java.sql.Date;

public class Data { 
	/*
	 * Transforma a data do padrão BR para o USA
	 */
	public static Date converteBRtoUSA(String data){
		Date d = null;
		if(data.equals("") || data.length() <1){
			d = null;
		}else{
			String datas[] = data.split("/");
			String dia = datas[0];
			String mes = datas[1];
			String ano = datas[2];			
			
			String res = ano+"-"+mes+"-"+dia;				
			d = Date.valueOf(res);			
		}
		
		return d;
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
	/*
	 * Transforma a data do padrão USA para BR.
	 * o parâmetro booleano diz se a data vai ter ou não a "/".
	 */
	public static String converteUSAtoBR(java.util.Date data, boolean barra){
		String res = null;
		if(data != null){
			String datas[] = data.toString().split("-");
			String dia = datas[2];
			String mes = datas[1];
			String ano = datas[0];	
			
			if(barra){
				res = dia+"/"+mes+"/"+ano;	
			}else{
				res = dia+mes+ano;	
			}
		}else{
			res = "";
		}		
		return res;
	}	
}
