package com.dare.modules.test.service;
import com.dare.modules.test.entity.PermissionTest;
import com.dare.modules.test.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public void updateExamine(PermissionTest permission)
    {
        permissionMapper.updateExamine(permission);
    }
    public void updateJointlySign(PermissionTest permission)
    {
        permissionMapper.updateJointlySign(permission);
    }
    public List<PermissionTest> SelectAllJointlySign()
    {
        return  permissionMapper.SelectAllJointlySign();
    }
    public List<PermissionTest> SelectAllExamine()
    {
        return permissionMapper.SelectAllExamine();
    }

}
