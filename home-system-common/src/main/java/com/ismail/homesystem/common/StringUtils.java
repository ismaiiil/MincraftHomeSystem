package com.ismail.homesystem.common;

public class StringUtils {
    public static String removeLineEndings(String fileContents) {
        return fileContents.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
    }
}
