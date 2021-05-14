package com.zqs.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqs.mall.model.Result;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.vo.AdminLoginVO;
import com.zqs.mall.model.vo.AllAdminVO;
import com.zqs.mall.service.AdminService;
import com.zqs.mall.service.AdminServiceImpl;
import com.zqs.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private AdminService adminService = new AdminServiceImpl();
    private Result result = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前端请求
        String uri = request.getRequestURI();
        String replace = uri.replace(request.getContextPath() + "/api/admin/admin/", "");
        if ("login".equals(replace)) {
            login(request, response);
        } else if ("addAdminss".equals(replace)) {
            addAdminss(request, response);
        } else if ("updateAdminss".equals(replace)) {
            updateAdmin(request, response);
        }
    }

    /**
     * 修改管理员信息
     *
     * @param request
     * @param response
     */
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 请求 POST http://115.29.141.32:8084/api/admin/updateAdminss HTTP/1.1
        // 请求参数 {"id":136,"nickname":"65a4654ad64","email":"7879789@qq.com","pwd":"aA123123!111"}
        // 响应 {"code":0}
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        String requestBody = outputStream.toString("utf-8");
        // 字符串转成javabean
        AllAdminVO adminRequest = objectMapper.readValue(requestBody, AllAdminVO.class);
        int effectRows = adminService.updateAdmin(adminRequest);
        if (effectRows == 200) {
            // 修改成功
            result = new Result(0);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 新增管理员账号
     * 获取前端表单输入的参数,JSON转成JavaBean
     * "{"nickname":"zqs666","email":"112233445566@qq.com","pwd":"Zqs123456!"}"
     * 调用service,service调用dao
     *
     * @param request
     * @param response
     */
    private void addAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        String requestBody = outputStream.toString("utf-8");
        // 字符串转成javabean
        AllAdminVO adminRequest = objectMapper.readValue(requestBody, AllAdminVO.class);
        int effectRows = adminService.insertAdmin(adminRequest);
        if (effectRows == 200) {
            // 添加成功 {"code":0}
            result = new Result(0);
        } else if (effectRows == 404 || effectRows == 403) {
            // 添加失败 {"code":10000,"message":"该账号不允许重复使用"}
            result = new Result(10000, "该账号不允许重复使用");
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 登录功能实现
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取前端表单输入的请求体参数,再将其转成JavaBean
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        // 将OutputStream转成字符串
        String requestBody = outputStream.toString("utf-8");
        AdminLoginBO adminLoginBO = objectMapper.readValue(requestBody, AdminLoginBO.class);


        // 参数校验   用户名或者密码是否为空
        // 为空
        if (StringUtils.isEmpty(adminLoginBO.getEmail()) || StringUtils.isEmpty(adminLoginBO.getPwd())) {
            // 返回响应体 {"code":10000,"message":"账号或密码不能为空"}
            result = new Result(10000, "账号或密码不能为空");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        }
        // 不为空
        // 调用service层
        int code = adminService.login(adminLoginBO);
        if (code == 404) {
            // 账号不存在   {"code":10000,"message":"该账号不存在"}
            result = new Result(10000, "该账号不存在");
        } else if (code == 403) {
            // 账号存在,密码不相等  {"code":10000,"message":"密码不正确!"}
            result = new Result(10000, "密码不正确");
        } else {
            // 账号密码正确,登录成功   {"code":0,"data":{"token":"admin","name":"admin"}}
            AdminLoginVO loginVO = new AdminLoginVO(adminLoginBO.getEmail(), adminLoginBO.getEmail());
            result = new Result(0, loginVO);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前端请求
        String uri = request.getRequestURI();
        String replace = uri.replace(request.getContextPath() + "/api/admin/admin/", "");
        if ("allAdmins".equals(replace)) {
            allAdmins(request, response);
        } else if (replace.startsWith("deleteAdmins")) {
            deleteAdmins(request, response);
        } else if (replace.startsWith("getAdminsInfo")) {
            getAdminsInfo(request, response);
        }
    }

    /**
     * 获取管理员信息
     * // GET http://115.29.141.32:8084/api/admin/getAdminsInfo?id=14
     * // "{"code":0,"data":{"id":1,"email":"admin","nickname":"admin","pwd":"admin"}}"
     *
     * @param request
     * @param response
     */
    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        AllAdminVO admin = adminService.queryAdminById(id);
        // JavaBean转成json
        if (admin == null) {
            // 没有这条数据
            result = new Result(10000, "获取信息失败");
        } else {
            result = new Result(0, admin);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 删除管理员账号
     * GET http://115.29.141.32:8084/api/admin/deleteAdmins?id=246
     * "{"code":0}"
     *
     * @param request
     * @param response
     */
    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求体的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        int effectRows = adminService.deleteAdminById(id);
        if (effectRows == 200) {
            // 删除成功 "{"code":0}"
            result = new Result(0);
        } else if (effectRows == 404) {
            result = new Result(10000, "删除失败");
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 获取所有管理员账号信息
     * 分为三步走 1.获取请求参数  2.具体的业务逻辑执行  3. 做出响应
     *
     * @param request
     * @param response
     */
    private void allAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<AllAdminVO> allAdminVOS = adminService.allAdmins();
        // 转成json
        Result result = new Result(0, allAdminVOS);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }
}
