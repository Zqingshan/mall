package com.zqs.mall.model;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class Result {
    private Integer code;
    private String massage;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(Integer code, String massage, Object data) {
        this.code = code;
        this.massage = massage;
        this.data = data;
    }
}
