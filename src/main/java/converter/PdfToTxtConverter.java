package converter;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfToTxtConverter {
    private static final Logger logger = LoggerFactory.getLogger(PdfToTxtConverter.class);

    public static void main(String[] args) {
        // Caminho para o diretório que contém os PDFs e o diretório de saída para os arquivos TXT
        // Usar esse aqui para teste: ./src/main/resources/input/
        String outputDirectory = "./src/main/resources/output/";

        Scanner scanner = new Scanner(System.in);
        int count = 0;

        System.out.print("> Inserir documentos: ");
        String pdfDirectory = scanner.nextLine();

        Tika tika = new Tika();

        // Obtém todos os arquivos PDF no diretório especificado
        File folder = new File(pdfDirectory);

        File[] listOfFiles = folder.listFiles((file) -> file.getName().endsWith(".pdf"));

        if (listOfFiles != null) {
            for (File pdfFile : listOfFiles) {
                try {
                    // Lê o conteúdo do PDF
                    String text = tika.parseToString(pdfFile);

                    // Define o caminho para o arquivo TXT de saída com o mesmo nome do PDF
                    String txtPath = outputDirectory + pdfFile.getName().replace(".pdf", ".txt");

                    // Salva o texto extraído em um arquivo TXT
                    try (FileWriter writer = new FileWriter(txtPath)) {
                        writer.write(text);
                    }
                } catch (Exception e) {
                    logger.error("{} Erro ao processar o arquivo {}", count, pdfFile.getName(), e);
                }

                count++;
                printProgressBar(count);
            }

            System.out.print("\nDocumentos inseridos com sucesso!\n");
        }
        else
            System.out.println("Nenhum arquivo PDF encontrado no diretório.");
    }

    private static void printProgressBar(int count) {
        // Comprimento da barra de progresso
        int progressBarLength = 30;

        // Calcula a porcentagem concluída
        int progress = (count * progressBarLength) / 30;

        // Constrói a barra de progresso
        StringBuilder progressBar = new StringBuilder("[");

        for (int i = 0; i < progressBarLength; i++) {
            if (i < progress)
                progressBar.append("#");
            else
                progressBar.append(" ");
        }

        progressBar.append("] ");

        // Exibe a barra de progresso e a porcentagem
        System.out.print("\r" + progressBar + count * 100 / 30 + "%");
    }
}
