package com.sen.springboot.service.user;

import com.sen.springboot.common.RedisUtil;
import com.sen.springboot.common.SnowFlake;
import com.sen.springboot.common.SysRoleEnum;
import com.sen.springboot.dto.ResetPwd.ResetPwdDto;
import com.sen.springboot.dto.register.RegisterDto;
import com.sen.springboot.mapper.MyUserMapper;
import com.sen.springboot.mapper.UserMapper;
import com.sen.springboot.model.User;
import com.sen.springboot.model.UserExample;
import com.sen.springboot.service.ShiroService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    MyUserMapper myUserMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ShiroService shiroService;
    @Resource
    SnowFlake snowFlake;
    @Resource
    RedisUtil redisUtil;

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

        redisUtil.del(userDto.getPhone());
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

    @Transactional(transactionManager = "productTransactionManager") //事务回滚是根据异常捕获来回滚的
    public int updatePwdByPhone(ResetPwdDto resetPwdDto){
        User user = getUserByPhone(resetPwdDto.getPhone());
        if (user == null){
            return ResetPwdStatusEnum.USER_NOT_EXIST.getStatus();
        }

        String password = resetPwdDto.getPassword();
        if (password.equals("")){
            return ResetPwdStatusEnum.EMPTY_PASSWORD.getStatus();
        }

        //生成新盐值加密
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodePassword = new SimpleHash("md5", user.getPassword(), salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodePassword);
        int i = userMapper.updateByPrimaryKey(user);
        if (i == 1){
            redisUtil.del(resetPwdDto.getPhone());
            return ResetPwdStatusEnum.SUCCESS.getStatus();
        } else {
            return ResetPwdStatusEnum.DB_RESET_ERROR.getStatus();
        }
    }

}
