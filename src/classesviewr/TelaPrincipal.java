package classesviewr;

import java.util.Scanner;

public class TelaPrincipal {

    public static void exibirMenuInicial() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Bem-Vindo ao Tastebook ===");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar");
            System.out.println("3 - Entrar como visitante");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    TelaUsuario.exibirTelaLogin();
                    break;
                case 2:
                    TelaUsuario.exibirTelaCadastro();
                    break;
                case 3:
                    TelaVisitante.exibirMenuVisitante();
                    break;
                case 4:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 4);
    }

    public static void main(String[] args) {
        exibirMenuInicial();
    }
}
