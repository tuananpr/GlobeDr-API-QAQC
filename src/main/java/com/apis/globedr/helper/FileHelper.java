package com.apis.globedr.helper;

import com.rest.core.debug.CucumberReport;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.rest.core.debug.Logger;

public class FileHelper {


    public static boolean saveFileFromURL(String sourceUrl, String destination) {

        try {
            URL url = new URL(sourceUrl);
            FileUtils.copyURLToFile(url, new File(destination));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static File get(String path) {
        if (isExistFile(path)) {
            return new File(path);
        }
        return null;
    }

    public static void writeFile(String path, String content) {
        Path filePath = Paths.get(path);

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            Files.write(filePath, content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /*
     * Author: Duy Ngo Function Name: readFile Description: read data of properties
     * file from path. Param1 : path file ( type : String ) Returns Value: data of
     * properties file ( type : Properties class )
     *
     * Example for reading browser settings properties :
     * FileHelper.readFile("E:\\test\\Globedr\\config\\browser.settings.properties")
     */
    public static Properties loadProperties(String path) {
        try {
            if (isExistFile(path)) {
                Properties prop = new Properties();
                BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                prop.load(ip);
                return prop;
            } else {
                throw new Exception(String.format("Error : Not found properties file %s ", path));
            }

        } catch (IOException e) {
            System.out.println(String.format("Error while reading properties file %s: %s ", path, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Author: Duy Ngo Function Name: getPropertiesValue Description: get value from
     * properties file Param1 : path file ( type : String ) Param2 : the key into
     * section ( type : String ) Param3 : section name ( type : String )
     *
     * Returns Value: value of key into section name ( type : String )
     *
     * Example: get "Chrome" value of "driver" key into "chrome.local" section.
     * [chrome.local] mode=Local driver=Chrome provider=Selenium
     * executable=data/drivers/chromedriver.exe
     *
     * FileHelper.getPropertiesValue(
     * "E:\\test\\Globedr\\config\\browser.settings.properties", "driver",
     * "chrome.local")
     */
    public static String getPropertiesValue(String path, String key, String sectionName) {

        if (isExistFile(path)) {
            Ini ini;
            try {

                ini = new Ini(new FileReader(path));
                return ini.get(sectionName, key);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /*
     * Author: Duy Ngo Function Name: isExistFile Description: check whether a file
     * exists ? Param1 : path file ( type : String )
     *
     * Returns Value: Return true if file is existed Return false if file is not
     * existed Example: FileHelper.isExistFile(
     * "E:\\test\\Globedr\\config\\browser.settings.properties")
     */
    public static boolean isExistFile(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static boolean isEmptyFile(String path) {
        File f = new File(path);
        return f.exists() && f.length() != 0;
    }

    public static String getFileName(File file) {
        return file.getName();
    }

    public static String getFileName(String path) {
        Path p = Paths.get(path);
        return p.getFileName().toString();
    }

    public static void createFolder(String path) {
        try {
            File theDir = new File(path);
            theDir.mkdir();
        } catch (Exception e) {
            System.out.println(
                    String.format("An error occured while creating folder with path %s : %s", path, e.getMessage()));
        }
    }

    public static List<File> getFilesFromFolder(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            }
        });


        return Arrays.asList(listOfFiles);
    }

    public static void delete(String path) {
        if (isExistFile(path)) {
            File f = new File(path);
            f.delete();
        }
    }


    public static String readFileToString(String fileName) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            // delete the last new line separator
            //stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            Logger.getInstance().info(String.format("Load file %s:\n%s", fileName, stringBuilder.toString()));
            CucumberReport.write(String.format("Load file %s:\n%s", fileName, stringBuilder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /*
        The resource URL is not working in the JAR
        If we try to access a file that is inside a JAR,
        It throws NoSuchFileException (linux), InvalidPathException (Windows)

        Resource URL Sample: file:java-io.jar!/json/file1.json
     */
    public static File getFileFromResource(Object fileName) {
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
                            return new File(resource.toURI());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return null;
    }

}
