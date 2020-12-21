package com.dasom.khuhelper.petition;

import java.io.Serializable;

public class Petition implements Serializable {

    String key;

    String username;
    String useremail;
    String title;
    String content;

    String csId; // 충전소 id
    String csName; // 충전소 이름
    String reply; // 관리자 답변

    boolean isCheck;

    public Petition(){}

    public Petition(String username, String useremail, String title, String content, String csId, String csName) {
        this.username = username;
        this.useremail = useremail;
        this.title = title;
        this.content = content;
        this.csId = csId;
        this.csName = csName;
        this.isCheck = false;
        this.reply = "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
