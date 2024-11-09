from pdfminer.high_level import extract_text
from pdfminer.layout import LAParams
import os

class PDFToTextConverter:
    def __init__(self, input_dir, output_dir):
        self.input_dir = input_dir
        self.output_dir = output_dir
        os.makedirs(output_dir, exist_ok=True)
        os.makedirs(input_dir, exist_ok=True)

    def convert_all_pdfs(self):
        pdf_files = [f for f in os.listdir(self.input_dir) if f.endswith('.pdf')]
        for pdf_file in pdf_files:
            self.convert_pdf_to_text(pdf_file)

    def convert_pdf_to_text(self, pdf_file):
        pdf_path = os.path.join(self.input_dir, pdf_file)
        text_path = os.path.join(self.output_dir, pdf_file.replace('.pdf', '.txt'))

        try:
            # Ajustes no LAParams para preservar o layout
            laparams = LAParams(
                char_margin=2.0,  # Ajusta o espaçamento entre caracteres
                line_margin=0.5,  # Ajusta o espaçamento entre linhas
                word_margin=0.1,  # Ajusta o espaçamento entre palavras
                boxes_flow=None   # Desativa o fluxo de caixas para melhorar a detecção
            )
            text = extract_text(pdf_path, laparams=laparams)
            
            # Escrevendo o texto extraído
            with open(text_path, 'w', encoding='utf-8') as txt_file:
                txt_file.write(text)
            
            print(f"Converted {pdf_file} to {text_path}")
        except Exception as e:
            print(f"Error converting {pdf_file}: {e}")

# Exemplo de uso
if __name__ == "__main__":
    input_dir = "./resources/input_pdfs"
    output_dir = "./resources/output_texts"
    converter = PDFToTextConverter(input_dir, output_dir)
    converter.convert_all_pdfs()
