package com.poc.loginpoc.dagger;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for activity related objects.
 */
@Module(library = true)
public class PulseActivityModule {
    private final Activity _activity;

    public PulseActivityModule(Activity activity) {
        _activity = activity;
    }

    @Singleton
    @Provides
    @ActivityContext
    public
    Context provideActivityContext() {
        return _activity;
    }

    @Singleton
    @Provides
    public Activity provideActivity() {
        return _activity;
    }

    @Singleton
    @Provides
    @ActivityContext
    public
    Resources provideActivityResources() {
        return _activity.getResources();
    }

    @Singleton
    @Provides
    public
    Configuration provideConfiguration() {
        return _activity.getResources().getConfiguration();
    }
}

