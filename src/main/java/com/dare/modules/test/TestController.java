//package com.dare.modules.test;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.dare.common.vo.PageResult;
//import com.dare.modules.hrm.entity.Employee;
//import com.dare.modules.hrm.service.EmployeeService;
//import com.dare.utils.JsonResourceUtil;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authz.annotation.RequiresRoles;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.*;
//import java.util.List;
//import java.util.Scanner;
//
//
///**
// * @Author: shengyao
// * @Package: com.dare.modules.test
// * @Date: 2020/11/9 17:08
// * @Description: 测试
// * @version: 1.0
// */
//@Slf4j
//@RestController
//public class TestController {
//    @Autowired
//    private EmployeeService employeeService;
//
//
//
//    @Value("classpath:json/test.json")
//    private Resource jsonData;
//
//    //    @RequestMapping("/json")
//    public JSONArray test(){
//
//        String path1 = JsonResourceUtil.class.getClassLoader().getResource("json/test.json").getPath();
//        String s = JsonResourceUtil.readJsonFile(path1);
////        JSONObject jobj = JSON.parseObject(s);
//        JSONArray jsonArray = JSONObject.parseArray(s);
//        return jsonArray;
//    }
//
////    @RequestMapping("/json1")
//    public JSONArray test1() throws IOException {
//        File file = jsonData.getFile();
//        String jsonData = this.jsonRead(file);
//        JSONArray jsonArray = JSONObject.parseArray(jsonData);
//        return  jsonArray;
//    }
//
//    private String jsonRead(File file){
//        Scanner scanner = null;
//        StringBuilder buffer = new StringBuilder();
//        try {
//            scanner = new Scanner(file, "utf-8");
//            while (scanner.hasNextLine()) {
//                buffer.append(scanner.nextLine());
//            }
//        } catch (Exception e) {
//
//        } finally {
//            if (scanner != null) {
//                scanner.close();
//            }
//        }
//        return buffer.toString();
//    }
//
//    @RequestMapping("/json")
//    public JSONArray test2() throws IOException {
////        Map<String, JSONArray> map = new HashMap<>();
//        final String path = "json/test.json";
//        ClassPathResource resource = new ClassPathResource(path);
//        InputStream is = resource.getInputStream();
//        StringBuffer sb;
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(is,"utf8"));
//
//            sb = new StringBuffer();
//
//            String data;
//            while ((data = br.readLine()) != null) {
//                sb.append(data);
//            }
//        } finally {
//            br.close();
//        }
//
//        JSONArray jsonArray = JSONObject.parseArray(sb.toString());
////        map.put("ApqpData",jsonArray);
//        return  jsonArray;
//
//    }
//
//
//    @GetMapping("/list")
//    @RequiresRoles("root")
//    public PageResult<List<Employee>> getList(@RequestParam(required = true, defaultValue = "1", value = "pageNo") Integer pageNo,
//                                              @RequestParam(required = true, defaultValue = "10", value = "pageSize") Integer pageSize) {
//        PageResult<List<Employee>> result = new PageResult<>();
//        PageHelper.startPage(pageNo, pageSize);
//
//
//        try {
//            List<Employee> employeeList = employeeService.findAll();
//            PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
////            System.out.println(employeeList);
//            log.info(String.valueOf(employeeList));
//            result.setTotal(pageInfo.getTotal());
//            result.setPages(pageInfo.getPages());
//            result.setCurrent(pageInfo.getPageNum());
//            result.setSize(pageInfo.getPageSize());
//            result.setResult(employeeList);
//
//        } finally {
//            PageHelper.clearPage();
//        }
////        Page<Employee> employeePage = employeeService.getList(new Page<>(pageNo, pageSize));
////        System.out.println(employeePage);
////        result.setResult(employeePage);
//
//        return result;
//    }
//
//}
