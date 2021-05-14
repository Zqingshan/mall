package com.zqs.mall.service;

import com.zqs.mall.dao.AdminDao;
import com.zqs.mall.model.Admin;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class AdminServiceImpl implements AdminService {

    @Override
    public int login(AdminLoginBO loginBO) {
        // 调用dao层
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao adminDao = sqlSession.getMapper(AdminDao.class);

        Admin admin = new Admin(null, loginBO.getEmail(), null, loginBO.getPwd());
        int login = adminDao.login(admin);
        if (login == 1) {
            // 登录成功
            return 200;
        }
        // 登录失败
        return 404;
    }
}
