package com.maxplus1.demo.utils;

import java.net.FileNameMap;
import java.net.URLConnection;

public class MimeUtils {

    public static String getMimeType(String fileUrl) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileUrl);
        return type==null?"text/plain":type;
    }
}
