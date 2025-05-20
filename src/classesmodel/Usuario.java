package classesmodel;

import java.util.List;


public class Usuario {
   private int id ;
   private String nome;
   private String nomeUsuario;
   private String email;
   private List<String> alergias;
   private String senha;

	public Usuario(String nome, String nomeUsuario, String email, List<String> alergias, String senha) {
		super();
		this.nome = nome;
		this.nomeUsuario = nomeUsuario;
		this.email = email;
		this.alergias = alergias;
		this.senha = senha;
	}

	public Usuario() {
		super();
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAlergias() {

		return String.join(", ", alergias);
	}
	public void setAlergias(List<String> alergias) {
		this.alergias = alergias;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", nomeUsuario='" + nomeUsuario + '\'' +
				", email='" + email + '\'' +
				", alergias=" + alergias + '\''+
				", senha='" + senha  +
				'}';
	}
}
