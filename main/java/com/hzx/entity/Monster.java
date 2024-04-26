package com.hzx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 11:20
 * @description: TODO
 */

@Data
@NoArgsConstructor
public class Monster {
    /*
      `id` INT NOT NULL AUTO_INCREMENT,
      `age` INT NOT NULL,
      `birthday` DATE DEFAULT NULL,
      `email` VARCHAR(255) NOT NULL,
      `gender` TINYINT NOT NULL,
      `name` VARCHAR(255) NOT NULL,
      `salary` DOUBLE NOT NULL,
     */
    private Integer id;
    private Integer age;
    private Date birthday;
    private Integer gender;
    private String email;
    private String name;
    private Double salary;

}
