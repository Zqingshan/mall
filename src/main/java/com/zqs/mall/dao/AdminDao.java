package com.zqs.mall.dao;

import com.zqs.mall.model.admin.Admin;

import java.util.List;

public interface AdminDao {
    /**
     * 管理员登录验证
     *
     * @param admin
     * @return
     */
    Admin queryAdminByUsername(Admin admin);

    /**
     * 获取所有管理员信息
     *
     * @return
     * @param admin
     */
    List<Admin> queryAllAdmins(Admin admin);

    /**
     * 新增管理员
     *
     * @param admin
     * @return
     */
    int insertAdmin(Admin admin);

    /**
     * 通过id删除管理员
     *
     * @param id
     * @return
     */
    int deleteAdminById(Integer id);

    /**
     * 通过id查询管理员信息
     *
     * @param id
     * @return
     */
    Admin queryAdminById(Integer id);

    /**
     * 新增管理员
     *
     * @param admin
     * @return
     */
    int updateAdmin(Admin admin);

    /**
     * 通过username查找nickname
     * @param userName
     * @return
     */
    Admin queryNickNameByUname(Admin userName);
}
