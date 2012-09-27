package br.saogeraldo.util;
/**
 * 
 * Exceção específica lançada para erros de validação de campos
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
