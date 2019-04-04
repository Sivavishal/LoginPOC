package com.poc.loginpoc.service;

import com.poc.loginpoc.request.LoginRequest;
import com.poc.loginpoc.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServiceCall {
    @POST("Authentication/Login")
    Call<LoginResponse> getAuthToken(@Body LoginRequest request);
}
