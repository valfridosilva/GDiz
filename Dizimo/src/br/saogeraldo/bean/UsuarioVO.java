package br.saogeraldo.bean;

public class UsuarioVO {
	private int idUsuario;
	private String nome;
	private String senha;
	
	public UsuarioVO(int idUsuario, String nome) {
		this.idUsuario = idUsuario;
		this.nome = nome;
	}
	public UsuarioVO() {
	
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
