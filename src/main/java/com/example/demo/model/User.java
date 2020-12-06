package com.example.demo.model;

public class User {
    private String username;
    private String password;
    private int favourite;
    private String collection;
    private int permission;


    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getfavourite() {
        return favourite;
    }

    public void setfavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getcollection() {
        return collection;
    }

    public void setcollection(String collection) {
        this.collection = collection;
    }

}
