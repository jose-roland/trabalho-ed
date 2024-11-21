package storage;

import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<HashEntry<K, V>>[] hashTable; // Tabela hash
    private int size; // Tamanho da tabela hash
    private String hashFunction; // "divisao" ou "djb2"

    public HashTable(int size) {
        hashTable = new LinkedList[size];
        this.size = size;
        this.hashFunction = "divisao"; // Padrão inicial
    }

    // Define a função hash a ser usada
    public void setHashFunction(String hashFunction) {
        if (!hashFunction.equals("divisao") && !hashFunction.equals("djb2")) {
            throw new IllegalArgumentException("Função hash inválida. Escolha entre 'divisao' ou 'djb2'.");
        }
        this.hashFunction = hashFunction;
    }

    // Retorna o valor associado à chave na tabela hash
    public LinkedList<String> get(K key) {
        LinkedList<V> valuesFound = new LinkedList<>();
        int position;

        // Não pode inserir valores com chave nula
        if (key == null)
            return null;

        if (hashFunction.equals("divisao"))
            position = hashDivisao(key, size); // Passa a chave genérica
        else
            position = Math.abs(hashDJB2(key) % size); // Passa a chave genérica

        // Se a posição do hash for nulo, então nenhum elemento com aquela chave foi inserido ainda.
        // Nesse caso, retorna null
        if (hashTable[position] == null)
            return null;

            // Se existir uma lista na posição
        else {
            LinkedList<HashEntry<K, V>> currentList = hashTable[position];
            for (HashEntry<K, V> currentEntry : currentList)
                // Se a chave que procuramos é igual à chave guardada nessa posição
                // Guarda o valor encontrado na lista de valores
                if (key.equals(currentEntry.key))
                    valuesFound.add(currentEntry.value);

            return (LinkedList<String>) valuesFound;
        }
    }

    public boolean put(K key, V value) {
        resizeIfNeeded();

        int position;

        // Não pode inserir valores com chave nula
        if (key == null)
            return false;

        // Antes de inserir, precisamos checar se já não existe na coleção uma chave com
        // valor idêntico ao que estamos tentando inserir. Caso exista, não deve inserir e
        // retorna null
        LinkedList<V> currentValuesForKey = (LinkedList<V>) get(key);

        if (currentValuesForKey != null && currentValuesForKey.contains(value))
            return false;

        if (hashFunction.equals("divisao"))
            position = hashDivisao(key, size); // Passa a chave genérica
        else
            position = Math.abs(hashDJB2(key) % size); // Passa a chave genérica

        // Pegando a lista da posição em que precisamos inserir
        LinkedList<HashEntry<K, V>> currentList = hashTable[position];

        // Testa se a posição é nula. Se for, precisamos criar a lista para inserir o valor
        if (currentList == null)
            currentList = new LinkedList<HashEntry<K, V>>(); // Cria a lista

        currentList.add(new HashEntry<K, V>(key, value)); // Inserir o valor na lista
        hashTable[position] = currentList; // Guardo a lista na posição

        return true; // Inserido com sucesso

    }

    public void print() {
        for (int i = 0; i < hashTable.length; i++) {
            System.out.println("---------------");
            System.out.println("Position " + i + ":");

            LinkedList<HashEntry<K, V>> currentList = hashTable[i]; // Obtém a lista na posição

            if (currentList == null) {
                System.out.println("Empty position"); // Verifica se a lista é nula
            } else {
                for (HashEntry<K, V> kvHashEntry : currentList) // Itera sobre a lista se ela existir
                    System.out.print(kvHashEntry.key.toString() + "  -  ");

                System.out.println();
            }
        }
    }


    public int hashDivisao(K key, int M) {
        String texto = key.toString(); // Converte a chave genérica para String
        int soma = 0;

        for (char c : texto.toCharArray())
            soma += c;

        return soma % M;
    }

    public int hashDJB2(K key) {
        String texto = key.toString(); // Converte a chave genérica para String
        long hash = 5381;

        for (char c : texto.toCharArray())
            hash = ((hash << 5) + hash) + c; // hash * 33 + c

        return (int) (hash % Integer.MAX_VALUE);
    }

    // Método para verificar se é necessário redimensionar
    private void resizeIfNeeded() {
        int loadFactor = getNumberOfElements() * 100 / size; // Calcula o fator de carga em %

        if (loadFactor >= 75) // Se passar de 75%, redimensionar
            resize();
    }

    // Método que redimensiona a tabela
    private void resize() {
        int newSize = getNextPrime(size * 2); // Dobra o tamanho e pega o próximo primo
        LinkedList<HashEntry<K, V>>[] oldTable = hashTable; // Armazena a tabela antiga

        hashTable = new LinkedList[newSize]; // Cria uma nova tabela com o tamanho atualizado
        size = newSize;

        // Reinsere os elementos antigos na nova tabela
        for (LinkedList<HashEntry<K, V>> currentList : oldTable)
            if (currentList != null)
                for (HashEntry<K, V> entry : currentList)
                    put(entry.key, entry.value); // Usa o método put para reposicionar os elementos
    }

    // Método auxiliar para contar o número de elementos na tabela
    private int getNumberOfElements() {
        int count = 0;

        for (LinkedList<HashEntry<K, V>> currentList : hashTable)
            if (currentList != null)
                count += currentList.size();

        return count;
    }

    // Método para encontrar o próximo número primo maior ou igual a um número
    private int getNextPrime(int num) {
        while (!isPrime(num))
            num++;

        return num;
    }

    // Método para verificar se um número é primo
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        for (int i = 5; i * i <= num; i += 6)
            if (num % i == 0 || num % (i + 2) == 0) return false;

        return true;
    }
}


