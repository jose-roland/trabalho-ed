package compression;

import java.util.*;

public class HuffmanTrie {
    TrieNode root;
    Map<Character, String> huffmanCodeMap;

    public HuffmanTrie(String input) {
        Map<Character, Integer> frequencies = calculateFrequencies(input);
        huffmanCodeMap = new HashMap<>();
        buildTrie(frequencies);
    }

    private Map<Character, Integer> calculateFrequencies(String input) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char currentCharacter : input.toCharArray()) {
            frequencies.put(currentCharacter, frequencies.getOrDefault(currentCharacter, 0) + 1);
        }
        return frequencies;
    }

    private void buildTrie(Map<Character, Integer> frequencies) {
        PriorityQueue<TrieNode> priority = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priority.add(new TrieNode(entry.getKey(), entry.getValue()));
        }

        while (priority.size() > 1) {
            TrieNode left = priority.poll();
            TrieNode right = priority.poll();
            TrieNode newNode = new TrieNode(left.frequency + right.frequency, left, right);
            priority.add(newNode);
        }

        root = priority.poll();
        generateHuffmanCodes(root, "");
    }

    private void generateHuffmanCodes(TrieNode node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            huffmanCodeMap.put(node.character, code);
        }
        generateHuffmanCodes(node.left, code + "0");
        generateHuffmanCodes(node.right, code + "1");
    }

    public String compress(String input) {
        StringBuilder compressedInput = new StringBuilder();
        for (char ch : input.toCharArray()) {
            compressedInput.append(huffmanCodeMap.get(ch));
        }
        return compressedInput.toString();
    }

    public String decompress(String compressed) {
        StringBuilder decompressedInput = new StringBuilder();
        TrieNode current = root;
        for (char bit : compressed.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            if (current.left == null && current.right == null) {
                decompressedInput.append(current.character);
                current = root;
            }
        }
        return decompressedInput.toString();
    }

    public String getHuffmanTable() {
        StringBuilder table = new StringBuilder();
        for (Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
            table.append("Caractere ").append(entry.getKey()).append(" -- ").append(entry.getValue()).append("\n");
        }
        return table.toString();
    }
}
