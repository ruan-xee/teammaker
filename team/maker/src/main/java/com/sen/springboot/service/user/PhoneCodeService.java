package com.sen.springboot.service.user;

import com.sen.springboot.common.RedisUtil;
import com.sen.springboot.exception.ServiceException;
import com.sen.springboot.exception.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class PhoneCodeService {
    @Resource
    RedisUtil redisUtil;

    @Value("${spring.register.expire-time}")
    private long expireTime;

    @Value("${spring.register.lock-time}")
    private long lockTime;

    public void sendCode(String phoneNumber){
        //发送验证码服务，暂时以固定格式9999存储
        if (!checkExpire(phoneNumber)){
            throw new ServiceException(ServiceExceptionEnum.CODE_SEND_FREQUENTY);
        }
        redisUtil.set(phoneNumber, "9999", expireTime);
    }

    //判断验证码是否相等
    public boolean checkCode(String key, String code) {
        try {
            String inCode = (String) redisUtil.get(key);
            if (inCode.equals(code)) {
                return true;
            }
        } catch (NullPointerException e) { //到这里是用户已经注册，redis已经删除key
            throw new ServiceException(ServiceExceptionEnum.CODE_INVALID);
        }
        return false;
    }

    //判断验证码是否过期
    public boolean checkExpire(String key) {
        String inCode = (String) redisUtil.get(key);
        //如果没有说明已经失效，可以重新发送验证码
        if (inCode == null) {
            return true;
        }
        long curExpireTime = redisUtil.getExpire(key);
        //一分钟内只能发一次
        return expireTime - curExpireTime >= lockTime;
    }

    //产生4位随机数
    protected long generateRandomNumber(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long) (Math.random() * 9 * Math.pow(10, n - 1)) + (long) Math.pow(10, n - 1);
    }

    public boolean isExistCode(String key){
        String inCode = (String) redisUtil.get(key);
        if (Objects.isNull(inCode)){
            return false;
        } else {
            return true;
        }
    }
}
