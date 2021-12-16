package com.sen.springboot.common;

public enum SysRoleEnum {
    SUPER(0),
    ADMIN(1),
    USER(2),
    VISITOR(3);

    private int roleId;

    SysRoleEnum(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
