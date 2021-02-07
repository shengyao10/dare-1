package com.dare.modules.system.entity;

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
 * @Package: com.dare.modules.system.entity
 * @Date: 2021/1/5 14:51
 * @Description: APQP文件类
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "APQP文件实体")
public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "项目编号")
    private String projectNo;

    @ApiModelProperty(value = "APQP流程编号")
    private String apqpNo;

    @ApiModelProperty(value = "存储路径")
    private String filePath;

    @ApiModelProperty(value = "已审查个数")
    private Integer examined;

    @ApiModelProperty(value = "已会签个数")
    private Integer jointlySigned;

    @ApiModelProperty(value = "是否已经批准（0为否，1为是）")
    private Integer approved;

    @ApiModelProperty(value = "版本号")
    private String edition;

    @ApiModelProperty(value = "会签表位置")
    private String signFile;

    @ApiModelProperty(value = "进度")
    private Integer progressRate;

    @ApiModelProperty(value = "是否驳回，1是已驳回，0是正常（未驳回）")
    private Integer reject;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreated;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
}
