package index;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    // Insere uma palavra na Trie e associa ao nome do documento
    public void insert(String word, String documentName) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
        node.documentos.add(documentName);  // Associa o documento à palavra
    }

    // Busca uma palavra completa na Trie
    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return false;
            }
        }
        return node.isEndOfWord;
    }

    // Verifica se existe alguma palavra que começa com o prefixo
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    // Busca os documentos onde a palavra completa está presente
    public List<String> searchDocuments(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return new ArrayList<>();  // Palavra não encontrada
            }
        }
        return node.isEndOfWord ? new ArrayList<>(node.documentos) : new ArrayList<>();
    }
}
