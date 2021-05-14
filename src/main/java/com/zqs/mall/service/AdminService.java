package com.zqs.mall.service;

import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.vo.AllAdminVO;

import java.util.List;

public interface AdminService {

    int login(AdminLoginBO adminLoginBO);

    List<AllAdminVO> allAdmins();

    int insertAdmin(AllAdminVO adminRequest);

    int deleteAdminById(Integer id);

    AllAdminVO queryAdminById(Integer id);

    int updateAdmin(AllAdminVO adminRequest);
}
