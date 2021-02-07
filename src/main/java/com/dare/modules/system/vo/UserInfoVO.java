package com.dare.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.vo
 * @Date: 2020/12/26 16:29
 * @Description: 用户个人信息
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "用户信息实体")
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名（登录账号）")
    private String userName;

    @ApiModelProperty(value = "员工编号")
    private String empNo;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "部门编号")
    private Integer deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;
//    private Department department;

    @ApiModelProperty(value = "职位编号")
    private Integer positionId;

    @ApiModelProperty(value = "职位名称")
    private String positionName;
//    private Position position;

    @ApiModelProperty(value = "身份证号")
    private String identityNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "地址")
    private String address;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职日期")
    private Date entryDate;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "毕业学校")
    private String graduateSchool;
}
