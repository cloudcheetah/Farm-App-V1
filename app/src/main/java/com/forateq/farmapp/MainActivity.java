package com.forateq.farmapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.forateq.farmapp.adapters.ViewPagerAdapter;
import com.forateq.farmapp.authenticate.AccountGeneral;
import com.forateq.farmapp.fragments.ChatFragment;
import com.forateq.farmapp.fragments.ContactsFragment;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.fragments.HomeFragment;
import com.forateq.farmapp.fragments.ProfileFragment;
import com.forateq.farmapp.utils.CustomViewPager;
import com.forateq.farmapp.utils.SlidingTabLayout;
import com.onesignal.OneSignal;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static CustomViewPager pager;
    private ViewPagerAdapter adapter;
    public static SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Home", "Contacts", "Chat", "ERP", "Profile"};
    private int Numboftabs = 5;
    private HomeFragment homeFragment;
    private ContactsFragment contactsFragment;
    private ChatFragment chatFragment;
    private ERPFragment erpFragment;
    private ProfileFragment profileFragment;
    private AccountManager accountManager;
    private Account[] accounts;
    private Account account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init(){

        FarmApplication.DEVICE_ID = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("Device ID", ""+FarmApplication.DEVICE_ID);
        OneSignal.enableNotificationsWhenActive(true);
        homeFragment = new HomeFragment();
        contactsFragment = new ContactsFragment();
        chatFragment = new ChatFragment();
        erpFragment = new ERPFragment();
        profileFragment = new ProfileFragment();
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, contactsFragment, chatFragment, erpFragment, profileFragment, homeFragment);
        pager = (CustomViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorLightPrimary);
            }
        });
        tabs.setViewPager(pager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initAccounts();
    }

    public void initAccounts(){
        accountManager = AccountManager.get(this);
        accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
        if(accounts.length == 0){
            Log.e("Authenticating", "Authenticating");
            addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        }
    }

    private void addNewAccount(String accountType, String authTokenType) {
        AccountManager.get(this).addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                    finish();
                } catch (AuthenticatorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }
}
