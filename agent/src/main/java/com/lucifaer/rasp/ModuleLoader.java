package com.lucifaer.rasp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URLDecoder;

public class ModuleLoader {
    private static ModuleLoader instance;
//    private static ModuleContainer raspContainer;
    static {
        Class clazz = ModuleLoader.class;
        // path值示例：　file:/opt/apache-tomcat-xxx/rasp/rasp.jar!/com/fuxi/javaagent/Agent.class
        String path = clazz.getResource("/" + clazz.getName().replace(".", "/") + ".class").getPath();
        if (path.startsWith("file:")) {
            path = path.substring(5);
        }
        if (path.contains("!")) {
            path = path.substring(0, path.indexOf("!"));
        }
        try {
            baseDirectory = URLDecoder.decode(new File(path).getParent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            baseDirectory = new File(path).getParent();
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        while (systemClassLoader.getParent() != null
                && !systemClassLoader.getClass().getName().equals("sun.misc.Launcher$ExtClassLoader")) {
            systemClassLoader = systemClassLoader.getParent();
        }
        moduleClassLoader = systemClassLoader;
    }
    ￿
    public ModuleLoader(Instrumentation inst) {
        raspContainer = new ModuleContainer();
        raspContainer.start(inst);
    }

    public static synchronized void load(Instrumentation inst) throws Throwable {
        try {
            instance = new ModuleLoader(inst);
        }catch (Throwable t) {
            instance = null;
            throw t;
        }
    }
}
