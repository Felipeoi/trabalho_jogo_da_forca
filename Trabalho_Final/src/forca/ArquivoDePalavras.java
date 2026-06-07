package forca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDePalavras {

    private String caminho;

    public ArquivoDePalavras(String caminho) {
        this.caminho = caminho;
    }

    public List<String> lerArquivo() {
        List<String> palavras = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    palavras.add(linha.toUpperCase());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de palavras: " + e.getMessage());
            System.err.println("Caminho tentado: " + caminho);
        }

        return palavras;
    }
}
