package com.poc.loginpoc.login;

import android.text.TextUtils;
import android.util.Log;


import com.poc.loginpoc.R;
import com.poc.loginpoc.mvp.MvpPresenterBase;
import com.poc.loginpoc.response.LoginResponse;
import com.poc.loginpoc.response.PulseAppList;
import com.poc.loginpoc.response.UserInfo;
import com.poc.loginpoc.util.AppPreferences;
import com.poc.loginpoc.util.PulseCroutonStyle;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends MvpPresenterBase<LoginView> {

    private LoginManager _loginManager;

    private AppPreferences _appPreferences;


    public LoginPresenter(LoginManager loginManager, AppPreferences appPreferences) {
        _loginManager = loginManager;
        _appPreferences = appPreferences;
    }

    public void onLogin() {
        final LoginView view = getView();

        if (TextUtils.isEmpty(view.getUsername())) {
            view.showCrouton(R.string.username_error, PulseCroutonStyle.INFO);
        } else if (TextUtils.isEmpty(view.getPassword())) {
            view.showCrouton(R.string.password_error, PulseCroutonStyle.INFO);
        } else {
            view.showProgress(true);

            final String username = view.getUsername().toString();
            final String password = view.getPassword().toString();

            Call<LoginResponse> loginResponseCall = _loginManager.login(username, password);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d("TAG", "Response: " + response.body());

                    view.showProgress(false);

                    if (response.body().getStatusCode() == 200) {
                       /* List<PulseAppList> appsList = response.body().getPulseAppList();
                        ArrayList<PulseAppList> apps = new ArrayList<>(appsList.size());
                        apps.addAll(appsList);

                        String currentUser = response.body().getUserName();
                        UserInfo currentUserInfo = _appPreferences.getUserInfoByName(currentUser);
                        if (currentUserInfo == null) {
                            //User has logged in for the first time
                            currentUserInfo = new UserInfo(currentUser, apps);
                            currentUserInfo.setLoginPreference(LoginPreference.Password);
                        }
                        currentUserInfo.setTokenExpiry(response.body().getTokenExpiry());
                        currentUserInfo.setPulseAppList(apps);
                        _appPreferences.setCurrentUser(currentUser);
                        _appPreferences.addUser(currentUserInfo);*/


                        view.login();
                    } else {
                        view.showCrouton(R.string.invalid_credentials, PulseCroutonStyle.ALERT);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                    //show error
                    view.showProgress(false);
                    if (throwable instanceof UnknownHostException) {
                        view.showCrouton(R.string.network_error, PulseCroutonStyle.ALERT);
                    } else {
                        view.showCrouton(R.string.generic_error, PulseCroutonStyle.ALERT);
                    }
                }
            });
        }
    }

    public void onForgotPassword() {
        getView().showCrouton(R.string.contact_help_desk, PulseCroutonStyle.INFO);
    }


}
