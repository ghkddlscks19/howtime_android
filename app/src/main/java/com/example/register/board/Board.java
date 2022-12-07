package com.example.register.board;

import com.example.register.member.Member;
import com.example.register.member.MemberDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.SimpleTimeZone;

public class Board {

    public Board(int id, String title, String content, String hashtag, int price, String createDate, String modifyDate, String requirement, MemberDTO memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.price = price;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.requirement = requirement;
        this.memberId = memberId;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("hashtag")
    @Expose
    private String hashtag;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("createDate")
    @Expose
    private String createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    @SerializedName("modifyDate")
    @Expose
    private String modifyDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    @SerializedName("requirement")
    @Expose
    private String requirement;

    @SerializedName("memberId")
    @Expose
    private MemberDTO memberId;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public MemberDTO getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberDTO memberId) {
        this.memberId = memberId;
    }
}
