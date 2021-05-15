package com.zqs.mall.utils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-15
 **/
public class HttpUtils {
    public static String parseRequestBody(HttpServletRequest request) throws IOException {
        // 获取前端表单输入的请求体参数
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        // 将OutputStream转成字符串
        return outputStream.toString("utf-8");
    }
}
