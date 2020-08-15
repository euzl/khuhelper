package com.dasom.khuhelper;

public class Petition {
    String username;
    String useremail;
    String title;
    String content;

    String csId; // 충전소 id
    String csName; // 충전소 이름

    boolean isCheck;

    public Petition(String username, String useremail, String title, String content, String csId, String csName) {
        this.username = username;
        this.useremail = useremail;
        this.title = title;
        this.content = content;
        this.csId = csId;
        this.csName = csName;
        this.isCheck = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
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

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
