package com.dare.modules.hrm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.entity
 * @Date: 2020/8/3 16:42
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "员工实体")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "员工编号")
    private String empNo;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "性别")
    private Integer sex;

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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

}
