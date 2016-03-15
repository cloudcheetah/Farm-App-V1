package com.forateq.farmapp.authenticate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Vallejos Family on 2/23/2016.
 */
public class FarmAppAuthenticateService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        FarmAppAuthenticator authenticator = new FarmAppAuthenticator(this);
        return authenticator.getIBinder();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("First", "FirstService destroyed");
    }

}
