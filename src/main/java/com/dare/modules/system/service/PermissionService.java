package com.dare.modules.system.service;

import java.util.Set;

/**
 * @Author: shengyao
 * @Package: com.lbj.service
 * @Date: 2020/11/13 20:57
 * @Description:
 * @version: 1.0
 */
public interface PermissionService {
    Set<String> getUserPermissionsSet(String username);
}
