package classes;

import java.io.Serializable;
import java.util.Arrays;

public class UsuarioAdmin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private char [] senha;
	private String nome;
	
	public UsuarioAdmin(String login, char [] senha, String nome){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public char[] getSenha() {
		return senha;
	}

	public void setSenha(char[] senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "UsuarioAdmin [login=" + login + ", senha=" + Arrays.toString(senha) + ", nome=" + nome + "]";
	}
	
	
	
}
