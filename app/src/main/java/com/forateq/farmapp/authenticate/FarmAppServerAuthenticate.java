package com.forateq.farmapp.authenticate;

import android.util.Log;

import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.models.SessionKey;
import com.forateq.farmapp.objects.LoginWrapper;

import retrofit.RestAdapter;


/**
 * Created by Vallejos Family on 2/23/2016.
 */
public class FarmAppServerAuthenticate {

    public static String userSignIn(final String username, final String password, String deviceId, String token, String notification_id){
        token = tryLogin(username, password, deviceId, token, notification_id);
        return token;
    }



    private static String tryLogin(String mUsername, String mPassword, String deviceId, String token, String notification_id)
    {
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(FarmAppService.URL)
                .build();
        FarmAppService service = retrofit.create(FarmAppService.class);
        LoginWrapper loginWrapper = service.login(mUsername, mPassword, deviceId, token, notification_id);
        Log.e("Token", "Result"+loginWrapper.getLogin().isLogin_success());
        if(loginWrapper.getLogin().isLogin_success()){
            SessionKey sessionKey = new SessionKey();
            sessionKey.setUser(mUsername);
            sessionKey.setSession_key(loginWrapper.getKey().getSession_key());
            sessionKey.save();
            return loginWrapper.getKey().getSession_key();
        }
        else{
            return "failed";
        }
    }

}
