package com.forateq.farmapp.authenticate;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.MainActivity;
import com.forateq.farmapp.R;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vallejos Family on 2/23/2016.
 */
public class AuthenticatorActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";
    private Bundle mResultBundle = null;
    private String authtoken;
    private AccountManager mAccountManager;
    private String mAuthTokenType;
    private String mAccountType;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private String notification_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        FarmApplication.DEVICE_ID = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        mAccountType = getIntent().getStringExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE);
        mAccountManager = AccountManager.get(getBaseContext());
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.e("debug", "User:" + userId);
                notification_id = userId;
                if (registrationId != null) {
                    Log.e("debug", "registrationId:" + registrationId);
                }
            }
        });
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null) {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
    }


    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }

    /**
     * Submits the username, password, deviceid as parameters to get the session_key from the API
     */
    public void submit(){
        if(isNetworkAvailable()){
            final String userName = usernameEditText.getText().toString();
            final String userPass = passwordEditText.getText().toString();
            new AsyncTask<String, Void, Intent>() {
                private ProgressDialog dialog;
                @Override
                protected Intent doInBackground(String... params) {
                    Bundle data = new Bundle();
                    try {
                        Log.e("Device Id", FarmApplication.DEVICE_ID);
                        authtoken =  new FarmAppServerAuthenticate().userSignIn(userName, userPass, FarmApplication.DEVICE_ID, FarmAppService.SERVER_TOKEN, notification_id);
                        if(authtoken.equals("failed")){
                            Toast.makeText(AuthenticatorActivity.this, "Username and password does not match", Toast.LENGTH_LONG).show();
                        }
                        else{
                            data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                            data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
                            data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                            data.putString(PARAM_USER_PASS, userPass);
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':'Login success!'}, 'include_player_ids': ['" + notification_id + "']}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                    }
                    Log.e("done acquiring", "");
                    final Intent res = new Intent();
                    res.putExtras(data);
                    Log.e("returning","");
                    return res;
                }

                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                        Toast.makeText(AuthenticatorActivity.this, "Username and password does not match", Toast.LENGTH_LONG).show();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } else {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Log.e("finishing", "");
                        finishLogin(intent);
                    }
                }
                @Override
                protected void onPreExecute(){
                    super.onPreExecute();
                    dialog = new ProgressDialog(AuthenticatorActivity.this);
                    dialog.setMessage("Logging in...");
                    dialog.setIndeterminate(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }.execute();
        }
        else{
            Toast.makeText(this, "Please connect to the internet to login", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:{
                submit();
                break;
            }
        }
    }

    /**
     * Closes the login activity after successful login
     * @param intent
     */
    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        this.startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Checks if there is a network available before login
     * @return
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
