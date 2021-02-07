package com.dare.modules.system.mapper;

import com.dare.modules.system.entity.User;
import com.dare.modules.system.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: shengyao
 * @Package: com.lbj.mapper
 * @Date: 2020/11/9 9:32
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "userMapper")
public interface UserMapper {
    User getUserByName(@Param("username") String username);

    void save(User user);

    UserInfoVO getUserInfoByUserName(String username);

    String getEmpNameByUsername(String username);
    void updatePassword(User user);
}
