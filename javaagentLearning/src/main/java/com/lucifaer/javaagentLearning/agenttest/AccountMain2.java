package com.lucifaer.javaagentLearning.agenttest;

public class AccountMain2 {
    public static void main(String[] args) throws InterruptedException {
        for (;;) {
            new Account().operation();
            Thread.sleep(5000);
        }
    }
}
