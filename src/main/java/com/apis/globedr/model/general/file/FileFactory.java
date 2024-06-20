package com.apis.globedr.model.general.file;

import com.apis.globedr.helper.FileResourcesUtils;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public class FileFactory {

    /*
        The resource URL is not working in the JAR
        If we try to access a file that is inside a JAR,
        It throws NoSuchFileException (linux), InvalidPathException (Windows)

        Resource URL Sample: file:java-io.jar!/json/file1.json
     */
    private static java.io.File getFileFromResource(Object fileName) {
        if(fileName != null){
            if(fileName instanceof String){
                if(!fileName.toString().isEmpty()){
                    ClassLoader classLoader = FileResourcesUtils.class.getClassLoader();
                    URL resource = classLoader.getResource(fileName.toString());
                    if (resource == null) {
                        throw new IllegalArgumentException("file not found! " + fileName);
                    } else {

                        // failed if files have whitespaces or special characters
                        //return new File(resource.getFile());

                        try {
                            return new java.io.File(resource.toURI());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return null;
    }


    public static File getFile(String path) {
        return getFile(getFileFromResource(path).toPath());
    }


    public static File getFile(Path path) {
        String extension = Optional.ofNullable(path.toFile().getName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(path.toFile().getName().lastIndexOf(".") + 1)).get();

        if (extension == null || extension.isEmpty()) return new NullFile(path);
        switch (extension.toLowerCase()) {
            case "pdf":
                return new PdfFile(path);
            default:
                return new ImageFile(path);
        }

    }


    public static File getFile(java.io.File file) {

        String extension = Optional.ofNullable(file.getName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(file.getName().lastIndexOf(".") + 1)).get();

        switch (extension.toLowerCase()) {
            case "pdf":
                return new PdfFile(file);
            default:
                return new ImageFile(file);
        }
    }

}
