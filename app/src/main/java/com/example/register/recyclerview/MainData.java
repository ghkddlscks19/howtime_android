package com.example.register.recyclerview;

public class MainData {

    private String Tag;
    private String Title;
    private String Date;
    private String Nname;
    private int id;


    public MainData(String tag, String title, String date, String nname, int id) {
        Tag = tag;
        Title = title;
        Date = date;
        Nname = nname;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
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
}
