package com.poc.loginpoc.login;

public enum LoginPreference {

    /**
     * Username/password login
     */
    Password,

    /**
     * 4-digit code login
     */
    Passcode,

    /**
     * Login using device registered ic_fingerprint
     */
    Fingerprint
}
