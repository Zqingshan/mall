package com.zqs.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqs.mall.model.admin.Admin;
import com.zqs.mall.model.Result;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.bo.ChangePwdBO;
import com.zqs.mall.model.bo.SearchAdminBO;
import com.zqs.mall.model.vo.AdminLoginVO;
import com.zqs.mall.model.vo.AllAdminVO;
import com.zqs.mall.service.AdminService;
import com.zqs.mall.service.AdminServiceImpl;
import com.zqs.mall.utils.HttpUtils;
import com.zqs.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private AdminService adminService = new AdminServiceImpl();
    private Result result = null;

    /**
     * post请求方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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
        } else if ("getSearchAdmins".equals(replace)) {
            // POST http://115.29.141.32:8084/api/admin/getSearchAdmins HTTP/1.1
            getSearchAdmins(request, response);
        } else if ("changePwd".equals(replace)) {
            // POST http://115.29.141.32:8084/api/admin/changePwd HTTP/1.1
            changePwd(request, response);
        }
    }

    /**
     * 修改密码
     * 请求参数 "{"adminToken":"zqs66666","oldPwd":"Zqs666666!","newPwd":"Zqs777777!","confirmPwd":"Zqs777777!"}
     * 修改成功 响应体 "{"code":0}"
     * 旧密码错误 "{"code":10000,"message":"旧密码错误!"}
     * 新密码与确认密码不一致 "{"code":10000,"message":"请保持确认新密码一致!"}
     * 可添加 新密码与旧密码相同 "{"code":10000,"message":"新密码与旧密码相同!"}
     *
     * @param request
     * @param response
     */
    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 接收参数
        String requestBody = HttpUtils.parseRequestBody(request);
        // json转成JavaBean
        ChangePwdBO changePwdBO = objectMapper.readValue(requestBody, ChangePwdBO.class);

        // 参数校验  原密码,新密码,确认新密码不能为空
        if (StringUtils.isEmpty(changePwdBO.getOldPwd())) {
            result = new Result(10000, "原密码不能为空");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        } else if (StringUtils.isEmpty(changePwdBO.getNewPwd())) {
            result = new Result(10000, "新密码不能为空");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        } else if (StringUtils.isEmpty(changePwdBO.getConfirmPwd())) {
            result = new Result(10000, "确认新密码不能为空");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        } else if (!changePwdBO.getNewPwd().equals(changePwdBO.getConfirmPwd())) {
            // 新密码与确认密码不一致
            result = new Result(10000, "请保持确认新密码一致!");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        }
        // 参数校验通过
        // 先查询原密码是否正确,将用户名传入sql,返回密码,最终得到状态码
        Admin admin = new Admin(null, changePwdBO.getAdminToken(), null, changePwdBO.getNewPwd());
        int code = adminService.login(admin);
        if (code == 200 || code == 403) {
            // 旧密码正确,并且新密码和旧密码不相等
            Admin adminPwd = new Admin(null, changePwdBO.getAdminToken(), null, changePwdBO.getNewPwd());
            int effectRows = adminService.updateAdmin(adminPwd);
            // TODO
            if (effectRows == 200) {
                // 修改成功
                result = new Result(0);
            } else {
                result = new Result(10000, "修改失败");
            }
        } else if (code == 404) {
            // 旧密码错误
            result = new Result(10000, "旧密码错误!");
        } else if (code == 405) {
            // "{"code":10000,"message":"新密码与旧密码相同!"}
            result = new Result(10000, "新密码与旧密码相同!");
        }
        // 返回结果
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 多条件查询
     *
     * @param request
     * @param response
     */
    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 请求参数 {"nickname":"1","email":"2"}
        String requestBody = HttpUtils.parseRequestBody(request);
        // json字符串转成JavaBean
        SearchAdminBO searchAdminBO = objectMapper.readValue(requestBody, SearchAdminBO.class);
        Admin admin = new Admin(null, searchAdminBO.getEmail(), searchAdminBO.getNickname(), null);
        List<AllAdminVO> adminVOList = adminService.allAdmins(admin);
        if (adminVOList != null) {
            // 查到数据 {"code":0,"data":[{"id":1,"email":"admin","nickname":"admin","pwd":"admin"}]}
            // 转成json
            result = new Result(0, adminVOList);
        } else {
            // 没有数据 {"code":0,"data":[]}
            result = new Result(0);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 修改管理员信息
     *
     * @param request
     * @param response
     */
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 请求参数 {"id":136,"nickname":"65a4654ad64","email":"7879789@qq.com","pwd":"aA123123!111"}
        // 响应 {"code":0}
        String requestBody = HttpUtils.parseRequestBody(request);
        // 字符串转成javabean
        AllAdminVO adminRequest = objectMapper.readValue(requestBody, AllAdminVO.class);
        Admin admin = new Admin(adminRequest.getId(), adminRequest.getEmail(), adminRequest.getNickname(), adminRequest.getPwd());
        int effectRows = adminService.updateAdmin(admin);
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
        String requestBody = HttpUtils.parseRequestBody(request);
        // 字符串转成javabean
        AllAdminVO adminRequest = objectMapper.readValue(requestBody, AllAdminVO.class);
        Admin admin = new Admin(adminRequest.getId(), adminRequest.getEmail(), adminRequest.getNickname(), adminRequest.getPwd());
        int effectRows = adminService.insertAdmin(admin);
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
        String requestBody = HttpUtils.parseRequestBody(request);
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
        Admin admin = new Admin(null, adminLoginBO.getEmail(), null, adminLoginBO.getPwd());
        int code = adminService.login(admin);
        if (code == 404) {
            // 账号不存在   {"code":10000,"message":"该账号不存在"}
            result = new Result(10000, "该账号不存在");
        } else if (code == 403) {
            // 账号存在,密码不相等
            result = new Result(10000, "密码不正确");
        } else if (code == 200 || code == 405) {
            // 账号密码正确,登录成功   {"code":0,"data":{"token":"admin","name":"admin"}}
            // 获取昵称
            Admin userName = new Admin(null, adminLoginBO.getEmail(), null, null);
            Admin nickName = adminService.queryNickNameByUname(userName);
            AdminLoginVO loginVO = new AdminLoginVO(nickName.getNickname(), nickName.getNickname());
            result = new Result(0, loginVO);
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * get请求方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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
     * 通过id获取管理员信息
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
        Admin admin = new Admin();
        List<AllAdminVO> allAdminVOS = adminService.allAdmins(admin);
        // 转成json
        Result result = new Result(0, allAdminVOS);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }
}
