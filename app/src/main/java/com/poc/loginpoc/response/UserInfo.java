package com.poc.loginpoc.response;

import com.poc.loginpoc.login.LoginPreference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class UserInfo {

    String _userName;

    String _pin;

    String _botAccessToken;

    String _botUserId;

    String _displayName;

    String _botAccessTokenCreated;

    int _botLanguage;

    int _defaultAppId = 0;

    boolean fingerprint = false;

    String _tokenExpiry;

    int _switchAppId = 0;

    LoginPreference _loginPreference = LoginPreference.Password;

    ArrayList<PulseAppList> _pulseAppList;

    public UserInfo(String username, ArrayList<PulseAppList> pulseAppList) {
        this._userName = username;
        this._pulseAppList = pulseAppList;
    }

    /**
     * Calculates an SHA-256 hash of the username.
     *
     * @return The SHA-256 hash of the username, or null if algorithm isn't available
     */
    public String getHashedUsername(String username) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        digest.reset();
        final byte[] bytes = digest.digest(username.getBytes());
        return convertBytesToHexString(bytes);
    }

    /**
     * Converts a String in a byte array to a hexadecimal string
     *
     * @param bytes The incoming bytes to be converted
     *
     * @return The hexadecimal string
     */
    public static String convertBytesToHexString(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte).toUpperCase(Locale.ENGLISH);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        this._userName = userName;
    }

    public String getPin() {
        return _pin;
    }

    public void setPin(String pin) {
        this._pin = pin;
    }

    public String getBotAccessToken() {
        return _botAccessToken;
    }

    public void setBotAccessToken(String botAccessToken) {
        this._botAccessToken = botAccessToken;
    }

    public String getBotUserId() {
        return _botUserId;
    }

    public void setBotUserId(String botUserId) {
        this._botUserId = botUserId;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public void setDisplayName(String displayName) {
        this._displayName = displayName;
    }

    public String getBotAccessTokenCreated() {
        return _botAccessTokenCreated;
    }

    public void setBotAccessTokenCreated(String botAccessTokenCreated) {
        this._botAccessTokenCreated = botAccessTokenCreated;
    }

    public int getBotLanguage() {
        return _botLanguage;
    }

    public void setBotLanguage(int botLanguage) {
        this._botLanguage = botLanguage;
    }

    public int getDefaultAppId() {
        return _defaultAppId;
    }

    public void setDefaultAppId(int defaultAppId) {
        this._defaultAppId = defaultAppId;
    }

    public LoginPreference getLoginPreference() {
        return _loginPreference;
    }

    public void setLoginPreference(LoginPreference loginPreference) {
        this._loginPreference = loginPreference;
    }

    public ArrayList<PulseAppList> getPulseAppList() {
        return _pulseAppList;
    }

    public void setPulseAppList(ArrayList<PulseAppList> pulseAppList) {
        this._pulseAppList = pulseAppList;
    }

    public boolean isFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(boolean fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getTokenExpiry() {
        return _tokenExpiry;
    }

    public void setTokenExpiry(String _tokenExpiry) {
        this._tokenExpiry = _tokenExpiry;
    }


    public int getSwitchAppId() {
        return _switchAppId;
    }

    public void setSwitchAppId(int _switchAppId) {
        this._switchAppId = _switchAppId;
    }

}
