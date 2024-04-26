package com.hzx.sqlSession;

import com.hzx.entity.MapperBean;
import com.hzx.entity.Monster;
import com.hzx.mapper.MonsterMapper;
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
    public void proxyTest() {
        JoolsSqlSession sqlSession = new JoolsSqlSession(new JoolsMybatisConfiguration());
        MonsterMapper mapper = sqlSession.getMapper(MonsterMapper.class);
        Monster monster = mapper.getMonsterById("1");
        System.out.println(monster);
    }

    @Test
    public void testGetMapperBean() {
        JoolsMybatisConfiguration configuration = new JoolsMybatisConfiguration();
        try {
            MapperBean mapperBean = configuration.getMapperBean("MonsterMapper.xml");
            System.out.println("MapperBean:" + mapperBean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSqlSessionQuery() {
        JoolsSqlSession sqlSession = new JoolsSqlSession(new JoolsMybatisConfiguration());
        Monster monster = (Monster) sqlSession.selectOne("SELECT * FROM `monster` WHERE id = ?", "1");
        System.out.println(monster);
    }

    @Test
    public void getConfigTest() {
        JoolsMybatisConfiguration configuration = new JoolsMybatisConfiguration();
        Connection connection = configuration.loadConfig();
        System.out.println("数据库连接:" + connection);
    }

    @Test
    public void testExecutor() throws SQLException {
        Executor executor = new JoolsExecutor(new JoolsMybatisConfiguration());
        Monster monster = executor.query("SELECT * FROM `monster` WHERE id = ?", "1");
        System.out.println(monster);
    }
}
