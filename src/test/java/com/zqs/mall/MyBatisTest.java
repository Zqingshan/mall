package com.zqs.mall;

import com.zqs.mall.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Connection;

/**
 * @description:
 * @author: z_qingshan
 * @create: 2021-05-14
 **/
public class MyBatisTest {
    @Test
    public void testMyBatisConfig(){
        SqlSession sqlSession = MyBatisUtils.openSession();
        Connection connection = sqlSession.getConnection();
        System.out.println(connection);
    }
}
