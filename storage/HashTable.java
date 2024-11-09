package storage;

import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<HashEntry<K, V>>[] hashTable;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        this.size = size;
        this.hashTable = new LinkedList[size];
    }

    public int getPosition(int hashCode) {
        return Math.abs(hashCode) % size;
    }

    public LinkedList<V> get(K key) {
        if (key == null) return null;
        int position = getPosition(key.hashCode());
        LinkedList<V> valuesFound = new LinkedList<>();

        LinkedList<HashEntry<K, V>> currentList = hashTable[position];
        if (currentList != null) {
            for (HashEntry<K, V> entry : currentList) {
                if (entry.key.equals(key)) {
                    valuesFound.add(entry.value);
                }
            }
        }
        return valuesFound.isEmpty() ? null : valuesFound;
    }

    public boolean put(K key, V value) {
        if (key == null) return false;

        int position = getPosition(key.hashCode());
        if (hashTable[position] == null) {
            hashTable[position] = new LinkedList<>();
        }

        for (HashEntry<K, V> entry : hashTable[position]) {
            if (entry.key.equals(key) && entry.value.equals(value)) {
                return false;
            }
        }

        hashTable[position].add(new HashEntry<>(key, value));
        return true;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println("Position " + i + ":");
            if (hashTable[i] == null) {
                System.out.println("Empty");
            } else {
                for (HashEntry<K, V> entry : hashTable[i]) {
                    System.out.print(entry + "  ");
                }
                System.out.println();
            }
        }
    }
}
