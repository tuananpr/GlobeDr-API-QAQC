package com.apis.globedr.model.general.file;

import com.itextpdf.html2pdf.HtmlConverter;
import com.apis.globedr.helper.Path;
import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@NoArgsConstructor
public class HtmlFile extends File {

    public HtmlFile(String content) {
        String fileTemp = Path.TARGET + "temp.html";
        try {
            Files.write(Paths.get(fileTemp), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFile(new java.io.File(fileTemp));
    }

    public HtmlFile(java.nio.file.Path path) {
        super(path);
    }

    public HtmlFile(java.io.File file) {
        super(file);
    }


    @Override
    protected void updateResumableType() {
        setResumableType("application/pdf");
    }


    @Override
    public <T> T compare(T t) {
        return null;
    }

    @Override
    public Boolean compare(File expectedFile, String outputPath) {
        return null;
    }


    public PdfFile convertToPdf() {
        java.io.File pdfFile = new java.io.File(Path.TARGET + "temp2.pdf");
        try {
            HtmlConverter.convertToPdf(getFile(), pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PdfFile(pdfFile);
    }
}
