package com.bhole.shop.common.base.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/6/5 15:37
 * @version: 1.0
 */
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 364731003794271747L;

    private Long userId;
    private String userName;
    private Long empId;
    private String empCode;
    private String token;
    private String userType;
    private String orgCode;
    private Long orgId;
    private String orgType;

    private Map<String, String> attrMap;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}
