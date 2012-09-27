package br.saogeraldo.util;
/**
 * 
 * Exce��o espec�fica lan�ada para erros de valida��o de campos
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
