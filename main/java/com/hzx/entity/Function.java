package com.hzx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 12:23
 * @description: TODO
 */

@Data
@NoArgsConstructor
public class Function {
    /*
      a. funcName - 对应的 XxxMapper 接口内的方法名
      b. Sql 语句(String)
      c. Sql语句的类型(String) - [select/insert/update/delete]
      d. resultType 结果类型
      e. parameterType 参数类型
     */
    private String funcName;
    private String sql;
    private String sqlType;
    private Object resultType;
    private Object parameterType;
}
