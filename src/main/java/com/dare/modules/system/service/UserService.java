package com.dare.modules.system.service;

import com.dare.modules.system.entity.User;
import com.dare.modules.system.vo.UserInfoVO;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.service
 * @Date: 2020/11/10 16:02
 * @Description:
 * @version: 1.0
 */
public interface UserService {
    User getUserByName(String username);

    void save(User user);

    UserInfoVO getUserInfoByUserName(String username);

    String getEmpNameByUsername(String username);
    void updatePassword(User user);
}
