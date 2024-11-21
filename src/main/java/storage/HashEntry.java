package storage;

public class HashEntry<K, V> {
    K key; // Chave do valor armazenado
    V value; // Valor armazenado

    public HashEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
