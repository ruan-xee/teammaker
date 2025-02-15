package com.sen.springboot.service;

import com.sen.springboot.mapper.*;
import com.sen.springboot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ShiroService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    PermissionMapper permissionMapper;
    @Resource
    UserMapRoleMapper userMapRoleMapper;
    @Resource
    MyUserMapRoleMapper myUserMapRoleMapper;
    @Resource
    RoleMapPermissionMapper roleMapPermissionMapper;

    private List<UserMapRole> getUserMapRoles(long userId) {
        UserMapRoleExample userMapRolesExample = new UserMapRoleExample();
        userMapRolesExample.createCriteria().andUserIdEqualTo(userId);
        return userMapRoleMapper.selectByExample(userMapRolesExample);
    }

    public Set<Role> getUserRoles(long userId) {
        Set<Role> roles = new HashSet<>();
        List<UserMapRole> userMapRoles = getUserMapRoles(userId);
        for (UserMapRole mapRole : userMapRoles) {
            Role sysRole = roleMapper.selectByPrimaryKey(mapRole.getId());
            roles.add(sysRole);
        }
        return roles;
    }

    public Set<Permission> getUserPermissions(long userId) {
        log.info(String.valueOf(userId));
        Set<Permission> permissions = new HashSet<>();
        List<UserMapRole> userMapRoles = getUserMapRoles(userId);
        for (UserMapRole mapRole : userMapRoles) {
            RoleMapPermissionExample roleMapPermissionExample = new RoleMapPermissionExample();
            roleMapPermissionExample.createCriteria().andIdEqualTo(mapRole.getId());
            List<RoleMapPermission> roleMapPermissionList = roleMapPermissionMapper.selectByExample(roleMapPermissionExample);

            for (RoleMapPermission roleMapPermission : roleMapPermissionList) {
                Permission permit = permissionMapper.selectByPrimaryKey(roleMapPermission.getId());
                permissions.add(permit);
            }
        }
        return permissions;
    }

    @Transactional(transactionManager = "productTransactionManager")
    public void setUserMapRoles(long userId, int roleId) {
        UserMapRole userMapRoles = new UserMapRole();
        userMapRoles.setUserId(userId);
        userMapRoles.setRoleId((long) roleId);
        myUserMapRoleMapper.insert(userMapRoles);
    }
}
