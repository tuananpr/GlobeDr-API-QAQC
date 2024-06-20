package com.apis.globedr.model.general.file;

import lombok.*;

import java.nio.file.Path;

@NoArgsConstructor
public class ImageFile extends File {


    public ImageFile(Path path) {
        super(path);
    }

    public ImageFile(java.io.File file) {
        super(file);
    }


    @Override
    protected void updateResumableType() {
        setResumableType("image/" + getExtension());
    }


    @Override
    public <T> T compare(T t) {
        return null;
    }

    @Override
    public Boolean compare(File expectedFile, String outputPath) {
        return null;
    }


}
