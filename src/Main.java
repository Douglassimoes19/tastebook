import classesmodel.Usuario;
import classesutil.*;
import classesdao.UsuarioDao;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        Connection conn = null;
        try {
            conn = Dbutil.getConnection();
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Conexão bem-sucedida com o banco de dados!");
            } else {
                JOptionPane.showMessageDialog(null,"Falha na conexão com o banco de dados.");
            }
        }  finally {
            Dbutil.desconectar(conn);
        }

        List<String> array = new ArrayList<String>();
        array.add("carne");
        array.add("leite");
        array.add("morango");

        //Usuario usuario1 = new Usuario("douglas", "Douglas@douglas",array);
        //System.out.println(usuario1.toString());
        UsuarioDao dao = new UsuarioDao();
        //dao.cadastrarUsuario(usuario1);
        List<Usuario> lista = dao.listarUsuarios();
        Usuario aleteraUser = dao.buscarUsuario(1);

        System.out.println(lista);

    }

}