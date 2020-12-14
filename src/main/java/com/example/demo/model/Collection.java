package com.example.demo.model;

public class Collection {
    private int n_id;
    private int u_id;
    private int c_id;
    private String title;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public int getN_id() {
        return n_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }
}
