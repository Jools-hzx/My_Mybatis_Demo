package com.hzx.sqlSession;

import com.hzx.entity.Function;
import com.hzx.entity.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 11:03
 * @description: 读取 Configuration 配置文件，得到与数据库的 Connection 连接
 */
public class JoolsMybatisConfiguration {

    private SAXReader saxReader;    //XML解析工具
    private static final String CONFIG_LOCATION = "my-database-config.xml";
    private ClassLoader classLoader;    //类加载器

    public JoolsMybatisConfiguration() {
        this.saxReader = new SAXReader();
        this.classLoader = JoolsMybatisConfiguration.class.getClassLoader();
    }

    //读取xml配置文件，创建数据库 Connection
    public Connection loadConfig() {
        InputStream inputStream = classLoader.getResourceAsStream(CONFIG_LOCATION);
        Document document = null;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element rootElement = document.getRootElement();
        System.out.println("RootElement:" + rootElement.getName()); //输出 database

        /*
        <database>
            <property name="driverClassName">com.mysql.cj.jdbc.Driver</property>
            <property name="url">jdbc:mysql://localhost:3306/jools_mybatis?useUnicode=true&amp;characterEncoding=utf8</property>
            <property name="username">root</property>
            <property name="password">hzx2001</property>
        </database>
         */
        String driverClass = "";
        String url = "";
        String username = "";
        String password = "";
        List<Element> elements = (List<Element>) rootElement.elements("property");
        for (Element element : elements) {
            String name = element.attributeValue("name");
            String val = element.getText();
            switch (name) {
                case "driverClassName":
                    driverClass = val;
                    break;
                case "url":
                    url = val;
                    break;
                case "username":
                    username = val;
                    break;
                case "password":
                    password = val;
                    break;
                default:
                    throw new RuntimeException("Can not resolve attribute name:" + name);
            }
        }
//        System.out.println("解析到的属性名:");
//        System.out.println(driverClass);
//        System.out.println(url);
//        System.out.println(username);
//        System.out.println(password);

        Class<?> cls = null;
        try {
            cls = Class.forName(driverClass);
            System.out.println("加载到的驱动名为:" + cls.getName());
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MapperBean getMapperBean(String mapperXmlLoc) throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (mapperXmlLoc.isEmpty()) return null;
        InputStream inputStream = classLoader.getResourceAsStream(mapperXmlLoc);
        Document document = this.saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
//        System.out.println("name:" + rootElement.getName());    //输出mapper

        //得到映射的 Mapper 接口全类名
        String mapperName = rootElement.attributeValue("namespace");
        /*
        <mapper namespace="com.hzx.mapper.MonsterMapper">
            ....
        </mapper>
         */
        //扫描遍历所有的 select / update/ delete / insert 配置信息
        Iterator iterator = rootElement.elementIterator();

        /*
            <select id="getMonsterById" resultType="com.hzx.entity.Monster" parameterType="java.lang.String">
                SELECT * FROM monster WHERE id=#{id};
            </select>
         */
        String funcName = null;
        Object resultType = null;
        String parameterType = null;
        String sql = null;
        String sqlType = null;

        MapperBean mapperBean = new MapperBean();
        mapperBean.setMapperName(mapperName);

        //遍历该 XML 文件内定义的子标签
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            sqlType = element.getName();
            funcName = element.attributeValue("id");
            String resultTypeStr = element.attributeValue("resultType");
            parameterType = element.attributeValue("parameterType");
            sql = element.getText();

            //封装成一个 Function 对象
            Function function = new Function();
            function.setSql(sql);
            function.setSqlType(sqlType);
            function.setFuncName(funcName);

            Class<?> cls = Class.forName(resultTypeStr);
            resultType = cls.newInstance();

            function.setResultType(resultType);
            function.setParameterType(parameterType);
            mapperBean.addFunction(function);
        }
        return mapperBean;
    }
}
