package com.dare.modules.hrm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.hrm.vo
 * @Date: 2020/11/30 9:35
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "EmployeeVO")
public class EmployeeDeptVO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "员工编号")
    private String empNo;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
