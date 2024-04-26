package com.hzx.sqlSession;

import com.hzx.mapper.MapperProxy;
import sun.reflect.Reflection;

import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
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

    //获取实现传入接口的动态代理类
    @SuppressWarnings("all")
    public <T> T getMapper(Class<T> clazz) {
        /*
            Proxy 构造器:
        public static Object newProxyInstance(ClassLoader loader,
                                            Class<?>[] interfaces,
                                            InvocationHandler h){}
         */
        return (T) Proxy.newProxyInstance(
                this.configuration.getClass().getClassLoader(),
                new Class[]{clazz}, //实现的所有接口Class类
                new MapperProxy(       //自定义的 InvocationHandler 类
                        this,          //自定义 SqlSession 对象
                        this.configuration, //自定义 Configuration 类
                        clazz)          //传入的接口类
        );
    }
}
