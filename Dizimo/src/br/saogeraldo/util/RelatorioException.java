package br.saogeraldo.util;
/**
 * 
 * Exce��o espec�fica lan�ada para erros de valida��o de campos
 */
public class RelatorioException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RelatorioException(){
		super();
	}

	public RelatorioException(String message){
		super(message);
	}
	public RelatorioException(Throwable cause){
		super(cause);
	}
	public RelatorioException(String message, Throwable cause){
		super(message, cause);
	}
	

}
