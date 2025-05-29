import classesmodel.*;
import classesutil.*;
import classesdao.*;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        // teste 
        Connection conn = null;
        try {
            conn = Dbutil.getConnection();
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Conexão bem-sucedida com o banco de dados!");
            } else {
                JOptionPane.showMessageDialog(null,"Falha na conexão com o banco de dados.");
            }
        }  finally {
            Dbutil.desconectar(conn);//adad
        }



        //Transferir essa parte comentada para service de criação de ingrediente ou receita :
        //List<Ingrediente> ingredientes = dao.listarIngredientes();
        //List<String> ingredientesBanco = new ArrayList<>();
        /*for (Ingrediente ingrediente : ingredientes) {
            ingredientesBanco.add(ingrediente.getNome().toUpperCase());
        }
        for (String ingrediente : array) {
            if (!ingredientesBanco.contains(ingrediente.toUpperCase())) {
                Ingrediente ing = new Ingrediente();
                ing.setNome(ingrediente);
                dao.criarIngrediente(ing);
                System.out.println("Ingrediente " + ingrediente + " criado com sucesso!");
            }
        }*/
        Receita receita1 = new Receita();
        receita1.setTitulo("Macarrão com queijo ");
        receita1.setCategoria("Massas italianas");
        List<String> array = new ArrayList<>();
        array.add("Spaghetti");
        array.add("Cebola");
        array.add("Queijo");
        array.add("Molho de tomate");

        UsuarioDao userDao = new UsuarioDao();

        Usuario user = userDao.buscarUsuario(1);
       // System.out.println(user.getNomeUsuario());

        /*List<Ingrediente> ingredientesReceita = new ArrayList<>();

        for (String ingrediente : array) {
            Ingrediente ing = new Ingrediente();
            ing.setNome(ingrediente);
            ingredientesReceita.add(ing);
        }
        receita1.setIngredientes(ingredientesReceita);
        receita1.setModoPreparo("Preparado com sucesso para teste de sistemas onde estamos com pouca fome ");
        receita1.setTempoPreparo("30 minutos");
        receita1.setUsuario(user);
        receita1.setId(4);*/


        ReceitaDao receitaDao = new ReceitaDao();
        Receita rec = receitaDao.buscarReceita(5);
        boolean recita = receitaDao.excluirReceita(rec);

        if (recita) {
            System.out.println("Deu bom o teste");
            //System.out.println(recita);
        }


        //receita1.setIngredientes();





    }

}
