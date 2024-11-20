package com.example;

import compression.HuffmanTrie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Parte 1: Inserção de documentos
        System.out.print("> Inserir documentos: ");
        String inputDirectory = scanner.nextLine(); // Recebendo diretório de onde estão os arquivos de texto
        // String inputDirectory = "./src/main/resources/output/";

        // Parte 2: Compressão dos arquivos de texto
        // Lista de arquivos que vai percorrer todos os arquivos no diretório
        File folder = new File(inputDirectory);
        File[] listOfFiles = folder.listFiles((file) -> file.getName().endsWith(".txt"));

        if (listOfFiles != null) // Caso o diretório não esteja vazio, vai executar
            for (File file : listOfFiles) // Para cada arquivo na lista de arquivos
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

                    // System.out.println("Comprimido: " + compressedContent);
                    // System.out.println("Descomprimido: "+ decompressedContent);
                    // System.out.println("Descompressão bem-sucedida? " + originalContent.equals(decompressedContent));
                } catch (IOException e) {
                    System.err.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                }
        else // Caso o diretório esteja vazio
            System.out.println("Nenhum arquivo .txt encontrado no diretório de entrada.");
    }
}
