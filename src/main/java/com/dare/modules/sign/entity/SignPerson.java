package com.dare.modules.sign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "签名人员实体")
public class SignPerson {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "apqp流程编号")
    private String apqpNo;
    @ApiModelProperty(value = "项目编号")
    private String projectNo;
    @ApiModelProperty(value = "审核人1")
    private String examine1;
    @ApiModelProperty(value = "审核人2")
    private String examine2;
    @ApiModelProperty(value = "审核人3")
    private String examine3;
    @ApiModelProperty(value = "会签人1")
    private String jointlySign1;
    @ApiModelProperty(value = "会签人2")
    private String jointlySign2;
    @ApiModelProperty(value = "会签人3")
    private String jointlySign3;
    @ApiModelProperty(value = "会签人4")
    private String jointlySign4;
    @ApiModelProperty(value = "会签人5")
    private String jointlySign5;
    @ApiModelProperty(value = "会签人6")
    private String jointlySign6;
    @ApiModelProperty(value = "审批准人")
    private String approve;
    @ApiModelProperty(value = "审核人时间1")
    private String examineTime1;
    @ApiModelProperty(value = "审核人时间2")
    private String examineTime2;
    @ApiModelProperty(value = "审核人时间3")
    private String examineTime3;
    @ApiModelProperty(value = "会签人时间1")
    private String jointlySignTime1;
    @ApiModelProperty(value = "会签人时间2")
    private String jointlySignTime2;
    @ApiModelProperty(value = "会签人时间3")
    private String jointlySignTime3;
    @ApiModelProperty(value = "会签人时间4")
    private String jointlySignTime4;
    @ApiModelProperty(value = "会签人时间5")
    private String jointlySignTime5;
    @ApiModelProperty(value = "会签人时间6")
    private String jointlySignTime6;
    @ApiModelProperty(value = "驳回标记")
    private int reject=0;
    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;
    @ApiModelProperty(value = "驳回时间")
    private Date rejectTime;
}
