package com.zqs.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqs.mall.model.Result;
import com.zqs.mall.model.bo.AdminLoginBO;
import com.zqs.mall.model.vo.AdminLoginVO;
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

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {
    private AdminService adminService = new AdminServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前端请求
        String uri = request.getRequestURI();
        String replace = uri.replace(request.getContextPath() + "/api/admin/admin/", "");
        if ("login".equals(replace)) {
            login(request, response);
        }
    }

    /**
     * 登录功能逻辑
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // System.out.println("login");
        // 获取请求体参数,格式是json,要转成java对象
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        String adminRequest = outputStream.toString("utf-8");
        // System.out.println(adminRequest);
        // json字符串转成Java对象
        AdminLoginBO loginBO = mapper.readValue(adminRequest, AdminLoginBO.class);

        // 参数验证
        if (StringUtils.isEmpty(loginBO.getEmail()) || StringUtils.isEmpty(loginBO.getPwd())) {
            // 如果为空
            Result result = new Result(10000, "用户名或密码不能为空", null);
            // 响应体里面输出json字符串
            response.getWriter().println(mapper.writeValueAsString(result));
            return;
        }
        // 参数不为空
        int code = adminService.login(loginBO);
        Result result = null;
        // 根据code的值不同做不同的逻辑  登录成功  登录失败
        if (code == 200) {
            // 登录成功,给前端发送响应报文
            // javaObject ===>  json
            AdminLoginVO loginVO = new AdminLoginVO(loginBO.getEmail(), loginBO.getEmail());
            result = new Result(0, null, loginVO);
        } else {
            // 登录失败
            result = new Result(10000, "账号或密码不正确", null);
        }
        response.getWriter().println(mapper.writeValueAsString(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
