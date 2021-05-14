package com.zqs.mall.model.bo;

/**
 * @description: admin登录请求报文的请求体参数
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class AdminLoginBO {
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

    @Override
    public String toString() {
        return "AdminLoginBO{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
