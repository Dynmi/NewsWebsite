package com.example.demo.model;

public class Commentreport {
    private int report_id;
    private String u1_name;  //举报者
    private String u_name; //被举报用户名
    private int n_id;//新闻id

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public int getN_id() {
        return n_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public String getU1_name() {
        return u1_name;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU1_name(String u1_name) {
        this.u1_name = u1_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
