package com.example.register.listview;

public class ListViewItem {

    private String editnicknameStr; //리스트뷰 닉네임
    private String editmenuStr; //리스트뷰 수정 삭제 메뉴
    private String editcomStr; //리스트뷰 댓글
    private String editidStr; //댓글의 아이디

    public void setEditnickname(String editNickname){
        editnicknameStr = editNickname;
    }
    public void setEditmenu(String editmenu){
        editmenuStr = editmenu;
    }
    public void setEditcom(String editcom){
        editcomStr = editcom;
    }
    public void setEditid(String editid) { editidStr = editid; }


    public String getEditNickname(){
        return this.editnicknameStr;
    }
    public String getEditmenu() { return this.editmenuStr; }
    public String getEditcom(){
        return this.editcomStr;
    }
    public String getEditid() { return this.editidStr; }
}
