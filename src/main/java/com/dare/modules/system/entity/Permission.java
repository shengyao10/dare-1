package com.dare.modules.system.entity;


import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: shengyao
 * @Package: com.lbj.entity
 * @Date: 2020/11/9 14:05
 * @Description: 权限表
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String permissionName;
}
