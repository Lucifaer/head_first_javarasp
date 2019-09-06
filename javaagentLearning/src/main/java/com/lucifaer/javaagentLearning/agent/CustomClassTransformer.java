package com.lucifaer.javaagentLearning.agent;

import jdk.internal.org.objectweb.asm.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class CustomClassTransformer implements ClassFileTransformer {
    private Instrumentation inst;
    public CustomClassTransformer(Instrumentation inst) {
        this.inst = inst;
        inst.addTransformer(this, true);
    }

    public void retransform() throws UnmodifiableClassException {
        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
            if ("com.lucifaer.javaagentLearning.agenttest.Account".equals(clazz.getName())) {
                if (inst.isModifiableClass(clazz) && !clazz.getName().startsWith("java.lang.invoke.LambdaForm")) {
                    inst.retransformClasses(clazz);
                }
            }
        }
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        return new byte[0];
        System.out.println("In transform");
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new CustomVisitor(cw);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        classfileBuffer = cw.toByteArray();
        FileOutputStream fos = null;
        return classfileBuffer;
    }
}

class CustomVisitor extends ClassVisitor implements Opcodes {
    public CustomVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
//        return super.visitMethod(i, s, s1, s2, strings);
        final MethodVisitor mv = super.visitMethod(i, s, s1, s2, strings);
        if ("operation".equals(s)) {
            return new MethodVisitor(ASM5, mv) {
                @Override
                public void visitCode() {
                    super.visitCode();
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("CALL " + "method");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                }
            };
//            mv.visitCode();
//            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitLdcInsn("CALL " + "method");
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//            mv.visitEnd();
        }
        return mv;
    }
}