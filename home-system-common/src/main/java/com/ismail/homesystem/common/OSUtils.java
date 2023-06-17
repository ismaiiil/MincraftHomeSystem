package com.ismail.homesystem.common;

public class OSUtils {
    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

}