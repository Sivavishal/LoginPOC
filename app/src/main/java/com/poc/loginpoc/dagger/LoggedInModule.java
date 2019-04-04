package com.poc.loginpoc.dagger;

import android.app.Application;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.poc.loginpoc.LoginActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(
        injects = {
                LoginActivity.class,
        },
        addsTo = PulseApplicationModule.class,
        complete = false,
        library = true // We have unused providers, so must be set to true
)
public class LoggedInModule {

    private static final String BASE_URL = "https://www.default.com";//This will be overridden by dynamic value

    @Singleton
    @Provides
    @Named("gopulse")
    Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build();
    }



}
