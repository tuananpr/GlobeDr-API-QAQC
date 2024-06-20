package com.apis.globedr.helper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileResourcesUtils {

    public static void test() {
        FileResourcesUtils app = new FileResourcesUtils();
        //String fileName = "database.properties";
        String fileName = Path.ACCOUNT + "signup.json";
        System.out.println("getResourceAsStream : " + fileName);
        InputStream is = app.getFileFromResourceAsStream(fileName);
        printInputStream(is);
    }

    public static void main(String[] args) {
        test();
    }

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    public InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }


    // print input stream
    private static void printInputStream(InputStream is) {
        InputStreamReader streamReader =
                new InputStreamReader(is, StandardCharsets.UTF_8);

        try {

            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getString(InputStream is) {
        InputStreamReader streamReader =
                new InputStreamReader(is, StandardCharsets.UTF_8);

        StringBuilder textBuilder = new StringBuilder();
        try {

            BufferedReader reader = new BufferedReader(streamReader);
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }


    // print a file
    private static void printFile(File file) {

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
//    private InputStream getFileFromResourceAsStream(String fileName) {
//
//        // The class loader that loaded the class
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream(fileName);
//
//        // the stream holding the file content
//        if (inputStream == null) {
//            throw new IllegalArgumentException("file not found! " + fileName);
//        } else {
//            return inputStream;
//        }
//
//    }

    // Get all paths from a folder that inside the JAR file
//    private List<Path> getPathsFromResourceJAR(String folder)
//            throws URISyntaxException, IOException {
//
//        List<Path> result;
//
//        // get path of the current running JAR
//        String jarPath = getClass().getProtectionDomain()
//                .getCodeSource()
//                .getLocation()
//                .toURI()
//                .getPath();
//        System.out.println("JAR Path :" + jarPath);
//
//        // file walks JAR
//        URI uri = URI.create("jar:file:" + jarPath);
//        FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
//        result = Files.walk(fs.getPath(folder))
//                .filter(Files::isRegularFile)
//                .collect(Collectors.toList());
//
//        return result;
//
//    }

}
