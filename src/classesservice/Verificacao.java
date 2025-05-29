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
            }// Teste completo
        }
        return true; 
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        str = str.trim().toLowerCase(); // remove espaços e padroniza para minúsculo
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
