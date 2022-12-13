package com.example.register.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportAnswerReceivedDTO {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("createDate")
    @Expose
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

    @SerializedName("memberId")
    @Expose
    private MemberDTO memberId;

    @SerializedName("reportId")
    @Expose
    private int reportId;

    public ReportAnswerReceivedDTO(int id, String content, String createDate, MemberDTO memberId, int reportId) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.memberId = memberId;
        this.reportId = reportId;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public MemberDTO getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberDTO memberId) {
        this.memberId = memberId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
}
