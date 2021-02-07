package com.dare.modules.project.vo;

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
 * @Date: 2021/1/14 17:28
 * @Description: 可视化VO
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ProjectVisualizationVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String projectNo;
    private String projectName;
    private Date startTime;
    private Date endTime;
    private String createdBy;
    private Integer projectTask;
    private Integer completeTask;

}
