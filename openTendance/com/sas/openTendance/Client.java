package com.sas.openTendance;

public class Client {
    protected String Name;
    protected int Port;

    Client(String name,int port) {
        Name = name;
        Port = port;
    }

    protected String getName() {
        return Name;
    }

    protected int getPort() {
        return Port;
    }
}
