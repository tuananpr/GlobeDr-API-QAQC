package com.apis.globedr.model.general.file;

import java.io.IOException;
import java.nio.file.Path;

import de.redsix.pdfcompare.PdfComparator;
import lombok.*;

@NoArgsConstructor
public class PdfFile extends File {

    public PdfFile(Path path) {
        super(path);
    }

    public PdfFile(java.io.File file) {
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
    public Boolean compare(File expectedFile, String outputResult) {
        Boolean isEquals = false;
        try {
            isEquals = new PdfComparator(expectedFile.getFile(), getFile()).compare().writeTo(outputResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isEquals;
    }

}
