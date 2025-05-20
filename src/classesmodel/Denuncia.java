package classesmodel;

import java.util.Date;

public class Denuncia {
	
	private int id;
    private String motivo;
    private Usuario usuario;
    private Date data;

    public Denuncia(int id, String motivo, Usuario usuario, Date data) {
		super();
		this.id = id;
		this.motivo = motivo;
		this.usuario = usuario;
		this.data = data;
	}

	public Denuncia() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
    
    
}
