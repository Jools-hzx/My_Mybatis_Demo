package com.hzx.sqlSession;

import org.junit.Test;

import java.sql.Connection;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 11:08
 * @description: TODO
 */
public class testJoolsMybatis {


    @Test
    public void getConfigTest() {
        JoolsMybatisConfiguration configuration = new JoolsMybatisConfiguration();
        Connection connection = configuration.loadConfig();
        System.out.println("数据库连接:" + connection);
    }
}
