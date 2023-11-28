package com.example.hanna;

public class task {
    private String Name;
    private String Priority;
    private String status;

    public task(String name, String Priority) {
        this.Name=name;
        this.Priority=Priority;
        this.status="unfinished";
    }
    public String getName() {
        return Name;
    }
    public String getPriority() {
        return Priority;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return Name;
    }
}