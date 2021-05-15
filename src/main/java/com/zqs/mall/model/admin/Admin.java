package com.zqs.mall.model.admin;

import java.io.Serializable;

/**
 * (Admin)实体类
 *
 * @author makejava
 * @since 2021-05-14 18:55:34
 */
public class Admin implements Serializable {
    // private static final long serialVersionUID = 180638179283312905L;
    
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