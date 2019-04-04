package com.poc.loginpoc.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PulseAppList {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("appUri")
    @Expose
    private String appUri;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("userType")
    @Expose
    private String userType;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUri() {
        return appUri;
    }

    public void setAppUri(String appUri) {
        this.appUri = appUri;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

