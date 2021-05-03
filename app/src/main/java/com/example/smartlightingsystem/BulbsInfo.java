package com.example.smartlightingsystem;

public class BulbsInfo {
    String name;
    int status;

    public BulbsInfo() {
    }

    public BulbsInfo(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
