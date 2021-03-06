package com.zqs.mall.service;

import com.zqs.mall.dao.AdminDao;
import com.zqs.mall.model.admin.Admin;
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
     * @param admin
     * @return
     */
    @Override
    public int login(Admin admin) {
        //调用dao层
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin adminParam = new Admin(null, admin.getUsername(), null, admin.getPassword());
        Admin user = mapper.queryAdminByUsername(adminParam);
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        if (user == null) {
            // 账号不存在,旧密码错误
            return 404;
        }
        if (!user.getPassword().equals(admin.getPassword())) {
            // 账号存在,但是新密码与旧密码不相等
            return 403;
        }
        // 修改密码的逻辑
        if (user.getPassword().equals(admin.getPassword())) {
            // 新密码与旧密码相同
            return 405;
        }
        // 账号密码正确, 旧密码正确
        return 200;
    }

    /**
     * 获取所有管理员信息
     *
     * @return
     */
    @Override
    public List<AllAdminVO> allAdmins(Admin admin) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        List<Admin> adminList = mapper.queryAllAdmins(admin);
        ArrayList<AllAdminVO> allAdminVOS = new ArrayList<>();
        for (Admin ad : adminList) {
            AllAdminVO allAdminVOResult = new AllAdminVO(ad.getId(), ad.getUsername(), ad.getNickname(), ad.getPassword());
            allAdminVOS.add(allAdminVOResult);
        }
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return allAdminVOS;
    }

    /**
     * 新增管理员
     *
     * @param admin
     * @return
     */
    @Override
    public int insertAdmin(Admin admin) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        // 先查询数据库有没有这条记录
        Admin selectAdmin = new Admin(null, admin.getUsername(), null, null);
        Admin user = mapper.queryAdminByUsername(selectAdmin);
        if (user != null) {
            // 如果有这条记录
            return 404;
        } else {
            // 没有这条记录
            Admin adminList = new Admin(null, admin.getUsername(), admin.getNickname(), admin.getPassword());
            int result = mapper.insertAdmin(adminList);
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
     * 通过id获取管理员信息
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
     *
     * @param admin
     * @return
     */
    @Override
    public int updateAdmin(Admin admin) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin adminResult = new Admin(admin.getId(), admin.getUsername(), admin.getNickname(), admin.getPassword());
        int result = mapper.updateAdmin(adminResult);
        if (result == 0) {
            // 修改失败
            return 404;
        }
        // 修改成功
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return 200;
    }

    /**
     * 通过username查找昵称
     *
     * @param userName
     * @return
     */
    @Override
    public Admin queryNickNameByUname(Admin userName) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AdminDao mapper = sqlSession.getMapper(AdminDao.class);
        Admin nickName = mapper.queryNickNameByUname(userName);
        sqlSession.commit();
        MyBatisUtils.closeSession(sqlSession);
        return nickName;
    }


}
