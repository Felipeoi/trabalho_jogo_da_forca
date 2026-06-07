package forca;

import java.util.List;
import java.util.Random;

public class GerenciadorDePalavras {

    private String caminhoArquivo;
    private List<String> palavras;

    public GerenciadorDePalavras(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void carregarPalavras() {
        ArquivoDePalavras arquivo = new ArquivoDePalavras(caminhoArquivo);
        palavras = arquivo.lerArquivo();

        if (palavras.isEmpty()) {
            throw new RuntimeException("Nenhuma palavra foi carregada. Verifique o arquivo: " + caminhoArquivo);
        }
    }

    public String getPalavraAleatoria() {
        if (palavras == null || palavras.isEmpty()) {
            throw new RuntimeException("Nenhuma palavra foi carregada. Verifique o arquivo: " + caminhoArquivo);
        }
        Random random = new Random();
        return palavras.get(random.nextInt(palavras.size()));
    }
}
