package com.zqs.mall.model.bo;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class UserLoginBO {
    private String email;
    private String pwd;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserLoginBO() {
    }

    public UserLoginBO(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
