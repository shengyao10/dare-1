package com.dare.common.constant;

/**
 * @Author: shengyao
 * @Package: com.lbj.common
 * @Date: 2020/10/9 10:46
 * @Description:
 * @version: 1.0
 */
public interface CacheConstant {

    /**
     * apqp第0阶段
     */
    public static final String APQP_STEP_0 = "apqp:step:0";

    /**
     *  某一部门所有员工缓存
     */
    public static final String SYS_EMPLOYEES_DEPT_CACHE = "sys:cache:employee:deptNo:";

    /**
     * 字典信息缓存
     */
    public static final String SYS_DICT_CACHE = "sys:cache:dict";
    /**
     * 表字典信息缓存
     */
    public static final String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";

    /**
     * 数据权限配置缓存
     */
    public static final String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:datarules";

    /**
     * 缓存用户信息
     */
    public static final String SYS_USERS_CACHE = "sys:cache:user";

    /**
     * 全部部门信息缓存
     */
    public static final String SYS_DEPARTS_CACHE = "sys:cache:depart:alldata";


    /**
     * 全部部门ids缓存
     */
    public static final String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allids";


    /**
     * 测试缓存key
     */
    public static final String TEST_DEMO_CACHE = "test:demo";

    /**
     * 字典信息缓存
     */
    public static final String SYS_DYNAMICDB_CACHE = "sys:cache:dbconnect:dynamic:";
}
