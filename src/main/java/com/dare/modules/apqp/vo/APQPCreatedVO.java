package com.dare.modules.apqp.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.vo
 * @Date: 2020/12/1 14:41
 * @Description: 创建APQP流程VO
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class APQPCreatedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String pid;

    private String staff;

    private String department;

    private List taskDateRange;



}
