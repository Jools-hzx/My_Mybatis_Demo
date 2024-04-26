package com.hzx.mapper;

import com.hzx.entity.Function;
import com.hzx.entity.MapperBean;
import com.hzx.sqlSession.JoolsMybatisConfiguration;
import com.hzx.sqlSession.JoolsSqlSession;
import org.dom4j.DocumentException;
import sun.awt.windows.ThemeReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 12:52
 * @description: TODO
 */
public class MapperProxy implements InvocationHandler {

    private JoolsSqlSession sqlSession;
    private JoolsMybatisConfiguration configuration;
    private Class<?> clazz;     //XxxMapper接口的Class类对象
    private List<MapperBean> mapperBeans = new ArrayList<>();

    /**
     * MapperProxy 代理类的构造器
     *
     * @param sqlSession    自定义 SqlSession 类
     * @param configuration 自定义 Configuration 类
     * @param clazz         代理的接口类
     */
    public MapperProxy(JoolsSqlSession sqlSession,
                       JoolsMybatisConfiguration configuration,
                       Class<?> clazz) {
        this.sqlSession = sqlSession;
        this.configuration = configuration;
        MapperBean mapperBean = null;

        //根据传入的接口名得到对应的 XML 配置文件
        String xmlFilename = clazz.getSimpleName() + ".xml";
        try {
            //扫描该 XML 文件；将其封装为 MapperBean
            mapperBean = configuration.getMapperBean(xmlFilename);
            mapperBeans.add(mapperBean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //遍历所有 MapperBean
        Object result = null;
        for (MapperBean mapperBean : mapperBeans) {
            //判断该方法所在的接口类型名称是否和MapperBean一致
            if (method.getDeclaringClass().getName().equals(mapperBean.getMapperName())) {
                List<Function> functions = mapperBean.getFunctions();
                for (Function function : functions) {
                    //判断方法名是否相等
                    if (method.getName().equals(function.getFuncName())) {
                        //简化，如果此处为 select 类型; 默认调用 SqlSession 的 selectOne 方法
                        if ("select".equals(function.getSqlType())) {
                            String sql = function.getSql();
                            result = this.sqlSession.selectOne(sql, "1");
                        }
                    }
                }
            }
        }
        return result;
    }
}
