package com.sen.springboot.service;

import com.sen.springboot.mapper.UserMapper;
import com.sen.springboot.model.User;
import com.sen.springboot.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserByPhone(String phone) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty())
            return users.get(0);
        return null;
    }
}
