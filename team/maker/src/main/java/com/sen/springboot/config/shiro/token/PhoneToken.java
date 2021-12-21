package com.sen.springboot.config.shiro.token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

public class PhoneToken extends UsernamePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {
    private String phone;
    private boolean rememberMe;
    private String host;


    @Override
    public Object getPrincipal() {
        return phone;
    }

    @Override
    public Object getCredentials() {
        return phone;
    }

    public PhoneToken() {
        this.rememberMe = false;
    }

    public PhoneToken(String phone) {
        this(phone, false, null);
    }

    public PhoneToken(String phone, boolean rememberMe) {
        this(phone, rememberMe, null);
    }

    public PhoneToken(String phone, boolean rememberMe, String host) {
        this.phone = phone;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    //@Override
    //public String toString() {
    //    return "PhoneToken{" +
    //            "phone='" + phone + '\'' +
    //            ", rememberMe=" + rememberMe +
    //            ", host='" + host + '\'' +
    //            '}';
    //}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(this.phone);
        sb.append(", rememberMe=").append(this.rememberMe);
        if (this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }

        return sb.toString();
    }
}
