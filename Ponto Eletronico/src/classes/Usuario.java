package classes;


import java.io.Serializable;

public class Usuario implements Serializable{
	
	private String login;
	private char [] senha;
	private String nome;
	private String lotacao;
	
	private static final long serialVersionUID = 1L;
	
	public Usuario(String login, char [] senha, String nome, String lotacao){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.lotacao = lotacao;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public char [] getSenha() {
		return senha;
	}

	public void setSenha(char [] senha) {
		this.senha = senha;
	}
}
