package com.hzx.sqlSession;

import java.sql.SQLException;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 11:46
 * @description: TODO
 */
public class JoolsSqlSession {

    private Executor executor;  //持有执行器实现类
    private JoolsMybatisConfiguration configuration;    //持有 Configuration

    //解析传入的 Configuration; 赋值 Executor 属性
    public JoolsSqlSession(JoolsMybatisConfiguration configuration) {
        this.configuration = configuration;
        this.executor = new JoolsExecutor(this.configuration);
    }

    @SuppressWarnings("all")
    public <T> T selectOne(String sql, Object parameter) {
        if (this.executor == null) return null;
        try {
            return (T) this.executor.query(sql, parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
