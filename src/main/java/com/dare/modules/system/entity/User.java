package com.dare.modules.system.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: shengyao
 * @Package: com.lbj.entity
 * @Date: 2020/11/8 20:16
 * @Description:  用户表
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String username;
    private String password;
    private String salt;


}
