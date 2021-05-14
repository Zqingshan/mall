package com.zqs.mall.utils;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class StringUtils {

    public static boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }
        return false;
    }
}
