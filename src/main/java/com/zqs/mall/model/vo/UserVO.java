package com.zqs.mall.model.vo;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
public class UserVO {
    private Integer code;
    private String token;
    private String name;



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserVO() {
    }

    public UserVO(Integer code, String token, String name) {
        this.code = code;
        this.token = token;
        this.name = name;
    }
}
