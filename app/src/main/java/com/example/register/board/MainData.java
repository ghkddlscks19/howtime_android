package com.example.register.board;

public class MainData {

    private String Tag;
    private String Title;
    private String Date;
    private String Nname;

    public MainData(String tag, String title, String date, String nname) {
        Tag = tag;
        Title = title;
        Date = date;
        Nname = nname;
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
