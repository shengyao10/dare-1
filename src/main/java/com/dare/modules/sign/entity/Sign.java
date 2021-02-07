package com.dare.modules.sign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "签名实体")
public class Sign {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "apqp流程编号")
    private String apqpNo;
    @ApiModelProperty(value = "项目编号")
    private String projectNo;
    @ApiModelProperty(value = "已会签个数")
    private int jointlySigned;
    @ApiModelProperty(value = "已审核个数")
    private int examined;
    @ApiModelProperty(value = "已批准个数")
    private int approvaled;
    @ApiModelProperty(value = "完成度")
    private int rate;
    @ApiModelProperty(value = "会签文件路径")
    private String signFile;
    @ApiModelProperty(value = "驳回标记")
    private int reject=0;

    /*@ApiModelProperty(value = "需要会签个数")
    private int jointlySign;
    @ApiModelProperty(value = "需要审核个数")
    private int examine;
    @ApiModelProperty(value = "签名文件位置")
    private int signFile;*/
}
