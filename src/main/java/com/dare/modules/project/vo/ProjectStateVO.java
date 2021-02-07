package com.dare.modules.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author cyl
 * @version 1.0
 * @date 2021/1/9 18:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ProjectStateVO {
    @ApiModelProperty(value = "项目编号")
    private String projectNo;

    @ApiModelProperty(value = "项目状态（0未完成立项，1项目进行中，2项目已完成）")
    private Integer projectState;

}
