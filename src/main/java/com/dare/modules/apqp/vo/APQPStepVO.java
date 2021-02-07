package com.dare.modules.apqp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.entity
 * @Date: 2020/11/19 10:50
 * @Description: APQP第0步
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class APQPStepVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "pid")
    private String pid;

    @ApiModelProperty(value = "标签（阶段名称）")
    private String label;

    @ApiModelProperty(value = "子节点")
    public List<APQPStepVO> children;

//    @ApiModelProperty(value = "输出文件")
//    private String out_record;

    public APQPStepVO(String id, String pid, String label) {
        this.id = id;
        this.pid = pid;
        this.label = label;
    }

    public static List<APQPStepVO> getTreeResult(List<APQPStepVO> list, String root) {
        List<APQPStepVO> listResult = new ArrayList<>();
        for (APQPStepVO t : list) {
            if (t.getPid().equals(root)) {
                listResult.add(t);// 得到父类
            }
        }
        secondTree(list, listResult, root);
        return listResult;
    }

    private static void secondTree(List<APQPStepVO> list, List<APQPStepVO> listResult, String root) {
        for (APQPStepVO tResult : listResult) {
            List<APQPStepVO> childrenList = new ArrayList<>();
            for (APQPStepVO t : list) {
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
