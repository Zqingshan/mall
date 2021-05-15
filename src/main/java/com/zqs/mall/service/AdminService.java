package com.zqs.mall.service;

import com.zqs.mall.model.Admin;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.vo.AllAdminVO;

import java.util.List;

public interface AdminService {

    /**
     * 登录功能验证
     * @param admin
     * @return
     */
    int login(Admin admin);

    /**
     * 获取所有管理员信息
     * @param admin
     * @return
     */
    List<AllAdminVO> allAdmins(Admin admin);

    /**
     * 新增管理员
     * @param adminRequest
     * @return
     */
    int insertAdmin(AllAdminVO adminRequest);

    /**
     * 通过id删除管理员
     * @param id
     * @return
     */
    int deleteAdminById(Integer id);

    /**
     * 通过id查找管理员,同修改一起使用
     * @param id
     * @return
     */
    AllAdminVO queryAdminById(Integer id);

    /**
     * 修改管理员信息
     * @param adminRequest
     * @return
     */
    int updateAdmin(AllAdminVO adminRequest);

}
