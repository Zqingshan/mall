package com.zqs.mall.service;

import com.zqs.mall.model.User;

public interface FrontUserService {
    /**
     * 前台用户登录
     *
     * @param user
     * @return
     */
    int login(User user);

    /**
     * 通过用户名查找昵称
     *
     * @param nickName
     * @return
     */
    User queryNickNameByUname(User nickName);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    int insertUser(User user);
}
