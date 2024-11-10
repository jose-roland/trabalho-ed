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
        Scanner scanner = new Scanner(System.in);

        System.out.print("> Inserir documentos: ");
        String pdfDirectory = scanner.nextLine();

        // Caminho para o diretório que contém os PDFs e o diretório de saída para os arquivos TXT
        // String pdfDirectory = "C:/Code/teste/Teste/src/main/resources/input/";
        String outputDirectory = "C:/Code/teste/Teste/src/main/resources/output/";

        int count = 1;

        // Cria o diretório de saída, se não existir
        File outputDir = new File(outputDirectory);

        if (!outputDir.exists() && !outputDir.mkdirs())
            System.err.println("Falha ao criar o diretório de saída.");
        else
            System.out.println("Diretório de saída criado ou já existe.");

        Tika tika = new Tika();

        // Obtém todos os arquivos PDF no diretório especificado
        File folder = new File(pdfDirectory);
        File[] listOfFiles = folder.listFiles((file) -> file.getName().endsWith(".pdf"));

        if (listOfFiles != null)
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

                    System.out.println(count + ". Conversão do arquivo " + pdfFile.getName() + " realizada com sucesso");

                } catch (Exception e) {
                    logger.error("{} Erro ao processar o arquivo {}", count, pdfFile.getName(), e);
                }

                count++;
            }
        else
            System.out.println("Nenhum arquivo PDF encontrado no diretório.");
    }
}
