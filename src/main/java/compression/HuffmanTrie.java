package compression;

import java.util.*;

public class HuffmanTrie {
    TrieNode root;
    public Map<Character, String> huffmanCodeMap;

    public HuffmanTrie(String input) {
        Map<Character, Integer> frequencies = calculateFrequencies(input);
        huffmanCodeMap = new HashMap<>();
        buildTrie(frequencies);
    }

    private Map<Character, Integer> calculateFrequencies(String input){
        Map<Character, Integer> frequencies = new HashMap<>();
        char currentCharacter;
        int currentFrequency;

        // Percorrer todas as posições da palavra
        for (int i = 0; i < input.length(); i++) {
            // Caractere que está na posição atual
            currentCharacter = input.charAt(i);

            // Frequência desse caracter na tabela de frequências (0 é padrão para caracteres ainda não inseridos)
            currentFrequency = frequencies.getOrDefault(currentCharacter, 0);

            // Adiciona 1 na frequência e armazena de volta na tabela
            frequencies.put(currentCharacter, currentFrequency + 1);
        }

        return frequencies;
    }

    private void buildTrie(Map<Character, Integer> frequencies) {
        // Fila de prioridades que armazena os nós a Trie
        // Caracteres menos frequentes devem são adicionados primeiro
        PriorityQueue<TrieNode> priority = new PriorityQueue<>();

        // Itera sobre a tabela de frequências e cria um nó folha para cada símbolo
        for(Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            TrieNode newNode = new TrieNode(entry.getKey(), entry.getValue());
            priority.add(newNode);
        }

        // Enquanto tiver nó para ser inserido na Trie, aguardando na fila
        while(priority.size() > 1) {
            // Cria um novo nó com as referências para os filhos
            TrieNode left = priority.poll();
            TrieNode right = priority.poll();

            if (right == null) throw new AssertionError();

            TrieNode newNode = new TrieNode(left.frequency + right.frequency, left, right);
            priority.add(newNode); // Adiciona o novo nó na fila
        }

        root = priority.poll(); // Trie criada
        generateHuffmanCodes(root, "");
    }

    private void generateHuffmanCodes(TrieNode node, String code) {
        if (node == null) return;

        // Se o nó for folha, ele adiciona o nó na tabela de
        // símbolos do Huffman com a codificação correspondente
        if(node.left == null && node.right == null)
            huffmanCodeMap.put(node.character, code);

        // Desce para os filhos da direita e da esquerda
        generateHuffmanCodes(node.left, code + "0"); // Se for filho da esquerda: adiciona 0 à representação
        generateHuffmanCodes(node.right, code + "1"); // Se for filho da direita: adiciona 1 à representação
    }

    // Comprime uma string
    public String compress(String input) {
        StringBuilder compressedInput = new StringBuilder();

        // Percorre a string a ser comprimida e pega o caracter
        for (int i = 0; i < input.length(); i++)
            // Adiciona na string comprimida a representação huffman do caractere
            compressedInput.append(huffmanCodeMap.get(input.charAt(i)));

        return compressedInput.toString();
    }

    // Recebe uma string binária, com a representação comprimida
    // Retorna a string original
    public String decompress(String compressed) { // 00110101
        StringBuilder decompressedInput = new StringBuilder(); // Palavra descomprimida
        TrieNode current = root;
        char currentBit;

        // Começa a percorrer pela raiz, navegando pelas posições indicadas pelo símbolo
        // binário atual. Se for zero, vai para a esquerda. Se for um, vai pela direita.
        for (int i = 0; i < compressed.length(); i++) {
            if (current == null) {
                throw new IllegalStateException("Árvore Huffman inválida para a descompressão.");
            }

            currentBit = compressed.charAt(i);

            if (currentBit == '0')
                current = current.left;
            else
                current = current.right;

            if (current == null) throw new AssertionError();

            // Se alcançou um nó folha, adiciona o símbolo correspondente na mensagem
            // descomprimida e continua a navegar, volta à raiz e desce pelos símbolos restantes
            if (current.left == null && current.right == null) {
                decompressedInput.append(current.character);
                current = root;
            }
        }

        return decompressedInput.toString();
    }

    public String getHuffmanTable() {
        StringBuilder table = new StringBuilder();

        for(Map.Entry<Character, String> entry : huffmanCodeMap.entrySet())
            table.append("Caractere ").append(entry.getKey()).append(" -- ").append(entry.getValue()).append("\n");

        return table.toString();
    }
}
