# Projeto de Estrutura de Dados - Sistema de Indexação de Textos

Este projeto foi desenvolvido como parte do trabalho prático da disciplina de Estrutura de Dados na UFMS, ministrada pelo Prof. Bruno M. Nogueira. O objetivo é implementar um sistema de indexação de textos eficiente utilizando diferentes estruturas de dados para armazenamento e recuperação de informações.

## Descrição do Projeto

O sistema permite:
1. Inserção de documentos em formato `.txt`, que são processados e armazenados em uma estrutura de dados.
2. Compactação dos documentos utilizando o algoritmo de Huffman.
3. Indexação das palavras dos documentos em uma Trie para facilitar a busca.
4. Busca de palavras, indicando quais documentos contêm a palavra pesquisada.

## Estruturas de Dados Utilizadas

- **Tabela Hash**: Utilizada para armazenar os textos compactados, com duas opções de funções de hash para comparação de desempenho (Divisão e DJB2).
- **Trie (Árvore Digital)**: Utilizada para indexação de palavras, possibilitando uma busca eficiente.

## Funcionalidades

- **Inserir documentos**: Permite a inserção de um conjunto de documentos, fornecendo o caminho da pasta com os arquivos `.txt`.
- **Escolher função de hash**: O usuário pode escolher entre as funções de hash de Divisão ou DJB2.
- **Buscar por palavra**: Exibe os documentos que contêm a palavra pesquisada.
  
## Medição de Desempenho

O sistema mede:
- Tempo de indexação
- Tempo de busca
- Consumo de memória das estruturas de dados

## Grupo

- José Roland
- Maria
- Mariana
- Wilson
