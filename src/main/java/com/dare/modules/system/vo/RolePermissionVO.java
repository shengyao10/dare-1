package com.dare.modules.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: shengyao
 * @Package: com.lbj.vo
 * @Date: 2020/11/13 21:06
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePermissionVO implements Serializable {

    private String id;

    private String roleName;

    private String permissionName;
}
