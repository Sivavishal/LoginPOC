package com.poc.loginpoc.login;


import com.poc.loginpoc.request.LoginRequest;
import com.poc.loginpoc.response.LoginResponse;
import com.poc.loginpoc.service.IServiceCall;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
@Singleton
public class LoginManager {

    private final IServiceCall _loginService;

    @Inject
    public LoginManager(IServiceCall loginService) {
        _loginService = loginService;
    }

    public Call<LoginResponse> login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        return _loginService.getAuthToken(request);
    }
}
