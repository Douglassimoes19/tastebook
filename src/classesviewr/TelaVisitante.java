package classesviewr;

import java.util.Scanner;

public class TelaVisitante {

    public static void exibirMenuVisitante() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Área do Visitante ---");
            System.out.println("1 - 10 receitas mais acessadas");
            System.out.println("2 - Buscar receitas");
            System.out.println("3 - Fazer login");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Exibindo as 10 receitas mais acessadas...");
                    break;
                case 2:
                    System.out.print("Digite o nome da receita para buscar: ");
                    String busca = scanner.nextLine();
                    System.out.println("Buscando receita: " + busca);
                    break;
                case 3:
                    TelaUsuario.exibirTelaLogin();
                    return; // sai do visitante ao logar
                case 4:
                    System.out.println("Saindo da área do visitante...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 4);
    }
}
