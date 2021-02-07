package com.dare.modules.test.control;

import com.dare.common.vo.Result;
import com.dare.modules.test.entity.PermissionTest;
import com.dare.modules.test.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @GetMapping(value = "/specForSh")
    public Result<?> updateexamine(@RequestParam(name = "identityNo") String empNo,
                             @RequestParam(name = "step") int step,
                             @RequestParam(name = "choice") int choice)
    {
        Result<?> result = new Result();
        try {
            PermissionTest permission = new PermissionTest();
            permission.setEmpNo(empNo);
            if (step == 1) permission.setStep1(choice);
            else if (step == 2) permission.setStep2(choice);
            else if (step == 3) permission.setStep3(choice);
            else if (step == 4) permission.setStep4(choice);
            permissionService.updateExamine(permission);
            result.success("成功！");
        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("失败！");
        }
        return result;

    }
    @GetMapping(value = "/specForHq")
    public Result<?> updateJointlySign(@RequestParam(name = "identityNo") String empNo,
                                   @RequestParam(name = "step") int step,
                                   @RequestParam(name = "choice") int choice)
    {
        Result<?> result = new Result();
        try {
            PermissionTest permission = new PermissionTest();
            permission.setEmpNo(empNo);
            if (step == 1) permission.setStep1(choice);
            else if (step == 2) permission.setStep2(choice);
            else if (step == 3) permission.setStep3(choice);
            else if (step == 4) permission.setStep4(choice);
            permissionService.updateJointlySign(permission);
            result.success("成功！");
        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("失败！");
        }
        return result;

    }
    @GetMapping(value = "/specForGetSh")
    public Result<List<PermissionTest>> showExamine(){
        Result<List<PermissionTest>> result = new Result<>();
        List<PermissionTest> examine=permissionService.SelectAllExamine();
        result.setResult(examine);
        return result;
    }
    @GetMapping(value = "/specForGetHq")
    public Result<List<PermissionTest>> showJointlySign(){
        Result<List<PermissionTest>> result = new Result<>();
        List<PermissionTest> jointlySign=permissionService.SelectAllJointlySign();
        result.setResult(jointlySign);

        return result;
    }



}
