package com.example.whunf.at13_timeblog.model;

/**
 * Created by whunf on 2015/6/17 in PC
 */
public class ArticalDetial {
    String content;
    int user_id;
    int type;

    public ArticalDetial(String content, int user_id, int type) {
        this.content = content;
        this.user_id = user_id;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ArticalDetial{" +
                "content='" + content + '\'' +
                ", user_id=" + user_id +
                ", type=" + type +
                '}';
    }
}
