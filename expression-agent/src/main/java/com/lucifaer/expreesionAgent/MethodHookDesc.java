package com.lucifaer.expreesionAgent;

public class MethodHookDesc {
    private String hookClassName;
    private String hookMethodName;
    private String hookMethodArgTypeDesc;

    public MethodHookDesc(String hookClassName, String hookMethodName, String hookMethodArgTypeDesc) {
        this.hookClassName = hookClassName;
        this.hookMethodName = hookMethodName;
        this.hookMethodArgTypeDesc = hookMethodArgTypeDesc;
    }

    public String getHookClassName() {
        return hookClassName;
    }

    public void setHookClassName(String hookClassName) {
        this.hookClassName = hookClassName;
    }

    public String getHookMethodName() {
        return hookMethodName;
    }

    public void setHookMethodName(String hookMethodName) {
        this.hookMethodName = hookMethodName;
    }

    public String getHookMethodArgTypeDesc() {
        return hookMethodArgTypeDesc;
    }

    public void setHookMethodArgTypeDesc(String hookMethodArgTypeDesc) {
        this.hookMethodArgTypeDesc = hookMethodArgTypeDesc;
    }
}
