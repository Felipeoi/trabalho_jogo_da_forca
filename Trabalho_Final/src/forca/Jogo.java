package forca;

import java.util.HashSet;
import java.util.Set;

public class Jogo {

    private static final int TENTATIVAS_INICIAIS = 6;

    private String palavraSecreta;
    private Set<Character> letrasCorretas;
    private Set<Character> letrasIncorretas;
    private int tentativasRestantes;
    private int tentativasIniciais;
    private EstadoJogo estado;
    private Jogador jogador;
    private GerenciadorDePalavras gerenciadorDePalavras;

    public Jogo(Jogador jogador, GerenciadorDePalavras gerenciadorDePalavras) {
        this.jogador = jogador;
        this.gerenciadorDePalavras = gerenciadorDePalavras;
        this.letrasCorretas = new HashSet<>();
        this.letrasIncorretas = new HashSet<>();
    }

    public void iniciar() {
        palavraSecreta = gerenciadorDePalavras.getPalavraAleatoria();
        tentativasIniciais = TENTATIVAS_INICIAIS;
        tentativasRestantes = TENTATIVAS_INICIAIS;
        letrasCorretas.clear();
        letrasIncorretas.clear();
        estado = EstadoJogo.EM_ANDAMENTO;
    }

    public boolean fazerAdivinhacao(char letra) {
        letra = Character.toUpperCase(letra);

        // Ignora letra já tentada
        if (letrasCorretas.contains(letra) || letrasIncorretas.contains(letra)) {
            return false; // indica que foi ignorada
        }

        if (verificarLetra(letra)) {
            letrasCorretas.add(letra);
        } else {
            letrasIncorretas.add(letra);
            tentativasRestantes--;
        }

        verificarFim();
        return true; // indica que foi processada
    }

    private boolean verificarLetra(char letra) {
        return palavraSecreta.indexOf(letra) >= 0;
    }

    private void verificarFim() {
        if (todasLetrasAdivinhadas()) {
            estado = EstadoJogo.VITORIA;
        } else if (tentativasRestantes <= 0) {
            estado = EstadoJogo.DERROTA;
        }
    }

    private boolean todasLetrasAdivinhadas() {
        for (char c : palavraSecreta.toCharArray()) {
            if (!letrasCorretas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void exibirEstado() {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("-          JOGO DA FORCA               -");
        System.out.println("----------------------------------------");
        System.out.printf( "-  Jogador : %-26s-%n", jogador.getNome());
        System.out.println("----------------------------------------");

        // Desenho da forca
        exibirForca();

        System.out.println(" ");

        // Palavra com underlines
        System.out.printf("-  Palavra : %-26s-%n", getPalavraExibida());
        System.out.printf("-  Tamanho : %-2d letras%17s-%n", palavraSecreta.length(), "");

        System.out.println(" ");

        // Letras incorretas
        String incorretas = letrasIncorretas.isEmpty() ? "nenhuma" : letrasIncorretas.toString();
        System.out.printf("-  Erros   : %-27s-%n", incorretas);

        // Tentativas
        System.out.printf("-  Tentativas: %2d usadas | %2d restantes -%n",
                (tentativasIniciais - tentativasRestantes), tentativasRestantes);

        System.out.println("-------------------------------------------");
    }

    private void exibirForca() {
        int erros = tentativasIniciais - tentativasRestantes;

        String[] forca = {
            "-   ______               -           -",
            "-  |      |              -           -",
            "-  |      " + (erros >= 1 ? "O" : " ") + "              -           -",
            "-  |     " + (erros >= 3 ? "/" : " ") + (erros >= 2 ? "|" : " ") + (erros >= 4 ? "\\" : " ") + "             -           -",
            "-  |     " + (erros >= 5 ? "/" : " ") + " " + (erros >= 6 ? "\\" : " ") + "             -           -",
            "-  |                     -           -",
            "-__|__                   -           -"
        };

        for (String linha : forca) {
            System.out.println(linha);
        }
    }

    private String getPalavraExibida() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraSecreta.toCharArray()) {
            if (letrasCorretas.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public void reiniciar() {
        iniciar();
    }


    public EstadoJogo getEstado() {
        return estado;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public Set<Character> getLetrasCorretas() {
        return letrasCorretas;
    }

    public Set<Character> getLetrasIncorretas() {
        return letrasIncorretas;
    }

    public Jogador getJogador() {
        return jogador;
    }
}
