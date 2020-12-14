package com.example.demo.model;

public class User {
    private String username;
    private String password;
    private String favourite;
    private int permission;
    private int u_id;

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getU_id() {
        return u_id;
    }

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

    public String getfavourite() {
        return favourite;
    }

    public void setfavourite(String favourite) {
        this.favourite = favourite;
    }



}
