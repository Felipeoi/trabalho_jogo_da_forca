package forca;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------------------------");
        System.out.println("--       BEM-VINDO AO JOGO DA FORCA   --");
        System.out.println("----------------------------------------");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            nome = "Jogador";
        }

        Jogador jogador = new Jogador(nome);

        GerenciadorDePalavras gerenciador = new GerenciadorDePalavras("src/forca/palavras_jogo_forca.txt");
        gerenciador.carregarPalavras();

        Jogo jogo = new Jogo(jogador, gerenciador);

        boolean jogarNovamente = true;

        while (jogarNovamente) {
            jogo.iniciar();
            jogarRodada(jogo, scanner);

            System.out.println();
            System.out.print("Deseja jogar novamente? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            jogarNovamente = resposta.equals("S");
        }

        System.out.println();
        System.out.println("Obrigado por jogar! Até a próxima!");
        scanner.close();
    }

    private static void jogarRodada(Jogo jogo, Scanner scanner) {
        while (jogo.getEstado() == EstadoJogo.EM_ANDAMENTO) {
            jogo.exibirEstado();

            System.out.print("Digite uma letra: ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.isEmpty()) {
                System.out.println("⚠  Entrada inválida. Digite uma letra.");
                continue;
            }

            if (entrada.length() > 1) {
                System.out.println("⚠  Digite apenas UMA letra por vez.");
                continue;
            }

            char letra = entrada.charAt(0);

            if (!Character.isLetter(letra)) {
                System.out.println("⚠  Digite apenas letras.");
                continue;
            }

            boolean processada = jogo.fazerAdivinhacao(letra);

            if (!processada) {
                System.out.println("⚠  Você já tentou a letra '" + letra + "'. Tente outra!");
            }
        }

        // Exibe estado final
        jogo.exibirEstado();

        if (jogo.getEstado() == EstadoJogo.VITORIA) {
            System.out.println();
            System.out.println("🎉 PARABÉNS, " + jogo.getJogador().getNome() + "! Você acertou a palavra: " + jogo.getPalavraSecreta());
        } else {
            System.out.println();
            System.out.println("💀 GAME OVER! A palavra era: " + jogo.getPalavraSecreta());
        }
    }
}
