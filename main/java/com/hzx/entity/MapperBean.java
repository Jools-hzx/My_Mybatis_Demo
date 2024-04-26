package com.hzx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/26 12:22
 * @description: TODO
 */

@Data
@NoArgsConstructor
public class MapperBean {

    private String mapperName;
    private List<Function> functions;
}
