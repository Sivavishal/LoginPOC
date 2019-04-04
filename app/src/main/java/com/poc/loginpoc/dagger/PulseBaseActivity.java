package com.poc.loginpoc.dagger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.poc.loginpoc.LoginActivity;
import com.poc.loginpoc.login.LoginPreference;
import com.poc.loginpoc.response.UserInfo;
import com.poc.loginpoc.util.AppPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.internal.Utils;

@SuppressLint("Registered")
public abstract class PulseBaseActivity extends AppCompatActivity {

    private static final String TAG = PulseBaseActivity.class.getName();

    @Inject
    AppPreferences _appPreferences;


    UserInfo _user;

    /**
     * Gets the screen name to submit to analytics.
     *
     * @return The screen name, or null if should not be tracked by default
     */
    protected abstract String getAnalyticsScreenName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((PulseApplication) getApplication()).inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        validateToken();
    }

    private void validateToken() {
        _user = _appPreferences.getCurrentUserInfo();
        if (_user == null) {//No logged in user
            return;
        }
        if (!isTokenValid(_user.getTokenExpiry())) {
            redirectToLogin();
        }
    }

    private void redirectToLogin() {
        _user.setLoginPreference(LoginPreference.Password);
        _appPreferences.addUser(_user);

        Toast.makeText(this, "Session expired, please login!", Toast.LENGTH_LONG).show();

        finishAffinity();
        LoginActivity.startActivity(this);
    }

    public static boolean isTokenValid(String tokenExpiry) {
        boolean valid = false;
         String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ";
        if (!TextUtils.isEmpty(tokenExpiry)) {
            SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
            try {
                Date expiryDate = sdf.parse(tokenExpiry);
                Date currentDate = new Date();
                sdf.format(currentDate);

                if (currentDate.before(expiryDate)) {
                    valid = true;
                }
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing token expiry date");
            }
        }
        return valid;
    }
}
