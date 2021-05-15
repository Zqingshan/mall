package com.zqs.mall.dao;

import com.zqs.mall.model.User;

public interface FrontUserDao {
    /**
     * 通过用户名查找密码
     * 登录功能
     *
     * @param user
     * @return
     */
    User queryPwdByUserName(User user);

    /**
     * 通过用户名查找昵称
     *
     * @param nickName
     * @return
     */
    User queryNickNameByUname(User nickName);

    /**
     * 通过email查找记录
     * @param email
     * @return
     */
    User queryUserByEmail(String email);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int insertUser(User user);
}
