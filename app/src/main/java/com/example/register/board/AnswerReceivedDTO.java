package com.example.register.board;

import com.example.register.member.Member;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerReceivedDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("memberId")
    @Expose
    private Member memberId;

    @SerializedName("boardId")
    @Expose
    private int boardId;

    public AnswerReceivedDTO(int id, String content, Member memberId, int boardId) {
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

    public Member getMemberId() {
        return memberId;
    }

    public void setMemberId(Member memberId) {
        this.memberId = memberId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
