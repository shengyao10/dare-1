package com.dare.modules.hrm.controller;

import com.dare.common.vo.PageResult;
import com.dare.modules.hrm.entity.Position;
import com.dare.modules.hrm.service.PositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.controller
 * @Date: 2020/8/4 9:52
 * @Description:
 * @version: 1.0
 */
@RestController
@RequestMapping("/hrm/position")
@Api(tags = "职位信息")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @ApiOperation("分页查询职位信息")
    @GetMapping(value = "/list")           //查询某部门所有职位
    public PageResult<List> show(@RequestParam("deptId") Integer deptId, @RequestParam("pageNo") String pageNo,
                                 @RequestParam("pageSize") String pageSize){
        PageResult<List> result = new PageResult<List>();
        int pagenum=1,pagesize=5;
        if(pageNo==null) pagenum=1;
        else pagenum=Integer.parseInt(pageNo);
        if(pageSize==null) pagesize=5;
        else pagesize=Integer.parseInt(pageSize);
        PageHelper.startPage(pagenum,pagesize);
        List<Position> list=positionService.ShowPosition(deptId); //将职位信息放入list
        PageInfo<Position> pageInfo =new PageInfo<Position>(list);
//        System.out.println(pageInfo.getTotal());  //输出数据总数
//        System.out.println(pageSize);             //输出每页显示条数
//        System.out.println(pageNo);                //输出当前页
//        System.out.println(pageInfo.getTotal()/pagesize);//输出总页数
//        List<String> page=new ArrayList<>();
//        page.add(""+pageInfo.getTotal());
//        page.add(pageSize);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setCurrent(pageInfo.getPageNum());
        result.setSize(pageInfo.getPageSize());
        result.setResult(list);
        result.setResult(list);
        return result;
    }



    @ApiOperation("修改职位信息")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Boolean edit(@RequestParam("positionId") int positionId,@RequestParam("positionName") String positionName,
                        @RequestParam("description") String description,@RequestParam("deptId") int deptId){
        boolean a= false;
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        a =(positionService.EditPosition(positionId,positionName,description,time.format(new Date()),deptId));
        return a;
    }


}
