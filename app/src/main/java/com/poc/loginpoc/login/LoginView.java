package com.poc.loginpoc.login;


import com.poc.loginpoc.mvp.MvpView;

import androidx.annotation.StringRes;
import de.keyboardsurfer.android.widget.crouton.Style;


public interface LoginView extends MvpView {



    void showCrouton( int stringId, Style style);

    void showProgress(boolean visible);

    CharSequence getUsername();

    CharSequence getPassword();

    void login();
}

