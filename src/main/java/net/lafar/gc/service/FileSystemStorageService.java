package net.lafar.gc.service;

import fr.opensagres.odfdom.converter.core.utils.StringUtils;
import fr.opensagres.poi.xwpf.converter.core.XWPFConverterException;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileSystemStorageService {
    public void convertir(){
        String filepath = "D:\\documentos_prueba\\manual.docx";
        String outpath = "D:\\documentos_prueba\\manual.pdf";

        InputStream source;
        OutputStream target;
        try {
            source = new FileInputStream(filepath);
            target = new FileOutputStream(outpath);
            Map<String, String> params = new HashMap<String, String>();


            PdfOptions options = PdfOptions.create();

            wordConverterToPdf(source, target, options, params);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            for (XWPFParagraph p : paragraphs){
                for (XWPFRun r : p.getRuns()){
                    String content = r.getText(r.getTextPosition());
                    if(StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }

    public void wordConverterToPdf(InputStream source, OutputStream target,
                                          PdfOptions options,
                                          Map<String, String> params) throws Exception {
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, options);
    }

    public XWPFDocument leerDocx(File archivoWord) {
        XWPFDocument documentoWord = null;
        try {
            // Se prepara el archivo para su tratamiento
            InputStream texto = new FileInputStream(archivoWord);

            // Creamos documento especial POI para su posterior conversión
            documentoWord = new XWPFDocument(texto);
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero de Word");
            e.printStackTrace();
        }
        return documentoWord;
    }

    public boolean convertirPDF(File archivoPDF, XWPFDocument documentWord) {
        boolean exito;
        try {
            OutputStream out = new FileOutputStream(archivoPDF);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(documentWord, out, options);
            exito = true;
        } catch (XWPFConverterException e) {
            exito = false;
            System.out.println("Error en la conversión");
            e.printStackTrace();
        } catch (IOException e) {
            exito = false;
            System.out.println("Error creando el fichero PDF");
            e.printStackTrace();
        }
        return exito;
    }
}
