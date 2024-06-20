package com.apis.globedr.model.general.file;

import lombok.*;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
public class NullFile extends File {
    public NullFile(Path path) {
        super(path);
    }

    public NullFile(java.io.File file) {
        super(file);
    }


    @Override
    public <T> T compare(T t) {
        return null;
    }

    @Override
    public Boolean compare(File expectedFile, String outputPath) {
        return null;
    }


    public long getSize() {
        return getFile().getTotalSpace();
    }

    public long getTotalSize() {
        return getSize();
    }

    public long getChunkSize() {
        return getFile().getTotalSpace();
    }

    @Override
    protected void updateResumableType() {

    }

    public Map<String, Object> getMapToUpload() {
        Map<String, Object> body = new HashMap<>();
        body.put("chunkSize", null);
        body.put("totalSize", null);
        body.put("file", null);
        body.put("fileName", null);
        body.put("chunkNumber", null);
        body.put("resumableTotalChunks", null);
        body.put("resumableType", null);
        body.put("resumableIdentifier", null);
        body.put("resumableRelativePath", null);
        return body;
    }



}
