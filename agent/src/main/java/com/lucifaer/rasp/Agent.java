package com.lucifaer.rasp;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArg, Instrumentation inst) throws IOException {
        init(inst);
    }

    public static void init(Instrumentation inst) throws IOException {
        JarFileHelper.addJarToBootstrap(inst);
    }
}
