package classesservice;

import java.util.List;

public class Verificacao {

	public boolean verificacaoNome(String nome) {
		return nome.matches("[\\p{L}]+");
	}
	
	public boolean emailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
	
	public boolean verificaLista(List<String> lista) {
        for (String item : lista) {
            if (!item.matches("[\\p{L}]+")) {
                return false;
            }// testando refresh
        }
        return true; 
    }
}
