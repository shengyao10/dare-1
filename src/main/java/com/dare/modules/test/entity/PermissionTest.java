package com.dare.modules.test.entity;
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
public class PermissionTest {
    private String empNo;
    private int step1;
    private int step2;
    private int step3;
    private int step4;
    private String position;
    private String dept;
    private String empName;

}
