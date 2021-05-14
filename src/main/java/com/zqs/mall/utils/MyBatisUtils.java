package com.zqs.mall.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @description: MyBatis工具类
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class MyBatisUtils {
    // 利用static(静态)属于类不属于对象,且全局唯一
    private static SqlSessionFactory sqlSessionFactory;

    // 利用静态代码块在初始化类时实例化sqlSessionFactory
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            // 初始化错误时,通过抛出异常通知调用者
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * openSession 创建一个新的SqlSession对象
     *
     * @return SqlSession对象
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 释放一个有效的SqlSession对象
     *
     * @param sqlSession 准备释放SqlSession对象
     */
    public static void closeSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
