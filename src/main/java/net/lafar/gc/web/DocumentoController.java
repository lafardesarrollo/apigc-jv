package net.lafar.gc.web;

import net.lafar.gc.service.FileSystemStorageService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/documento")
public class DocumentoController {

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("convertir")
    public void convertir(){
        fileSystemStorageService.convertir();
        /*
        File archivoWord = new File("D:\\documentos_prueba\\manual.docx");
        File archivoPDF = new File("D:\\documentos_prueba\\manual.pdf");

        XWPFDocument document = fileSystemStorageService.leerDocx(archivoWord);
        boolean result = fileSystemStorageService.convertirPDF(archivoPDF, document);
        if (result){
            return "Se convirtio";
        } else {
            return "No se convirtio";
        }

         */
    }
}
