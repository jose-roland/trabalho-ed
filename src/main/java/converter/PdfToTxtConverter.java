package converter;

import org.apache.tika.Tika;
import java.io.File;
import java.io.FileWriter;

public class PdfToTxtConverter {
    public static void main(String[] args) {
        // Caminho para o diretório que contém os PDFs e o diretório de saída para os arquivos TXT
        String pdfDirectory = "C:/Code/teste/Teste/src/main/resources/input/";
        String outputDirectory = "C:/Code/teste/Teste/src/main/resources/output/";

        int count = 1;

        // Cria o diretório de saída, se não existir
        File outputDir = new File(outputDirectory);

        if (!outputDir.exists())
            outputDir.mkdirs();

        Tika tika = new Tika();

        // Obtém todos os arquivos PDF no diretório especificado
        File folder = new File(pdfDirectory);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".pdf"));  // Filtra apenas PDFs

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

                    System.out.println(count + ". Conversão do arquivo " + pdfFile.getName() + " realizada com sucesso");

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(count + ". Erro ao processar o arquivo " + pdfFile.getName());
                }

                count++;
            }
        } else {
            System.out.println("Nenhum arquivo PDF encontrado no diretório.");
        }

        count = 1;
    }
}