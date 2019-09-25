package com.lucifaer.rasp.raspContainer;

import com.lucifaer.rasp.raspContainer.transformer.CustomClassTransformer;

import java.lang.instrument.Instrumentation;

public class ContainerBoot {

    private CustomClassTransformer transformer;

    public static void start(Instrumentation inst) {
        initTransformer(inst);
    }

    private static void initTransformer(Instrumentation inst) {
        transformer = new CustomClassTransformer(inst);
    }
}
