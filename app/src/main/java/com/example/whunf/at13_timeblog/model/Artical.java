package com.example.whunf.at13_timeblog.model;

/**
 * Created by whunf on 2015/6/12 in PC
 */
public class Artical {
    String a_id;
    String reply_count;
    String time;
    String title;
    String u_name;
    String u_photo;

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_photo() {
        return u_photo;
    }

    public void setU_photo(String u_photo) {
        this.u_photo = u_photo;
    }

    @Override
    public String toString() {
        return "Artical{" +
                "a_id='" + a_id + '\'' +
                ", reply_count='" + reply_count + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_photo='" + u_photo + '\'' +
                '}';
    }
}
