package com.lucifaer.javaagentLearning.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMainTranceAgent2 {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        CustomClassTransformer transformer = new CustomClassTransformer(inst);
        transformer.retransform();
    }
}
