package br.saogeraldo.bean;

import java.util.Date;

public class DizimistaVO {
	private int idDizimista;
	private String nome;
	private Date dtNascimento;
	private String endereco;
	private String telefone;
	private Date dtCasamento;
	private String nomeConjuge;
	private Integer idConjugeDizimista;

	public int getIdDizimista() {
		return idDizimista;
	}

	public void setIdDizimista(int idDizimista) {
		this.idDizimista = idDizimista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Date getDtCasamento() {
		return dtCasamento;
	}

	public void setDtCasamento(Date dataCasamento) {
		this.dtCasamento = dataCasamento;
	}
	
	public Integer getIdConjugeDizimista() {
		return idConjugeDizimista;
	}
	
	public void setIdConjugeDizimista(Integer idConjugeDizimista) {
		this.idConjugeDizimista = idConjugeDizimista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDizimista;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DizimistaVO other = (DizimistaVO) obj;
		if (idDizimista != other.idDizimista)
			return false;
		return true;
	}

}
