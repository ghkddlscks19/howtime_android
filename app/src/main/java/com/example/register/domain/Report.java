package com.example.register.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("attackerNickname")
    @Expose
    private String attackerNickname;

    @SerializedName("createDate")
    @Expose
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    @SerializedName("modifyDate")
    @Expose
    private String modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("memberId")
    @Expose
    private MemberDTO memberId;


    public Report(Integer id, String title, String attackerNickname, String createDate, String modifyDate, String content, MemberDTO memberId) {
        this.id = id;
        this.title = title;
        this.attackerNickname = attackerNickname;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.content = content;
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttackerNickname() {
        return attackerNickname;
    }

    public void setAttackerNickname(String attackerNickname) {
        this.attackerNickname = attackerNickname;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MemberDTO getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberDTO memberId) {
        this.memberId = memberId;
    }
}
