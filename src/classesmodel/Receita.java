package classesmodel;

import java.util.ArrayList;
import java.util.List;

public class Receita {
	
    private int id;
    private String titulo;
    private String categoria;
    private List<Ingrediente> ingredientes;
    private String modoPreparo;
    private String tempoPreparo;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Receita(String titulo, String categoria, List<Ingrediente> ingredientes, String modoPreparo, String tempoPreparo, Usuario usuario) {
		super();
		this.titulo = titulo;
		this.categoria = categoria;
		this.ingredientes = ingredientes;
		this.modoPreparo = modoPreparo;
		this.tempoPreparo = tempoPreparo;
        this.usuario = usuario;
    }
	public Receita() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Ingrediente> getIngredientes() {

		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public String getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(String tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public List<String> FormatarIngredientes(List<Ingrediente> ingredientes) {
		List<String> ingredientesFormat = new ArrayList<>();
		for(Ingrediente ing : this.ingredientes){
			ingredientesFormat.add(ing.getNome());
		}
		return ingredientesFormat;
	}

	@Override
	public String toString() {
		return "Receita{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", categoria='" + categoria + '\'' +
				", ingredientes=" + FormatarIngredientes(ingredientes) +
				", modoPreparo='" + modoPreparo + '\'' +
				", tempoPreparo='" + tempoPreparo + '\'' +
				", usuario=" + usuario.getNome() +
				'}';
	}
}
