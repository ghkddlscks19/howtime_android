package com.example.register.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("memberId")
    @Expose
    private String memberId;

    @SerializedName("boardId")
    @Expose
    private int boardId;

    public AnswerDTO(String content, String memberId, int boardId) {
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
