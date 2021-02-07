package com.dare.modules.apqp.controller;

import com.dare.common.constant.CacheConstant;
import com.dare.common.vo.Result;
import com.dare.modules.apqp.service.APQPService;
import com.dare.modules.apqp.vo.APQPStepVO;
import com.dare.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.controller
 * @Date: 2020/11/19 20:48
 * @Description: APQP
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/apqp/APQP")
@Api(tags = "APQP")
public class APQPController {

    @Autowired
    private APQPService apqpService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("获取APQP第0阶段任务")
    @RequestMapping(value = "/getStep0", method = RequestMethod.GET)
    public Result<List<APQPStepVO>> getStep0() {
        Result<List<APQPStepVO>> result = new Result<>();

        try {
//            if (redisUtil.exists(CacheConstant.APQP_STEP_0)) {
//                log.info("通过缓存读取APQP第0阶段" + redisUtil.get(CacheConstant.APQP_STEP_0));
//                return (Result<List<APQPStepVO>>) redisUtil.get(CacheConstant.APQP_STEP_0);
//            }
            List<APQPStepVO> list = null;
            list = apqpService.getStep0();
//        System.out.println("list: " + list);
            result.setResult(list);
            result.success("查询成功！");
//            log.info("APQP第0阶段存入缓存" + result);
//            redisUtil.set(CacheConstant.APQP_STEP_0, result);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }

        return result;
    }

    @ApiOperation("获取APQP所有流程")
    @RequestMapping(value = "/getAllStep", method = RequestMethod.GET)
    public Result<List<APQPStepVO>> getAllStep() {
        Result<List<APQPStepVO>> result = new Result<>();

        try {
            List<APQPStepVO> list = apqpService.getAllStep();
            result.setResult(list);
            result.success("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
//            throw new DareException();
        }

        return result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<List<APQPStepVO>> test() {

        Result<List<APQPStepVO>> result = new Result<>();
        List<APQPStepVO> list = apqpService.test();

//        System.out.println("list: " + list);
//        String str = list.toString();
//        str.replace("0","-1").replace("9","0");
//        List<String> list1 =  Arrays.asList(str);

        result.setResult(list);
//        if (!list.isEmpty()){
//            result.setResult(list);
//            result.success("查询成功");
//        }else {
//            result.setMessage("未找到符合项");
//            result.setSuccess(false);
//        }

        return result;
    }


}
