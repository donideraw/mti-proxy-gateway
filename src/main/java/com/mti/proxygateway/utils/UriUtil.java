package com.mti.proxygateway.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UriUtil {

    public static String getPath(String uri) {
        String path = "";
        URL url;

        try {
            url = new URL(uri);
            path = url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return path;
    }

}
