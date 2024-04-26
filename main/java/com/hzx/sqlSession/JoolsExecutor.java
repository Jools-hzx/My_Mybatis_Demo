package com.hzx.sqlSession;

import com.hzx.entity.Monster;

import java.sql.*;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 11:20
 * @description: TODO
 */
public class JoolsExecutor implements Executor {

    private JoolsMybatisConfiguration configuration;
    private Connection connection;

    public JoolsExecutor(JoolsMybatisConfiguration configuration) {
        this.configuration = configuration;
        this.connection = this.configuration.loadConfig();
    }

    @Override
    public <T> T query(String sql, Object parameter) throws SQLException {

        //准备statement
        PreparedStatement statement = connection.prepareStatement(sql);
        //设置 SQL 语句中的参数
        statement.setString(1, String.valueOf(parameter));
        //执行 SQL 语句
        ResultSet resultSet = statement.executeQuery();

            /*
              `id` INT NOT NULL AUTO_INCREMENT,
              `age` INT NOT NULL,
              `birthday` DATE DEFAULT NULL,
              `email` VARCHAR(255) NOT NULL,
              `gender` TINYINT NOT NULL,
              `name` VARCHAR(255) NOT NULL,
              `salary` DOUBLE NOT NULL,
             */
        //此处简化，仅考虑查询到一条 Monster 数据的情况
        Integer id  = null;
        Integer age = null;
        String email = null;
        Date birthday = null;
        Integer gender = null;
        String name = null;
        Double salary = null;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            age = resultSet.getInt("age");
            email = resultSet.getString("email");
            birthday = resultSet.getDate("birthday");
            gender = resultSet.getInt("gender");
            name = resultSet.getString("name");
            salary = resultSet.getDouble("salary");
        }


        //封装成一条 Monster 数据
        Monster monster = new Monster();
        monster.setName(name);
        monster.setId(id);
        monster.setAge(age);
        monster.setEmail(email);
        monster.setBirthday(birthday);
        monster.setGender(gender);
        monster.setSalary(salary);

        return (T) monster;
    }
}
