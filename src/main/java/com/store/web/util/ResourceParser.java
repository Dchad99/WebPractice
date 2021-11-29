package com.store.web.util;

public class ResourceParser {
    private static final String rootSource = "templates";

    private ResourceParser(){}

    public static String getFileSource(String source){
        String res = rootSource;
        String file = source.substring(source.lastIndexOf("/"));
        if (file.endsWith(".css")) {
            res += "/css" + file;
        } else if (file.endsWith(".js")) {
            res += "/js" + file;
        } else if (file.endsWith(".png")) {
            res += "/img" + file;
        }
        return res;
    }

}
