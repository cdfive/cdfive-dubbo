package com.cdfive.framework.util;

public abstract class FileUtil {

    public static String getFileNameWithoutExt(String fileName) {
        String fileNameWithoutExt;
        int i = fileName.lastIndexOf(".");
        if (i > -1) {
            fileNameWithoutExt = fileName.substring(0, i);
        } else {
            fileNameWithoutExt = fileName;
        }
        return fileNameWithoutExt;
    }

    public static String getFileNameExt(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i > -1) {
            return fileName.substring(i + 1);
        }
        return "";
    }
}
