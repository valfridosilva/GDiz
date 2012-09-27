package br.saogeraldo.util;
/**
 * 
 * Exceção específica lançada para erros de validação de campos
 */
public class RegraDeNegocioException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RegraDeNegocioException(){
		super();
	}

	public RegraDeNegocioException(String message){
		super(message);
	}
	public RegraDeNegocioException(Throwable cause){
		super(cause);
	}
	public RegraDeNegocioException(String message, Throwable cause){
		super(message, cause);
	}
	

}
