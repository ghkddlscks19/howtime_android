package com.example.register.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Member {

    //싱글톤 객체
    private static final Member member = new Member();

    public static Member setInstance(String studentNum, String password, String name, String nickname, String gender, String email) {
        member.setStudentNum(studentNum);
        member.setPassword(password);
        member.setName(name);
        member.setNickname(nickname);
        member.setGender(gender);
        member.setEmail(email);
        return member;
    }

    public static Member getInstance() {
        return member;
    }

    @SerializedName("studentNum")
    @Expose
    private String studentNum;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("email")
    @Expose
    private String email;

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
