package com.forateq.farmapp;

import android.app.Application;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.onesignal.OneSignal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Vallejos Family on 2/24/2016.
 * This class is use to initialize the sqlite db and the push notification service
 */
public class FarmApplication extends Application {

    public static Deque<View> viewDeque = new ArrayDeque<>();
    public static String SESSION_KEY = "";
    public static String DEVICE_ID = "";

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        OneSignal.startInit(this).init();
    }
}
