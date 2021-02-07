package com.dare.modules.sign.vo;
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
@ApiModel(description = "批准APQP状态更新")
public class ApqpStateVO {
    private String projectNo;

    private String apqpNo;

    private int state;
    private int fileState;
}
