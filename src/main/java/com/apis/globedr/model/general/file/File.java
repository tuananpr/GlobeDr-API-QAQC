package com.apis.globedr.model.general.file;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public abstract class File {
    @JsonSerialize(using = FileCustomSerializer.class, as=java.io.File.class)
    private java.io.File file;
    @JsonSerialize(using = FileCustomSerializer.class, as=java.io.File.class)
    private java.io.File files;
    private long chunkSize;
    private long totalSize;
    private String fileName;
    private long chunkNumber;
    private Integer resumableTotalChunks;
    private String resumableType;
    private String resumableIdentifier;
    private String resumableRelativePath;

    public static class FileCustomSerializer extends JsonSerializer<java.io.File> {

        @Override
        public void serialize(java.io.File value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider)
                throws IOException {
            jsonGenerator.writePOJO(value);
        }
    }

    public File(){}
    public File(Path path) {
        setFile(path.toFile());
        setFiles(path.toFile());
        init();
    }

    public File(java.io.File file) {
        setFile(file);
        init();
    }

    private void init() {
        setChunkSize(getFile().getTotalSpace());
        setTotalSize(getFile().getTotalSpace());
        setFileName(getFile().getName());
        setChunkNumber(1);
        setResumableTotalChunks(1);
        updateResumableType();
        setResumableIdentifier(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
        setResumableRelativePath(getFile().getName());
    }

    public String getFileName() {
        return this.fileName;
    }


    public String getExtension() {
        String extension = Optional.ofNullable(getFileName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(getFileName().lastIndexOf(".") + 1)).get();

        return extension;
    }

    public String getResumableIdentifier() {
        return this.resumableIdentifier;
    }

    public long getSize() {
        return getFile().getTotalSpace();
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public long getChunkSize() {
        return this.chunkSize;
    }

    public String getResumableType() {
        return this.resumableType;
    }

    protected abstract void updateResumableType();

    public Map<String, Object> getMapToUpload() {
        Map<String, Object> body = new HashMap<>();
        body.put("chunkSize", getChunkSize());
        body.put("totalSize", getTotalSize());
        body.put("file", getFile());
        body.put("fileName", getFileName());
        body.put("chunkNumber", 1);
        body.put("resumableTotalChunks", 1);
        body.put("resumableType", getResumableType());
        body.put("resumableIdentifier", getResumableIdentifier());
        body.put("resumableRelativePath", getFileName());
        return body;
    }



    public abstract <T> T compare(T t);

    public abstract Boolean compare(File expectedFile, String outputPath);


    public void setFile(java.io.File file) {
        this.file = file;
    }

    public java.io.File getFile() {
        return this.file;
    }

    public void save(String dest) {

        try {
            Files.copy(getFile().toPath(), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toBase64() {
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }


    public boolean isExisted() {
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.file.getPath();
    }


}
