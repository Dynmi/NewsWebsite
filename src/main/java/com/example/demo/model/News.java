package com.example.demo.model;

public class News {
    private  int n_id;
    private String title;
    private String time;
    private String source;
    private String contents;
    private String category;

    public int getN_id() {
        return n_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public String getCategory() {
        return category;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

