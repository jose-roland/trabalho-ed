package com.example;

import compression.HuffmanTrie;
import storage.HashTable;

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

        // Parte 3: Hash
        System.out.print("> Qual a função de hashing (divisao/djb2): ");
        String hashChoice = scanner.nextLine();

        HashTable<String, String> testHash = new HashTable<>(31);

        try {
            testHash.setHashFunction(hashChoice);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
      
        if (listOfFiles != null) // Caso o diretório não esteja vazio, vai executar
            for (File file : listOfFiles) // Para cada arquivo na lista de arquivos
                try {
                    // Lê o conteúdo original do arquivo
                    String originalContent = new String(Files.readAllBytes(file.toPath()));

                    // Comprime o conteúdo
                    HuffmanTrie huffmanTrie = new HuffmanTrie(originalContent);
                    String compressedContent = huffmanTrie.compress(originalContent);

                    // Insere o texto comprimido com a chave sendo o nome do arquivo.
                    testHash.put(file.getName(), compressedContent);
                } catch (IOException e) {
                    System.err.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                }
    }
}
