package com.example.register.domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("memberId")
    @Expose
    private MemberDTO memberId;

    @SerializedName("boardId")
    @Expose
    private int boardId;

    public Answer(int id, String content, MemberDTO memberId, int boardId) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
