package util;

import java.util.Optional;

public class StringHelper {

    public static String removeSquareBrackets(String input){
        return input
                .replaceAll("\"\\{","{")
                .replaceAll("\\\\","")
                .replaceAll("\"\\[", "[")
                .replaceAll("]\"","]");
    }

    public static String removeAllSpace(String input){
        return input.replaceAll("\\s","");
    }


    public static String removeString(String content, String... removedText) {
        for (String text : removedText) {
            content = content.replaceAll(text, "");
        }
        return content;
    }

    public static String capitalizeWord(String str){
        str = str.replaceAll("_", " ").toLowerCase();
        String[] words =str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim().replaceAll(" ", "");
    }

    public static String upperCaseTheFirstCharacter(String str){
        String first=str.substring(0,1).toUpperCase().trim();
        String afterfirst=str.substring(1).toLowerCase().trim();
        return first + afterfirst ;
    }

    public static String capitalizeFirstWord(String str){
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String removeLastCharacter(String str) {
        return Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
    }


    public static String getLastcharacter(String string){
        return string.substring(string.length() - 1);
    }


    public static String decodeUnicode(String content){
        String str = content.split(" ")[0];
        str = str.replace("\\","");
        String[] arr = str.split("u");
        String text = "";
        for(int i = 1; i < arr.length; i++){
            int hexVal = Integer.parseInt(arr[i], 16);
            text += (char)hexVal;
        }
        return text;
    }

}
