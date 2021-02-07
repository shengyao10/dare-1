//package com.dare.modules.apqp.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.models.auth.In;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.experimental.Accessors;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @Author: shengyao
// * @Package: com.dare.modules.apqp.entity
// * @Date: 2020/10/31 9:46
// * @Description: 项目实体
// * @version: 1.0
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Accessors(chain = true)
//@ApiModel(description = "项目实体")
//public class Project implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "主键")
//    private Integer id;
//
//    @ApiModelProperty(value = "项目编号")
//    private String projectNo;
//
//    @ApiModelProperty(value = "项目名称")
//    private String projectName;
//
//    @ApiModelProperty(value = "顾客姓名")
//    private String customerNo;
//
////    @ApiModelProperty(value = "员工编号")
////    private String meeting_no;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "项目开始时间")
//    private Date startTime;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "项目结束时间")
//    private Date endTime;
//
//    @ApiModelProperty(value = "项目状态（0未完成立项，1项目进行中，2项目已完成）")
//    private Integer projectState;
//
//    @ApiModelProperty(value = "总任务数")
//    private Integer projectTask;
//
//    @ApiModelProperty(value = "已完成任务数")
//    private String completeTask;
//
//    @ApiModelProperty(value = "创建人姓名")
//    private String createdBy;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "创建时间")
//    private Date gmtCreated;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "修改时间")
//    private Date gmtModified;
//
//
//
//}
