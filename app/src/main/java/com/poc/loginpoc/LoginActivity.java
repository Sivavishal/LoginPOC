package com.poc.loginpoc;

import android.animation.Animator;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poc.loginpoc.dagger.PulseApplicationModule;
import com.poc.loginpoc.dagger.PulseBaseActivity;
import com.poc.loginpoc.login.LoginManager;
import com.poc.loginpoc.login.LoginPreference;
import com.poc.loginpoc.login.LoginPresenter;
import com.poc.loginpoc.login.LoginView;
import com.poc.loginpoc.response.UserInfo;
import com.poc.loginpoc.service.ApiClient;
import com.poc.loginpoc.service.IServiceCall;
import com.poc.loginpoc.util.AppPreferences;
import com.poc.loginpoc.util.PulseCroutonStyle;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private Handler mHandler;

    @BindView(R.id.lyt_wholelayout)
RelativeLayout mWholeLayout;
    @BindView(R.id.lyt_login_wholelayout)
RelativeLayout mLoginWholeLayout;
    @BindView(R.id.lyt_signup_wholelayout)
RelativeLayout mSignUpWholeLayout;

    @BindView(R.id.anim_layout)
     LinearLayout mLytLoginAnim;
    @BindView(R.id.anim_signup_layout)
     LinearLayout mLytSignUpAnim;


    @BindView(R.id.login_navigation)
      TextView txtLoginNav;
    @BindView(R.id.signup_navigation)
    TextView txtSingUpNav;



    @BindView(R.id.login_header)
    TextView txtbtnLogin;
    @BindView(R.id.signup_header)
    TextView txtbtnSingUp;

    @BindView(R.id.edt_loginusername)
    EditText edtLoginUserName;
    @BindView(R.id.edt_loginpassword)
    EditText edtLoginPassword;

    @BindView(R.id.edt_signupusername)
    EditText edtSignUpUserName;
    @BindView(R.id.edt_signuppassword)
    EditText edtSignUpPassword;
    @BindView(R.id.edt_signupconpassword)
    EditText edtSignUpConPassword;

    ConnectivityManager _connectivityManager;

    Animation animate;
    Animation ltoranimate;
    Context mContext;

    @Inject
    LoginPresenter _presenter;

    @Inject
    Application _application;

    @Inject
    LoginManager _loginManager;

    @Inject
    AppPreferences _appPreferences;

    /*@Override
    protected String getAnalyticsScreenName() {
        return LoginActivity.class.getName();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mHandler = new Handler();
        mContext = LoginActivity.this;
        _connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

         animate = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.right_to_left_bounce);
         ltoranimate = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.left_to_right_bounce);

        IServiceCall apiService =
                ApiClient.getClient().create(IServiceCall.class);

        _loginManager = new LoginManager(apiService);
         _presenter = new LoginPresenter(_loginManager, _appPreferences);
        _presenter.attach(this);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSingUpNav.setVisibility(View.VISIBLE);
                mLytLoginAnim.startAnimation(animate);
            }
        }, 500);


        mLytLoginAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleScreen(true);
            }
        });

        mLytSignUpAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleScreen(false);
            }
        });


        edtLoginPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (isNetworkAvailable()) {
                    _presenter.onLogin();
                } else {
                    Crouton.makeText(this, getString(R.string.no_internet), PulseCroutonStyle.ALERT).show();

                }
                return true;
            }
            return false;
        });


        edtSignUpConPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (isNetworkAvailable()) {
                    // _presenter.onLogin();
                } else {
                    Crouton.makeText(this, getString(R.string.no_internet), PulseCroutonStyle.ALERT).show();

                }
                return true;
            }
            return false;
        });
    }


    @OnClick(R.id.login_header)
    protected void onLoginClick() {
        _presenter.onLogin();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    private void toggleScreen(boolean IsLogin) {

        if (IsLogin) {

            int x = mLytLoginAnim.getRight();
            int y = mLytLoginAnim.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(mWholeLayout.getWidth(), mWholeLayout.getHeight());



            Animator anim = ViewAnimationUtils.createCircularReveal(mSignUpWholeLayout, x, y, startRadius, endRadius);
            mSignUpWholeLayout.setVisibility(View.VISIBLE);
            mSignUpWholeLayout.bringToFront();
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {


                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtLoginNav.setVisibility(View.VISIBLE);
                            mLytSignUpAnim.startAnimation(ltoranimate);
                        }
                    }, 500);
                    txtSingUpNav.setVisibility(View.GONE);
                    mLoginWholeLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

        } else {



            int x = mLytSignUpAnim.getLeft();
            int y = mLytSignUpAnim.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(mWholeLayout.getWidth(), mWholeLayout.getHeight());



            Animator anim = ViewAnimationUtils.createCircularReveal(mLoginWholeLayout, x, y, startRadius, endRadius);

            mLoginWholeLayout.setVisibility(View.VISIBLE);
            mLoginWholeLayout.bringToFront();
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {


                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            txtSingUpNav.setVisibility(View.VISIBLE);
                            mLytLoginAnim.startAnimation(animate);
                        }
                    }, 500);
                    mSignUpWholeLayout.setVisibility(View.GONE);
                    txtLoginNav.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();


        }
    }

    boolean isNetworkAvailable() {

        NetworkInfo activeNetwork = _connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }



    @Override
    public void showCrouton(int stringId, Style style) {
        Crouton.makeText(this, getString(stringId), style).show();
    }

    @Override
    public void showProgress(boolean visible) {

    }

    @Override
    public CharSequence getUsername() {
        return edtLoginUserName.getText().toString().trim();
    }

    @Override
    public CharSequence getPassword() {
        return edtLoginPassword.getText().toString().trim();
    }

    @Override
    public void login() {
        Log.d("LoginActivity", "You are logged in!");
        Crouton.makeText(this, "Login Successfully..But Need Pin",  PulseCroutonStyle.ALERT).show();

       /* UserInfo user = _appPreferences.getCurrentUserInfo();
        if (TextUtils.isEmpty(user.getPin())) {
            Crouton.makeText(this, "Login Successfully..But Need Pin",  PulseCroutonStyle.ALERT).show();
        } else {
            if (user.getLoginPreference() == LoginPreference.Password) {
                user.setLoginPreference(LoginPreference.Passcode);
                _appPreferences.addUser(user);
            }
            Crouton.makeText(this, "Login Successfully..No Need Pin :)",  PulseCroutonStyle.ALERT).show();
        }*/

    }
}




