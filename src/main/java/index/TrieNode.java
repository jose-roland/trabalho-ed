package index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    Set<String> documentos;  // Armazena os documentos onde a palavra é encontrada

    // Construtor padrão
    public TrieNode() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
        this.documentos = new HashSet<>();
    }
}
