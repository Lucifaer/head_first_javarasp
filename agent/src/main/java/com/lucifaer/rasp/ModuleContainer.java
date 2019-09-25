package com.lucifaer.rasp;

import com.lucifaer.rasp.raspContainer.ContainerBoot;

import java.lang.instrument.Instrumentation;

public class ModuleContainer {
    public void start(Instrumentation inst) {
        ContainerBoot.start(inst);
    }
}
