package com.zqs.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqs.mall.model.Result;
import com.zqs.mall.model.User;
import com.zqs.mall.model.bo.UserLoginBO;
import com.zqs.mall.model.bo.UserSignUpBO;
import com.zqs.mall.model.vo.UserVO;
import com.zqs.mall.service.FrontUserService;
import com.zqs.mall.service.FrontUserServiceImpl;
import com.zqs.mall.utils.HttpUtils;
import com.zqs.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/mall/user/*")
public class FrontUserServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    private FrontUserService frontUserService = new FrontUserServiceImpl();
    private Result result = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String replace = uri.replace(request.getContextPath() + "/api/mall/user/", "");
        if ("login".equals(replace)) {
            login(request, response);
        } else if ("signup".equals(replace)) {
            signup(request, response);
        }
    }

    /**
     * 用户注册
     *
     * @param request
     * @param response
     */
    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.parseRequestBody(request);
        UserSignUpBO userSignUpBO = objectMapper.readValue(requestBody, UserSignUpBO.class);
        User user = new User(null, userSignUpBO.getEmail(), userSignUpBO.getNickname(),
                userSignUpBO.getPwd(), userSignUpBO.getRecipient(), userSignUpBO.getAddress(), userSignUpBO.getPhone());
        int code = frontUserService.insertUser(user);
        if (code == 200) {
            // 注册成功
            UserVO userVO = new UserVO(0, user.getNickname(), user.getNickname());
            result = new Result(0, userVO);
        } else if (code == 404) {
            // 注册失败,账号已存在
            result = new Result(10000, "该账号已存在!");
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.parseRequestBody(request);
        UserLoginBO userLoginBO = objectMapper.readValue(requestBody, UserLoginBO.class);
        // 参数校验
        if (StringUtils.isEmpty(userLoginBO.getEmail()) || StringUtils.isEmpty(userLoginBO.getPwd())) {
            // 参数为空
            result = new Result(10000, "用户名或密码不能为空");
            response.getWriter().println(objectMapper.writeValueAsString(result));
            return;
        }
        // 参数不为空
        User user = new User(null, userLoginBO.getEmail(), null, userLoginBO.getPwd(), null, null, null);
        int code = frontUserService.login(user);
        if (code == 200) {
            // 登录成功
            User nickName = frontUserService.queryNickNameByUname(user);
            UserVO userLoginVO = new UserVO(0, nickName.getNickname(), nickName.getNickname());
            result = new Result(0, userLoginVO);
        } else if (code == 403) {
            // 登录失败
            result = new Result(10000, "账号不存在!");
        } else if (code == 404) {
            // 登录失败
            result = new Result(10000, "密码错误!");
        }
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
