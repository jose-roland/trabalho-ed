public static void main(String[] args) {
    System.out.println("Hello world!");
    // Parte 1: Inserção de documentos
    System.out.println("> Inserir documentos: /home/bruno/documentos");
    List<Documento> documentos = new ArrayList<>();

     documentos.add(new Documento("artigo1.txt"));

    documentos.get(0).adicionarPalavra("aprendizado");

    System.out.println("Documentos inseridos com sucesso!");

     // Parte 2: Escolher função de hashing
    System.out.print("> Qual a função de hashing (divisao/djb2): ");
    String tipoHashing = scanner.nextLine();
    System.out.println("Documentos indexados com sucesso!");

     // Parte 3: Criação da tabela de hash
    int tabelaTamanho = 10; // Definindo o tamanho da tabela de hash
    HashTable hashTable = new HashTable(tabelaTamanho);
    
    // Inserindo documentos na tabela de hash
    for (Documento doc : documentos) {
        for (String palavra : doc.palavras) {
            hashTable.inserir(doc, palavra);
        }
    }
}
