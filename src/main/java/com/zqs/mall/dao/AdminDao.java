package com.zqs.mall.dao;

import com.zqs.mall.model.Admin;

import java.util.List;

public interface AdminDao {
    // 管理员登录验证
    Admin queryAdminByUsername(Admin admin);

    // 获取所有管理员信息
    List<Admin> queryAllAdmins();

    // 新增管理员
    int insertAdmin(Admin admin);

    // 删除管理员
    int deleteAdminById(Integer id);

    Admin queryAdminById(Integer id);

    int updateAdmin(Admin admin);
}
