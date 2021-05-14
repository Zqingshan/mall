package com.zqs.mall.service;

import com.zqs.mall.dao.AdminDao;
import com.zqs.mall.model.Admin;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.vo.AllAdminVO;
import com.zqs.mall.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class AdminServiceImpl implements AdminService {

    /**
     * 登录功能
     *
     * @param adminLoginBO
     * @return
     */
    @Override
    public int login(AdminLoginBO adminLoginBO) {
        //调用dao层
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin admin = new Admin(null, adminLoginBO.getEmail(), null, null);
        Admin user = mapper.queryAdminByUsername(admin);
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        if (user == null) {
            // 账号不存在
            return 404;
        }
        if (!user.getPassword().equals(adminLoginBO.getPwd())) {
            // 账号存在,密码不相等
            return 403;
        }
        // 账号密码正确
        return 200;
    }

    /**
     * 获取所有管理员信息
     *
     * @return
     */
    @Override
    public List<AllAdminVO> allAdmins() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        List<Admin> adminList = mapper.queryAllAdmins();
        ArrayList<AllAdminVO> allAdminVOS = new ArrayList<>();
        for (Admin admin : adminList) {
            AllAdminVO allAdminVO = new AllAdminVO(admin.getId(), admin.getUsername(), admin.getNickname(), admin.getPassword());
            allAdminVOS.add(allAdminVO);
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return allAdminVOS;
    }

    /**
     * 新增管理员
     *
     * @param adminRequest
     * @return
     */
    @Override
    public int insertAdmin(AllAdminVO adminRequest) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        // 先查询数据库有没有这条记录
        Admin selectAdmin = new Admin(null, adminRequest.getEmail(), null, null);
        Admin user = mapper.queryAdminByUsername(selectAdmin);
        if (user != null) {
            // 如果有这条记录
            return 404;
        } else {
            // 没有这条记录
            Admin admin = new Admin(null, adminRequest.getEmail(), adminRequest.getNickname(), adminRequest.getPwd());
            int result = mapper.insertAdmin(admin);
            if (0 == result) {
                // 添加失败
                return 403;
            }
        }
        // 添加成功
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return 200;
    }

    /**
     * 删除管理员账号
     *
     * @param id
     * @return
     */
    @Override
    public int deleteAdminById(Integer id) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        int effectRows = mapper.deleteAdminById(id);
        if (effectRows == 0) {
            // 删除失败
            return 404;
        }
        // 删除成功
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return 200;
    }

    /**
     * 获取管理员信息
     * 返回对象
     *
     * @param id
     * @return
     */
    @Override
    public AllAdminVO queryAdminById(Integer id) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin admin = mapper.queryAdminById(id);
        if (admin == null) {
            // 没有这条数据
            return null;
        }
        // 有这条数据
        AllAdminVO adminVO = new AllAdminVO(admin.getId(), admin.getUsername(), admin.getNickname(), admin.getPassword());
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return adminVO;
    }

    /**
     * 修改管理员信息
     *  // 有问题
     * @param adminRequest
     * @return
     */
    @Override
    public int updateAdmin(AllAdminVO adminRequest) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin admin = new Admin(adminRequest.getId(), adminRequest.getEmail(), adminRequest.getNickname(), adminRequest.getPwd());
        int result = mapper.updateAdmin(admin);
        if (result == 0) {
            // 修改失败
            return 404;
        }
        // 修改成功
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return 200;
    }


}
