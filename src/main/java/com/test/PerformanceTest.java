package com.test;

import compression.HuffmanTrie;
import index.Trie;
import storage.HashTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PerformanceTest {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            String[] testWords = {"method", "estimation", "analysis", "total", "information", "complexity", "what", "distance", "interests", "significantly", "calibration", "predictions", "patient", "source", "models", "pretend", "introduce", "immediately", "small", "procedure", "assignment", "different", "constant", "generic", "anything", "construction", "exists", "classes", "useful", "exactly", "compute", "power", "start", "development", "such", "instance", "necessarily", "fact", "attention", "both", "robust", "from", "output", "straightforward", "calculation", "initial", "have", "vectors", "results", "generalize", "though", "approach", "geometric", "error", "standard", "step", "algorithm", "scratch", "quantum", "careful", "which", "other", "random", "under", "environment", "model", "unitary", "interested", "minimum", "probability", "where", "may", "instrument", "range", "sun", "more", "order", "relative", "full", "line", "data", "each", "with", "absolute", "image", "behavior", "although", "translation", "study", "that", "becc", "minecraft", "itadori", "sukuna", "gachiakuta", "banana", "brother", "botafogo", "barbie", "ufms"};

            File folder = new File("./src/main/resources/output/");
            File[] listOfFiles = folder.listFiles((file) -> file.getName().endsWith(".txt"));

            HashTable<String, String> testHash = new HashTable<>(31);
            Trie trie = new Trie();

            if (i == 0) {
                testHash.setHashFunction("divisao");
                System.out.println("TESTE DA FUNÇÃO DIVISÃO");
            } else {
                testHash.setHashFunction("djb2");
                System.out.println("TESTE DA FUNÇÃO DJB2");
            }

            if (listOfFiles != null) {
                long startTime = System.nanoTime();

                Runtime runtime = Runtime.getRuntime();
                runtime.gc(); // Sugere que o Garbage Collector execute antes de medir
                long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                for (File file : listOfFiles)
                    try {
                        String originalContent = new String(Files.readAllBytes(file.toPath()));

                        HuffmanTrie huffmanTrie = new HuffmanTrie(originalContent);
                        String compressedContent = huffmanTrie.compress(originalContent);

                        testHash.put(file.getName(), compressedContent);

                        String decompressedContent = huffmanTrie.decompress(compressedContent);
                        String[] words = decompressedContent.split("\\W+");

                        for (String word : words)
                            if (!word.isBlank())
                                trie.insert(word.toLowerCase(), file.getName());
                    } catch (IOException e) {
                        System.err.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                    }
                long endTime = System.nanoTime();
                long duration = endTime - startTime; // Duração em nanosegundos
                System.out.println("Tempo de indexação: " + duration + " ns, " + duration / 1000000 + " ms");

                long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
                long totalMemory = usedMemoryAfter - usedMemoryBefore;

                System.out.println("Consumo de memória das estruturas: " + totalMemory + " b, " + totalMemory/1000.00 + " kb, " + totalMemory / 1000000.00 + " mb\n");
            }

            // NOVO: Busca de palavras
            long totalFound = 0, totalNotFound = 0;

            for (String word : testWords) {
                long startTime = System.nanoTime();
                var results = trie.searchDocuments(word.toLowerCase()); // Busca a palavra no índice
                if (results.isEmpty()) {
                    System.out.print("x " + word);
                } else {
                    System.out.print(word);
                }
                long endTime = System.nanoTime();
                long duration = endTime - startTime; // Duração em nanosegundos

                if (results.isEmpty()) {
                    totalNotFound += duration;
                } else {
                    totalFound += duration;
                }

                System.out.println(" / " + duration + " ns");
            }

            System.out.println("\nTempo médio de palavras encontradas: " + totalFound/90 + " ns\nTempo médio de palavras que não foram encontrados: " + totalNotFound/10 + " ns\nTempo médio de todas: " + (totalNotFound+totalFound)/100 + " ns");

            System.out.println();
        }
    }
}
