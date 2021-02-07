<!--<?xml version="1.0" encoding="UTF-8" ?>-->
<!--<!DOCTYPE mapper-->
<!--        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"-->
<!--        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">-->
<!--<mapper namespace="com.dare.modules.apqp.mapper.ProjectMapper">-->

<!--    <insert id="save" parameterType="Project">-->
<!--        insert into-->
<!--        project(id, project_no, project_name, customer_no, start_time, end_time, project_state, project_task, complete_task, created_by, gmt_created, gmt_modified)-->
<!--        values-->
<!--        (#{id}, #{projectNo}, #{projectName}, #{customerNo}, #{startTime}, #{endTime}, #{projectState}, #{projectTask}, #{completeTask}, #{createdBy}, #{gmtCreated}, #{gmtModified})-->
<!--    </insert>-->

<!--    <select id="findAll" resultType="com.dare.modules.project.vo.ProjectVO">-->
<!--        select p.id, p.project_no, p.project_name, p.project_state, p.created_by, p.gmt_created-->
<!--        from project p-->
<!--        order by p.gmt_created desc-->
<!--    </select>-->

<!--    <select id="queryByProNo" resultType="com.dare.modules.project.vo.ProjectVO">-->
<!--        select p.id, p.project_no, p.project_name, p.project_state, p.created_by, p.gmt_created-->
<!--        from project p-->
<!--        where p.project_no=#{projectNo}-->
<!--    </select>-->

<!--    <select id="queryByProState" resultType="com.dare.modules.project.vo.ProjectVO">-->
<!--        select p.id, p.project_no, p.project_name, p.project_state, p.created_by, p.gmt_created-->
<!--        from project p-->
<!--        where p.project_state=#{projectState}-->
<!--        order by p.gmt_created desc-->
<!--    </select>-->

<!--    <select id="getProCount" resultType="Integer">-->
<!--        select count(*) from project-->
<!--        order by id-->
<!--    </select>-->



<!--</mapper>-->