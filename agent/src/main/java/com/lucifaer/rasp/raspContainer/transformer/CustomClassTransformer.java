package com.lucifaer.rasp.raspContainer.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class CustomClassTransformer implements ClassFileTransformer {

    private static final String SCAN_ANNOTATION_PACKAGE = "com.lucifaer.rasp.hook";
    private Instrumentation inst;

    public CustomClassTransformer(Instrumentation inst) {
        this.inst = inst;

    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}
