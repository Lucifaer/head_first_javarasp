package com.lucifaer.javaagentLearning.agent;

import com.lucifaer.javaagentLearning.agenttest.Account;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class AgentMainTranceAgent {
    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : "+ agentArgs);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                System.out.println("agentmain load Class : " + className);
                return classfileBuffer;
            }
        }, true);
        instrumentation.retransformClasses(Account.class);
    }
}
