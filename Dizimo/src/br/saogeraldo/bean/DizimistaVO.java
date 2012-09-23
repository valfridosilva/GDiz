package br.saogeraldo.bean;

import java.util.Date;

public class DizimistaVO {
	private int idDizimista;
	private String nome;
	private String nomeConjugue;
	private Date dtNascimento;
	private Date dtNascimentoConjugue;
	private String endereco;
	private String telefone;
	private Date dtCasamento;

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

	public String getNomeConjugue() {
		return nomeConjugue;
	}

	public void setNomeConjugue(String nomeConjugue) {
		this.nomeConjugue = nomeConjugue;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Date getDtNascimentoConjugue() {
		return dtNascimentoConjugue;
	}

	public void setDtNascimentoConjugue(Date dtNascimentoConjugue) {
		this.dtNascimentoConjugue = dtNascimentoConjugue;
	}

	public Date getDtCasamento() {
		return dtCasamento;
	}

	public void setDtCasamento(Date dataCasamento) {
		this.dtCasamento = dataCasamento;
	}

}
