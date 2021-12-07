package com.sen.springboot.common;

public class RegexpUtils {
    public static final String PHONE="[0-9-()（）]{7,18}";
    public static final String EMAIL="\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    //验证手机号
    public static boolean checkPhone(String phone){
        return phone.matches(PHONE);
    }

    //验证邮箱
    public static boolean checkEmail(String email){
        return email.matches(EMAIL);
    }
}

