package com.hzx.sqlSession;

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

}
