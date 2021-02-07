package com.dare.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: shengyao
 * @Package: com.deer.common.vo
 * @Date: 2020/8/3 23:57
 * @Description:
 * @version: 1.0
 */
@Data
@ApiModel(value = "分页接口返回对象", description = "分页接口返回对象")
public class PageResult<T> extends Result<T> {

    @ApiModelProperty(value = "数据总数")
    private long total;

    @ApiModelProperty(value = "每页显示条数")
    private Integer size;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "总页数")
    private Integer pages;
}
