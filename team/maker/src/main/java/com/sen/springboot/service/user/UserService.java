package com.sen.springboot.service.user;

import com.sen.springboot.common.SnowFlake;
import com.sen.springboot.common.SysRoleEnum;
import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sen.springboot.dto.register.RegisterDto;
import com.sen.springboot.exception.ServiceException;
import com.sen.springboot.exception.ServiceExceptionEnum;
import com.sen.springboot.mapper.MyUserMapper;
import com.sen.springboot.mapper.UserMapper;
import com.sen.springboot.model.User;
import com.sen.springboot.model.UserExample;
import com.sen.springboot.service.ShiroService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    MyUserMapper myUserMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SnowFlake snowFlake;
    @Autowired
    ShiroService shiroService;

    public User addUser(RegisterDto userDto) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        long userId = snowFlake.nextId();
        User user = userDto.transToUser();
        String encodePassword = new SimpleHash("md5", user.getPassword(), salt, times).toString();
        user.setId(userId);
        user.setSalt(salt);
        user.setPassword(encodePassword);
        Object result = myUserMapper.insert(user);
        if (result == null) {
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "productTransactionManager") //事务回滚是根据异常捕获来回滚的
    public int addUserByPhone(RegisterDto userDto) {
        String password = userDto.getPassword();
        if (password.equals("")) {
            return AddUserStatusEnum.EMPTY_PASSWORD.getStatus();
        }
        if (getUserByPhone(userDto.getPhone()) != null) {
            return AddUserStatusEnum.USER_EXIST.getStatus();
        }
        User result = addUser(userDto);
        if (result == null){
            return AddUserStatusEnum.DB_ADD_ERROR.getStatus();
        }

//        redisUtil.del(userDto.getPhone());
        shiroService.setUserMapRoles(result.getId(), SysRoleEnum.USER.getRoleId());
        return AddUserStatusEnum.SUCCESS.getStatus();
    }

    public User getUserByPhone(String phone) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty())
            return users.get(0);
        return null;
    }

}
