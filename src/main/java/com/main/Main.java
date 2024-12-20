package com.main;

import compression.HuffmanTrie;
import index.Trie;
import storage.HashTable;
import util.CompressedFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
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

        HashTable<String, CompressedFile> testHash = new HashTable<>(31);
        Trie trie = new Trie(); // Índice de busca

        try {
            testHash.setHashFunction(hashChoice);
            System.out.println("Função de hashing '" + hashChoice + "' configurada com sucesso!");
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

                    CompressedFile compressedFile = new CompressedFile(compressedContent, huffmanTrie);
                    testHash.put(file.getName(), compressedFile);

                    // Insere o texto comprimido com a chave sendo o nome do arquivo.
                    // testHash.put(file.getName(), compressedContent);

                    // Indexa palavras no Trie usando o texto descomprimido
                    String decompressedContent = huffmanTrie.decompress(compressedContent); // Descomprime o texto
                    String[] words = decompressedContent.split("\\W+"); // Divide o texto em palavras

                    for (String word : words) { // Para cada palavra encontrada
                        if (!word.isBlank()) { // Ignorar palavras vazias
                            trie.insert(word.toLowerCase(), file.getName()); // Insere no índice com o nome do arquivo
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                }

        // Mensagem de sucesso após inserção e indexação
        System.out.println("Documentos inseridos e indexados com sucesso!");

        // Parte 3: Busca de palavras
        System.out.print("\n> Buscar palavra: ");
        String query = scanner.nextLine();

        var results = trie.searchDocuments(query.toLowerCase()); // Busca a palavra no índice
        if (results.isEmpty()) {
            System.out.println("A palavra \"" + query + "\" não foi encontrada em nenhum documento.");
        } else {
            System.out.println("A palavra \"" + query + "\" foi encontrada nos seguintes documentos:");

            for (String doc : results) {
                System.out.println("- " + doc);
            }

            // Parte 4: Busca de documento para imprimir seu conteúdo
            System.out.print("\n> Digite o nome do arquivo para exibir o conteúdo: ");
            String fileName = scanner.nextLine();

            LinkedList<CompressedFile> compressedFiles = testHash.get(fileName); // Busca o arquivo na tabela hash

            if (compressedFiles != null && !compressedFiles.isEmpty()) {
                CompressedFile compressedFile = compressedFiles.getFirst(); // Obter o conteúdo comprimido
                HuffmanTrie huffmanTrie = compressedFile.getHuffmanTrie(); // Obter a árvore Huffman associada

                // Descomprimir
                System.out.println(huffmanTrie.decompress(compressedFile.getCompressedContent()));
            } else {
                System.out.println("Arquivo não encontrado na tabela hash.");
            }
        }

        scanner.close();
        System.out.println("\nPrograma encerrado.");
    }
}
