package classesmodel;

public class Avaliacao {
	private int nota;
	private String comentario;
	private Usuario usuario;
	
	public Avaliacao(int nota, String comentario, Usuario usuario) {
		super();
		this.nota = nota;
		this.comentario = comentario;
		this.usuario = usuario;
	}

	public Avaliacao() {
		super();
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
