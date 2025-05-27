package classesviewr;

import classesdao.UsuarioDao;
import classesmodel.Usuario;

import java.util.Arrays;
import java.util.Scanner;

public class TelaUsuario {

    public static void exibirTelaCadastro() {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario();
        UsuarioDao dao = new UsuarioDao();

        System.out.println("\n--- Tela de Cadastro ---");
        System.out.print("Digite seu nome: ");
        usuario.setNome(scanner.nextLine());

        System.out.print("Digite seu email: ");
        usuario.setEmail(scanner.nextLine());

        System.out.print("Digite suas alergias (separadas por vírgula): ");
        String alergias = scanner.nextLine();
        usuario.setAlergias(Arrays.asList(alergias.split(",")));

        boolean cadastrado = dao.cadastrarUsuario(usuario);

        if (cadastrado) {
            System.out.println("Cadastro realizado com sucesso!");
            TelaUsuarioLogado.exibirMenuUsuario(usuario);
        } else {
            System.out.println("Erro ao cadastrar. Você pode entrar como visitante.");
            TelaVisitante.exibirMenuVisitante();
        }
    }

    public static void exibirTelaLogin() {
        Scanner scanner = new Scanner(System.in);
//        UsuarioDao dao = new UsuarioDao();

        System.out.println("\n--- Tela de Login ---");
        System.out.print("Digite seu ID de usuário: ");
        int id = scanner.nextInt();
        scanner.nextLine();

//        Usuario usuario = dao.buscarUsuario(id);
//        if (usuario != null) {
//            System.out.println("Login realizado com sucesso!");
//            TelaUsuarioLogado.exibirMenuUsuario(usuario);
//        } else {
//            System.out.println("Usuário não encontrado. Tente novamente ou entre como visitante.");
//            TelaVisitante.exibirMenuVisitante();
//        }
    }
}
