package com.hzx.factory;

import com.hzx.sqlSession.JoolsMybatisConfiguration;
import com.hzx.sqlSession.JoolsSqlSession;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 13:29
 * @description: TODO
 */
public class SqlSessionFactory {

    private JoolsMybatisConfiguration configuration;

    private SqlSessionFactory() {
        this.configuration = new JoolsMybatisConfiguration();
    }

    //构建 SqlSessionFactory 工厂类
    public static SqlSessionFactory build(JoolsMybatisConfiguration configuration) {
        return new SqlSessionFactory();
    }

    //获取 SqlSession 实现对象
    public JoolsSqlSession openSession() {
        return new JoolsSqlSession(this.configuration);
    }
}
