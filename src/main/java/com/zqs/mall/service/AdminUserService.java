package com.zqs.mall.service;

import com.zqs.mall.model.User;
import com.zqs.mall.model.vo.AllUserVO;

import java.util.List;

public interface AdminUserService {
    /**
     * 获取所有用户信息
     *
     * @param user
     * @return
     */
    List<AllUserVO> queryAllUser(User user);

    /**
     * 通过id删除用户账号
     *
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 通过用户昵称搜索用户信息
     *
     * @param nickName
     * @return
     */
    List<AllUserVO> queryUserByNickName(String nickName);
}
