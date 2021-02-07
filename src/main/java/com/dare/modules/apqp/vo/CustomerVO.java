package com.dare.modules.apqp.vo;

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
 * @Package: com.dare.modules.apqp.vo
 * @Date: 2020/12/8 16:11
 * @Description: CustomerVO
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "CustomerVO")
public class CustomerVO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "顾客名称")
    private String customerName;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系方式")
    private String tel;

}
