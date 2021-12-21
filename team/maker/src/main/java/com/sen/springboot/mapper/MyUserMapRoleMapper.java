package com.sen.springboot.mapper;

import com.sen.springboot.model.UserMapRole;
import org.apache.ibatis.annotations.Insert;

public interface MyUserMapRoleMapper extends UserMapRoleMapper{
    @Insert({
            "insert into tb_user_role (id, user_id, ",
            "role_id",
            ")",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{roleId,jdbcType=BIGINT})"
    })
    @Override
    int insert(UserMapRole record);
}
