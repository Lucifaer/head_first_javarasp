package com.lucifaer.rasp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarFile;

public class JarFileHelper {
    public static void addJarToBootstrap(Instrumentation inst) throws IOException {
        String localJarPath = getLocalJarPath();
        inst.appendToBootstrapClassLoaderSearch(new JarFile(localJarPath));
    }

    private static String getLocalJarPath() {
        URL localUrl = Agent.class.getProtectionDomain().getCodeSource().getLocation();
        String path = null;
        try {
            path = URLDecoder.decode(localUrl.getFile().replace("+", "%2B"), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }
}
