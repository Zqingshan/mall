package com.zqs.mall.model;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
public class User {
    private Integer id;

    private String email;

    private String nickname;

    private String password;

    private String receiver;

    private String address;

    private String telephone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User() {
    }

    public User(Integer id, String email, String nickname, String password, String receiver, String address, String telephone) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.receiver = receiver;
        this.address = address;
        this.telephone = telephone;
    }
}
