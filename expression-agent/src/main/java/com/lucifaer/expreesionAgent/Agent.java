package com.lucifaer.expreesionAgent;

import jdk.internal.org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class Agent implements Opcodes {
    private static List<MethodHookDesc> expClassList = new ArrayList<MethodHookDesc>();

    static {
        expClassList.add(new MethodHookDesc("org.mvel2.MVELInterpretedRuntime", "parse",
                "()Ljava/lang/Object;"));
        expClassList.add(new MethodHookDesc("ognl.Ognl", "parseExpression",
                "(Ljava/lang/String;)Ljava/lang/Object;"));
        expClassList.add(new MethodHookDesc("org.springframework.expression.spel.standard.SpelExpression", "<init>",
                "(Ljava/lang/String;Lorg/springframework/expression/spel/ast/SpelNodeImpl;" +
                        "Lorg/springframework/expression/spel/SpelParserConfiguration;)V"));
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("agentArgs : " + agentArgs);
        instrumentation.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                final String class_name = className.replace("/", ".");

                for (final MethodHookDesc methodHookDesc : expClassList) {
                    if (methodHookDesc.getHookClassName().equals(class_name)) {
                        final ClassReader classReader = new ClassReader(classfileBuffer);
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                        final int api = ASM5;

                        try {
                            ClassVisitor classVisitor = new ClassVisitor(api, classWriter) {
                                @Override
                                public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                                    final MethodVisitor methodVisitor = super.visitMethod(i, s, s1, s2, strings);

                                    if (methodHookDesc.getHookMethodName().equals(s) && methodHookDesc.getHookMethodArgTypeDesc().equals(s1)) {
                                        return new MethodVisitor(api, methodVisitor) {
                                            @Override
                                            public void visitCode() {
                                                if ("ognl.Ognl".equals(class_name)) {
                                                    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                                                }else {
                                                    mv.visitVarInsn(Opcodes.ALOAD, 1);
                                                }
                                                methodVisitor.visitMethodInsn(
                                                        Opcodes.INVOKESTATIC, Agent.class.getName().replace("/", "."), "expression", "(Ljava/lang/String)V", false
                                                );
                                            }
                                        };
                                    }
                                    return methodVisitor;
                                }
                            };
                            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
                            classfileBuffer = classWriter.toByteArray();
                        }catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
                return classfileBuffer;
            }
        });
    }

    public static void expression(String exp) {
        System.err.println("---------------------------------EXP-----------------------------------------");
        System.err.println(exp);
        System.err.println("---------------------------------调用链---------------------------------------");

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : elements) {
            System.err.println(element);
        }

        System.err.println("-----------------------------------------------------------------------------");
    }
}
