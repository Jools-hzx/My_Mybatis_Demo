# My_Mybatis_Demo
A mini demo that imitate Mybatis framework, using proxy to load XML file config and execute SQL to query Database with personal defined Executor, Configuration, SqlSesion


## 核心机制

My_Mybatis_Demo 是一个模拟 MyBatis 的简单 ORM 框架，它实现了基本的 MyBatis 功能，如配置文件解析、SQL 执行器、结果映射和动态代理。

## 设计思路

项目通过自定义配置文件和映射文件，结合动态代理技术，实现了简化版的 MyBatis 框架。它允许用户通过接口直接操作数据库，而不需要编写具体的 JDBC 代码。

## 快速开始

### 环境要求
- JDK 1.8 或更高版本
- MySQL数据库

1. **创建数据库和表**

    ```sql
    CREATE DATABASE `jools_mybatis`;
    USE `jools_mybatis`;

    CREATE TABLE `monster`(
      `id` INT NOT NULL AUTO_INCREMENT,
      `age` INT NOT NULL,
      `birthday` DATE DEFAULT NULL,
      `email` VARCHAR(255) NOT NULL,
      `gender` TINYINT NOT NULL,
      `name` VARCHAR(255) NOT NULL,
      `salary` DOUBLE NOT NULL,
      PRIMARY KEY(`id`)
    ) CHARSET = utf8;

    INSERT INTO `monster` VALUES(NULL, 200, '2000-11-11', 'nmw@sohu.com', 1, '牛魔王', 8888.88);
    SELECT * FROM `monster`;
    ```

2. **配置项目的 Maven 依赖**

    在项目的 `pom.xml` 文件中添加必要的依赖。
   ```xml
     <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>org.example</groupId>
      <artifactId>jools_mybatis</artifactId>
      <version>1.0-SNAPSHOT</version>
      <name>Archetype - jools_mybatis</name>
      <url>http://maven.apache.org</url>
    
      <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <java.version>1.8</java.version>
      </properties>
    
      <dependencies>
        <!-- 读取xml文件-->
        <dependency>
          <groupId>dom4j</groupId>
          <artifactId>dom4j</artifactId>
          <version>1.6.1</version>
        </dependency>
        <!-- MySQL-->
        <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>8.0.33</version>
        </dependency>
        <!-- Lombok -->
        <dependency>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <version>1.18.20</version>
          <scope>provided</scope>
        </dependency>
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.13.1</version>
          <scope>test</scope>
        </dependency>
      </dependencies>
    </project>
   ```

4. **实现任务阶段**

    - 阶段 1: 完成读取配置文件，得到数据库连接。
    - 阶段 2: 编写执行器，输入 SQL 语句，完成查询操作。
    - 阶段 3: 将执行器封装到 SqlSession 中。
    - 阶段 4: 开发 XxxMapper 接口和 XxxMapper.xml。
    - 阶段 5: 开发和 Mapper 接口相映射的 MapperBean。
    - 阶段 6: 在 Configuration 中读取 XxxMapper.xml，创建 MapperBean 对象。
    - 阶段 7: 实现动态代理 Mapper 的方法。
    - 阶段 8: 定义 SqlSessionFactory，获取 SqlSession 对象。

## 使用方法

```java
SqlSessionFactory factory = SqlSessionFactory.build(new JoolsMybatisConfiguration());
JoolsSqlSession sqlSession = factory.openSession();
MonsterMapper mapper = sqlSession.getMapper(MonsterMapper.class);
Monster monster = mapper.getMonsterById("1");
System.out.println(monster);
```
## 项目结构
src
├── main
│   ├── java
│   │   └── com.hzx
│   │       ├── entity
│   │       │   ├── Function.java
│   │       │   ├── MapperBean.java
│   │       │   └── Monster.java
│   │       ├── factory
│   │       │   └── SqlSessionFactory.java
│   │       ├── mapper
│   │       │   ├── MapperProxy.java
│   │       │   └── MonsterMapper.java
│   │       └── sqlSession
│   │           ├── Executor.java
│   │           ├── JoolsExecutor.java
│   │           ├── JoolsMybatisConfiguration.java
│   │           └── JoolsSqlSession.java
│   └── resources
│       ├── MonsterMapper.xml
│       └── my-database-config.xml
└── test
    └── java
        └── com.hzx.sqlSession
            └── testJoolsMybatis.java

## Contributer
Jools He【email: 1035558517@qq.com】

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
