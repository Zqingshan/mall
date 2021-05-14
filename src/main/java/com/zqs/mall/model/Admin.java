package com.zqs.mall.model;

/**
 * @description: admin数据库实体
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class Admin {
    private Integer id;
    private String username;
    private String nickname;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Admin() {
    }

    public Admin(Integer id, String username, String nickname, String password) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
