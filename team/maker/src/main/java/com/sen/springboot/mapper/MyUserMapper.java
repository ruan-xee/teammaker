package com.sen.springboot.mapper;


import com.sen.springboot.model.User;
import org.apache.ibatis.annotations.Insert;

public interface MyUserMapper extends UserMapper {
    @Insert({
            "insert into tb_user(id, ",
            "username, password, ",
            "phone, avater, ",
            "salt",
            ")",
            "values (#{id,jdbcType=BIGINT},  ",
            "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
            "#{phone,jdbcType=VARCHAR}, #{avater,jdbcType=VARCHAR}, ",
            "#{salt,jdbcType=VARCHAR})"
    })
    @Override
    int insert(User record);
}
