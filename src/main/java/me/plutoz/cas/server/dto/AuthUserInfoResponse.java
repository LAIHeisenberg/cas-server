package me.plutoz.cas.server.dto;

import java.util.Map;
import java.util.Set;

/**
 * 远程用户校验返回内容
 * Created by zhangjianfeng on 2017/9/5.
 */
public class AuthUserInfoResponse {
    private boolean auth;
    private String msg;

    private String userName;
    private String phoneNo;
    private Map<String, Set<String>> data;
    private Map<String, Set<String>> func;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Map<String, Set<String>> getData() {
        return data;
    }

    public void setData(Map<String, Set<String>> data) {
        this.data = data;
    }

    public Map<String, Set<String>> getFunc() {
        return func;
    }

    public void setFunc(Map<String, Set<String>> func) {
        this.func = func;
    }
}
