package com.poc.loginpoc.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("tokenExpiry")
    @Expose
    private String tokenExpiry;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("isActive")
    @Expose
    private Object isActive;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("isPasswordReset")
    @Expose
    private Boolean isPasswordReset;
    @SerializedName("enableEncryption")
    @Expose
    private Boolean enableEncryption;
    @SerializedName("emailId")
    @Expose
    private Object emailId;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("statusDescription")
    @Expose
    private String statusDescription;
    @SerializedName("pulseAppList")
    @Expose
    private List<PulseAppList> pulseAppList = null;

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getIsPasswordReset() {
        return isPasswordReset;
    }

    public void setIsPasswordReset(Boolean isPasswordReset) {
        this.isPasswordReset = isPasswordReset;
    }

    public Boolean getEnableEncryption() {
        return enableEncryption;
    }

    public void setEnableEncryption(Boolean enableEncryption) {
        this.enableEncryption = enableEncryption;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public List<PulseAppList> getPulseAppList() {
        return pulseAppList;
    }

    public void setPulseAppList(List<PulseAppList> pulseAppList) {
        this.pulseAppList = pulseAppList;
    }
}