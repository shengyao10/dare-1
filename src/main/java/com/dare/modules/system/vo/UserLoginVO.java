package com.dare.modules.system.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.vo
 * @Date: 2020/12/18 18:47
 * @Description:
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserLoginVO {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
