package com.zqs.mall.model.vo;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class AllAdminVO {
    private Integer id;
    private String email;
    private String nickname;
    private String pwd;

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public AllAdminVO() {
    }

    public AllAdminVO(Integer id, String email, String nickname, String pwd) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.pwd = pwd;
    }
}
