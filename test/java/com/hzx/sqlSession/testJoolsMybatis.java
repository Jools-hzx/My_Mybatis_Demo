package com.hzx.sqlSession;

import com.hzx.entity.Monster;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

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

    @Test
    public void testExecutor() throws SQLException {
        Executor executor = new JoolsExecutor();
        Monster monster = executor.query("SELECT * FROM `monster` WHERE id = ?", "1");
        System.out.println(monster);
    }
}
