package com.dare.modules.project.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.vo
 * @Date: 2021/1/14 10:47
 * @Description: 流程VO
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ScheduleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "项目号")
    private String projectNo;

    @ApiModelProperty(value = "apqp流程号")
    private String apqpStepNo;

    @ApiModelProperty(value = "apqp流程号父id")
    private String apqpStepPNo;

    @ApiModelProperty(value = "计划开始时间")
    private Date planStart;

    @ApiModelProperty(value = "计划结束时间")
    private Date planEnd;

    @ApiModelProperty(value = "计划期限")
    private Integer planPeriod;

    @ApiModelProperty(value = "实际工期")
    private Integer realPeriod;

    @ApiModelProperty(value = "实际开始时间")
    private Date realStart;

    @ApiModelProperty(value = "实际结束时间")
    private Date realEnd;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "是否完成（0未完成，1已完成）")
    private Integer state;

    @ApiModelProperty(value = "文件是否上传的状态（0未上传，1已上传）")
    private Integer fileState;

    @ApiModelProperty(value = "部门")
    private String dept;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreated;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;


}
