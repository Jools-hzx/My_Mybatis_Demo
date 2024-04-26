package com.hzx.sqlSession;


import java.sql.SQLException;

public interface Executor {

    //查询数据库操作，传入SQL语句和语句内的参数
    <T> T query(String sql, Object parameter) throws SQLException;
}
