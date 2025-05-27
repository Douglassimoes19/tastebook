package classesviewr;

import classesmodel.Usuario;

import java.util.Scanner;

public class TelaUsuarioLogado {

    public static void exibirMenuUsuario(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Bem-vindo(a), " + usuario.getNome() + " ---");
            System.out.println("1 - Ver receitas recomendadas");
            System.out.println("2 - Alterar dados da conta");
            System.out.println("3 - Fazer logout");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Exibindo receitas recomendadas...");
                    break;
                case 2:
                    System.out.println("Alterar dados ainda não implementado.");
                    break;
                case 3:
                    System.out.println("Logout realizado. Retornando ao menu principal.");
                    TelaPrincipal.exibirMenuInicial();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 3);
    }
}
