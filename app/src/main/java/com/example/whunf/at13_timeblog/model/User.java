package com.example.whunf.at13_timeblog.model;

/**
 * Created by whunf on 2015/6/9 in PC
 */
public class User {
    public static String birth;
    public static String id;
    public static String nick;
    public static String phone;
    public static String photo;
    public static String sex;
    public static String userName;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "birth='" + birth + '\'' +
                ", id='" + id + '\'' +
                ", nick='" + nick + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", sex='" + sex + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
