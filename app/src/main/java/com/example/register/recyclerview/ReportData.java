package com.example.register.recyclerview;

public class ReportData {
    private String Title;
    private String Date;
    private String Nname;
    private int id;

    public ReportData(String title, String date, String nname, int id) {
        Title = title;
        Date = date;
        Nname = nname;
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNname() {
        return Nname;
    }

    public void setNname(String nname) {
        Nname = nname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
