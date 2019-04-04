package com.poc.loginpoc.util;

import android.content.SharedPreferences;
import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poc.loginpoc.response.PulseAppList;
import com.poc.loginpoc.response.UserInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferences {

    private static final String USERS_KEY = "users";

    private static final String CURRENT_USER_KEY = "current_user";

    private static final String FINGERPRINT_SUPPORT = "fingerprint_support";

    private static final String INSPECTION_TAB_LAST_VISIT = "tab_last_visit";

    private static final  String TEMPLATE_PAGE_LAST_VISIT = "template_page_last_visit";

    private static final  String BUILDINGS_PAGE_LAST_VISIT = "buildings_page_last_visit";

    private static final String VOICEBOT_SESSION_ATTRIBUTES_KEY = "session_attributes";

    private static final String BUILDING_KEY = "last_selected_building";

    private static final String TEMPLATE_KEY = "last_selected_template";

    private static final String RESPONSE_TAB_LAST_VISIT = "response_tab_last_visit";

    private final SharedPreferences _prefs;

    @Inject
    public AppPreferences(SharedPreferences sharedPreferences) {
        _prefs = sharedPreferences;
    }

    public ArrayList<UserInfo> getAppUsers() {
        ArrayList<UserInfo> appUsers = null;
        try {
            Gson gson = new Gson();
            String json = _prefs.getString(USERS_KEY, "");
            if (!TextUtils.isEmpty(json)) {
                Type type = new TypeToken<List<UserInfo>>(){}.getType();
                appUsers = gson.fromJson(json, type);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        if (appUsers == null) {
            return new ArrayList<>();
        }
        return appUsers;
    }

    public void setAppUsers(List<UserInfo> users) {
        SharedPreferences.Editor editor = _prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        editor.putString(USERS_KEY, json);
        editor.apply();
    }

    public String getCurrentUser() {
        return _prefs.getString(CURRENT_USER_KEY, "");
    }

    public void setCurrentUser(String username) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putString(CURRENT_USER_KEY, username);
        editor.apply();
    }

    public UserInfo getCurrentUserInfo() {
        List<UserInfo> users = getAppUsers();
        for (int index = 0; index < users.size(); ++index) {
            UserInfo user = users.get(index);
            if (user.getUserName() != null && user.getUserName().equals(getCurrentUser())) {
                return user;
            }
        }
        return null;
    }

    public UserInfo getUserInfoByName(String username) {
        List<UserInfo> users = getAppUsers();
        for (int index = 0; index < users.size(); ++index) {
            UserInfo user = users.get(index);
            if (user.getUserName() != null && user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(UserInfo userInfo) {
        List<UserInfo> users = getAppUsers();
        for (int index = 0; index < users.size(); ++index) {
            UserInfo user = users.get(index);
            if (user.getUserName() != null && user.getUserName().equals(userInfo.getUserName())) {
                //user exists
                users.set(index, userInfo);
                setAppUsers(users);
                return;
            }
        }
        users.add(userInfo);
        setAppUsers(users);
    }

    public void deleteUser(UserInfo userInfo) {
        List<UserInfo> users = getAppUsers();
        for (int index = 0; index < users.size(); ++index) {
            UserInfo user = users.get(index);
            if (user.getUserName() != null && user.getUserName().equals(userInfo.getUserName())) {
                //user exists
                users.remove(index);
                setAppUsers(users);
                return;
            }
        }
        users.add(userInfo);
        setAppUsers(users);
    }

    public boolean isFingerprintSupported() {
        return _prefs.getBoolean(FINGERPRINT_SUPPORT, false);
    }

    public void setFingerprintSupported(boolean supported) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putBoolean(FINGERPRINT_SUPPORT, supported);
        editor.apply();
    }

    public Map<String, String> getVoiceBotSessionAttributes() {
        Map<String, String> sessionAttributes = null;
        try {
            Gson gson = new Gson();
            String json = _prefs.getString(VOICEBOT_SESSION_ATTRIBUTES_KEY, "");
            if (!TextUtils.isEmpty(json)) {
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                sessionAttributes = gson.fromJson(json, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionAttributes == null) {
            return new HashMap<>();
        }
        return sessionAttributes;
    }


    public void setVoiceBotSessionAttributes(Map<String, String> sessionAttributes) {
        SharedPreferences.Editor editor = _prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sessionAttributes);
        editor.putString(VOICEBOT_SESSION_ATTRIBUTES_KEY, json);
        editor.apply();
    }

    public long getInspectionTabLastVisit() {
        return _prefs.getLong(INSPECTION_TAB_LAST_VISIT, 0);
    }

    public void setInspectionTabLastVisit(long timestamp) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putLong(INSPECTION_TAB_LAST_VISIT, timestamp);
        editor.apply();
    }

    /*Saving and Fetching last visit time
                for loading data for Buildings*/
    public long getBuildingsPageLastVisit() {
        return _prefs.getLong(BUILDINGS_PAGE_LAST_VISIT, 0);
    }

    public void setBuildingsPageLastVisit(long timestamp) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putLong(BUILDINGS_PAGE_LAST_VISIT, timestamp);
        editor.apply();
    }


    /*Saving and Fetching last visit time
            for loading data for templates*/
    public long getTemplatesPageLastVisit() {
        return _prefs.getLong(TEMPLATE_PAGE_LAST_VISIT, 0);
    }

    public void setTemplatePageLastVisit(long timestamp) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putLong(TEMPLATE_PAGE_LAST_VISIT, timestamp);
        editor.apply();
    }

    /**
     * Returns the Pulse Tech access token
     *
     * @return the access token
     */
    public String getPulseTechToken() {
        String accessToken = "";
        UserInfo userInfo = getUserInfoByName(getCurrentUser());
        List<PulseAppList> appsList = userInfo.getPulseAppList();
        for (int i = 0; i < appsList.size(); i++) {
            if (appsList.get(i).getAppName()
                    .equalsIgnoreCase("Pulse Tech")) {
                accessToken = appsList.get(i).getAccessToken();
                break;
            }
        }
        return accessToken;
    }

    public long getResponseTabLastVisit() {
        return _prefs.getLong(RESPONSE_TAB_LAST_VISIT, 0);
    }

    public void setResponseTabLastVisit(long timestamp) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putLong(RESPONSE_TAB_LAST_VISIT, timestamp);
        editor.apply();
    }
}
