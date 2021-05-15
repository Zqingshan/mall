package com.zqs.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqs.mall.model.Result;
import com.zqs.mall.model.User;
import com.zqs.mall.model.vo.AllUserVO;
import com.zqs.mall.service.AdminUserService;
import com.zqs.mall.service.AdminUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description: 管理员模块下的用户管理
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
@WebServlet("/api/admin/user/*")
public class AdminUserServlet extends HttpServlet {
    private AdminUserService adminUserService = new AdminUserServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Result result = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String replace = uri.replace(request.getContextPath() + "/api/admin/user/", "");
        if ("allUser".equals(replace)) {
            // 获取所有用户信息
            allUser(request, response);
        } else if (replace.startsWith("deleteUser")) {
            // 通过id删除用户账号
            deleteUser(request, response);
        } else if ("searchUser".startsWith(replace)) {
            // 通过用户昵称搜索账号
            searchUser(request, response);
        }
    }

    /**
     * 通过用户昵称搜索账号
     *
     * @param request
     * @param response
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nickName = request.getParameter("word");
        List<AllUserVO> allUserVOList = adminUserService.queryUserByNickName(nickName);
        if (allUserVOList == null) {
            // 没有这条数据
            result = new Result(0);
        } else {
            result = new Result(0, allUserVOList);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 通过id删除用户账号
     * 响应体 "{"code":0}"
     *
     * @param request
     * @param response
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        int code = adminUserService.deleteUserById(id);
        if (code == 200) {
            // 删除成功
            result = new Result(0);
        } else {
            // 删除失败
            result = new Result(10000, "删除失败");
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 获取所有用户信息
     *
     * @param request
     * @param response
     */
    private void allUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        List<AllUserVO> allUserVOList = adminUserService.queryAllUser(user);
        // 转成json
        result = new Result(0, allUserVOList);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }
}
