package com.panasiares.amaysimexam.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.panasiares.amaysimexam.R;
import com.panasiares.amaysimexam.main.SplashMain;
import com.panasiares.amaysimexam.model.SplashModel;
import com.panasiares.amaysimexam.presenter.SplashPresenter;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity implements SplashMain.SplashRequiredViewOps {

    private SplashPresenter mPresenter;
    private HashMap<String, String> mUserInfoData;
    private HashMap<String, String> mServiceData;
    private HashMap<String, String> mSubscriptionData;
    private HashMap<String, String> mProductionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupMVP();
    }

    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public Context getActivityContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(true);
    }

    @Override
    public void userInfoData(HashMap<String, String> data) {
        mUserInfoData = data;
    }

    @Override
    public void serviceData(HashMap<String, String> data) {
        mServiceData = data;
    }

    @Override
    public void subscriptionData(HashMap<String, String> data) {
        mSubscriptionData = data;
    }

    @Override
    public void productData(HashMap<String, String> data) {
        mProductionData = data;
    }

    @Override
    public void displayNextPage() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("userInfo", mUserInfoData);
                intent.putExtra("services", mServiceData);
                intent.putExtra("subscription", mSubscriptionData);
                intent.putExtra("production", mProductionData);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        if (mPresenter == null) {
            // Create the Presenter
            SplashPresenter presenter = new SplashPresenter(this);
            // Create the Model
            SplashModel model = new SplashModel(presenter);
            // Set Presenter model
            presenter.setModel(model);
            // Set the Presenter as a interface
            mPresenter = presenter;
        } else {
            mPresenter.setView(this);
        }

        mPresenter.OnCreate();
    }
}
