package com.dare.modules.project.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.vo
 * @Date: 2021/1/4 14:35
 * @Description: 项目详细信息VO
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@ApiModel(description = "项目详细信息")
public class ProjectDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父id")
    private String pid;

    @ApiModelProperty(value = "标签（阶段名称）")
    private String label;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划开始时间")
    private Date planStart;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划结束时间")
    private Date planEnd;

    @ApiModelProperty(value = "计划期限")
    private Integer planPeriod;

    @ApiModelProperty(value = "责任部门编号")
    private String dept;

    @ApiModelProperty(value = "责任部门名称")
    private String deptName;

    @ApiModelProperty(value = "责任人编号")
    private String leader;

    @ApiModelProperty(value = "责任人姓名")
    private String empName;

    @ApiModelProperty(value = "完成状态（0未完成，1已完成）")
    private String state;

    private List timeList;

    @ApiModelProperty(value = "进度")
    private Integer progressRate;

    @ApiModelProperty(value = "子节点")
    public List<ProjectDetailVO> children;

//    public ProjectDetailVO(String id, String pid, String label, String dept, String leader, List timeList) {
//        this.id = id;
//        this.pid = pid;
//        this.label = label;
//        this.dept = dept;
//        this.leader = leader;
//        this.timeList = timeList;
//    }

    public static List<ProjectDetailVO> getTreeResult(List<ProjectDetailVO> list, String root) {
        List<ProjectDetailVO> listResult = new ArrayList<>();
        for (ProjectDetailVO t : list) {
            if (t.getPid().equals(root)) {
                listResult.add(t);// 得到父类
            }
        }
        secondTree(list, listResult, root);
        return listResult;
    }

    private static void secondTree(List<ProjectDetailVO> list, List<ProjectDetailVO> listResult, String root) {
        for (ProjectDetailVO tResult : listResult) {
            List<ProjectDetailVO> childrenList = new ArrayList<>();
            for (ProjectDetailVO t : list) {
                if (t.getPid().equals(root)) {
                    continue;
                }
                if (tResult.getId().equals(t.getPid())) {
                    childrenList.add(t);
                }
            }
            tResult.setChildren(childrenList);
            if (!tResult.getChildren().isEmpty()) {
                secondTree(list, tResult.getChildren(), root);
            }
        }
    }

}
