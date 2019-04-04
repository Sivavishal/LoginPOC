package com.poc.loginpoc.dagger;

import android.app.Application;
import android.os.Environment;


import java.io.File;
import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class PulseApplication extends Application {

    private ObjectGraph graph;

    protected ObjectGraph _userObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();



        graph = ObjectGraph.create(getModules().toArray());

    }

    private static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] files = fileOrDirectory.listFiles();
            if (files != null && files.length > 0) {
                for (File child : fileOrDirectory.listFiles()) {
                    deleteRecursive(child);
                }
            }
        }

        fileOrDirectory.delete();
    }

    /**
     * Crashlytics is an automatic crash reporting solution from Fabric.
     * Crash will be automatically reported after relaunch of the app
     * Handled exceptions will be listed as non-fatal issues
     * Log messages will be listed only if there is a crash
     */


    protected List<Object> getModules() {
        return Arrays.asList(
                new PulseApplicationModule(this),
                new LoggedInModule()
        );
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
