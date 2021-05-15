package com.zqs.mall.service;

import com.zqs.mall.dao.FrontUserDao;
import com.zqs.mall.model.User;
import com.zqs.mall.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
public class FrontUserServiceImpl implements FrontUserService {

    /**
     * 前台用户登录
     *
     * @param user
     * @return
     */
    @Override
    public int login(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        FrontUserDao mapper = sqlSession.getMapper(FrontUserDao.class);
        User userList = mapper.queryPwdByUserName(user);
        if (userList == null) {
            // 账号不存在
            return 403;
        }
        if (!userList.getPassword().equals(user.getPassword())) {
            // 密码错误
            return 404;
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return 200;
    }

    /**
     * 通过用户名查找昵称
     *
     * @param nickName
     * @return
     */
    @Override
    public User queryNickNameByUname(User nickName) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        FrontUserDao mapper = sqlSession.getMapper(FrontUserDao.class);
        User userNickName = mapper.queryNickNameByUname(nickName);
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return userNickName;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public int insertUser(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        FrontUserDao mapper = sqlSession.getMapper(FrontUserDao.class);
        // 先根据用户名查询有没有这条记录
        User repeat = mapper.queryUserByEmail(user.getEmail());
        if (repeat == null) {
            // 用户名没有被注册,可以注册
            int code = mapper.insertUser(user);
            if (code > 0) {
                // 注册成功
                return 200;
            }
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        // 用户名已被注册
        return 404;
    }
}
