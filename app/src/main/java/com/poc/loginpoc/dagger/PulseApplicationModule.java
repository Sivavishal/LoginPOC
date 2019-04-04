package com.poc.loginpoc.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.poc.loginpoc.service.IServiceCall;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link Application} to create.
 */
@Module(library = true)
public class PulseApplicationModule {

    private static final String PULSE_BASE_URL = "https://support2pulseapi.cbre.com.au/api/";

    public static final HttpLoggingInterceptor.Level OKHTTP_DEBUG_LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY;

    private final Application _application;

    public PulseApplicationModule(Application context) {
        _application = context;
    }

    @Singleton
    @Provides
    @ApplicationContext
    public Context provideApplicationContext() {
        return _application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return _application;
    }

    @Singleton
    @Provides
    @ApplicationContext
    public Resources provideApplicationResources() {
        return _application.getResources();
    }

    @Provides
    public AssetManager provideAssets() {
        return _application.getAssets();
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(_application);
    }

    @Singleton
    @Provides
    public ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) _application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder
                builder =
                new OkHttpClient().newBuilder()
                                  .readTimeout(10, TimeUnit.SECONDS)
                                  .connectTimeout(5, TimeUnit.SECONDS);
        if (true) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(OKHTTP_DEBUG_LOGGING_LEVEL);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(PULSE_BASE_URL)
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    IServiceCall provideLoginService(Retrofit retrofit) {
        return retrofit.create(IServiceCall.class);
    }



    @Singleton
    @Provides
    @Named("regular")
    Typeface provideFontRegular() {
        return Typeface.createFromAsset(_application.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Singleton
    @Provides
    @Named("medium")
    Typeface provideFontMedium() {
        return Typeface.createFromAsset(_application.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    @Singleton
    @Provides
    @Named("bold")
    Typeface provideFontBold() {
        return Typeface.createFromAsset(_application.getAssets(), "fonts/Roboto-Bold.ttf");
    }
}
