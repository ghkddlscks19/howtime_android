package com.example.register.listview;

public class ListViewItem {

    private String editnicknameStr; //리스트뷰 닉네임
    private String editdateStr; //
    private String editcomStr; //리스트뷰 댓글
    private int editid; //댓글의 아이디

    public void setEditnickname(String editNickname){
        editnicknameStr = editNickname;
    }
    public void setEditdate(String editdate){
        editdateStr = editdate;
    }
    public void setEditcom(String editcom){
        editcomStr = editcom;
    }
    public void setEditid(int editid) { this.editid = editid; }


    public String getEditNickname(){
        return this.editnicknameStr;
    }
    public String getEditdate() { return this.editdateStr; }
    public String getEditcom(){
        return this.editcomStr;
    }
    public int getEditid() { return this.editid; }

    public ListViewItem(String editnicknameStr, String editdateStr, String editcomStr, int editid) {
        this.editnicknameStr = editnicknameStr;
        this.editdateStr = editdateStr;
        this.editcomStr = editcomStr;
        this.editid = editid;
    }
}
