package br.saogeraldo.util;

public enum TipoPesquisa {

	CASAMENTO("Casamento"),ANIVERSARIO("Anivers�rio");
	
	private TipoPesquisa(String descricao){
		this.descricao = descricao;
	}
	
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
}
