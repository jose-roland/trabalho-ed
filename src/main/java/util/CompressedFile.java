package util;

import compression.HuffmanTrie;

public class CompressedFile {
    String compressedContent;
    HuffmanTrie huffmanTrie;

    public CompressedFile(String compressedContent, HuffmanTrie huffmanTrie) {
        this.compressedContent = compressedContent;
        this.huffmanTrie = huffmanTrie;
    }

    public String getCompressedContent() {
        return compressedContent;
    }

    public HuffmanTrie getHuffmanTrie() {
        return huffmanTrie;
    }
}
