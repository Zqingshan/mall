package com.zqs.mall.dao;

import com.zqs.mall.model.User;

import java.util.List;

public interface AdminUserDao {

    /**
     * 获取所有用户信息
     *
     * @return
     */
    List<User> queryAllUser(User user);

    /**
     * 通过id删除用户账号
     *
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

}
