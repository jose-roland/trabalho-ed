package compression;

import java.io.*;
import java.nio.file.*;

public class HuffmanTrieTest {
    public static void main(String[] args) {
        String inputDirectory = "./src/main/resources/output/";

        File folder = new File(inputDirectory);
        File[] listOfFiles = folder.listFiles((file) -> file.getName().endsWith(".txt"));

        if (listOfFiles != null)
            for (File file : listOfFiles) {
                try {
                    // Lê o conteúdo original do arquivo
                    String originalContent = new String(Files.readAllBytes(file.toPath()));

                    // Comprime o conteúdo
                    HuffmanTrie huffmanTrie = new HuffmanTrie(originalContent);
                    String compressedContent = huffmanTrie.compress(originalContent);

                    // Verifica se a descompressão corresponde ao conteúdo original
                    System.out.println("Arquivo: " + file.getName());
                    System.out.println();
                    System.out.println(huffmanTrie.getHuffmanTable());
//                    System.out.println("Comprimido: " + compressedContent);
//                    System.out.println("Descomprimido: "+ decompressedContent);
//                    System.out.println("Descompressão bem-sucedida? " + originalContent.equals(decompressedContent));

                } catch (IOException e) {
                    System.err.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                }
            }
        else
            System.out.println("Nenhum arquivo .txt encontrado no diretório de entrada.");
    }
}
