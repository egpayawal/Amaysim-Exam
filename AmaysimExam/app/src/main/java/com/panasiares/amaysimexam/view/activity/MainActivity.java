package com.panasiares.amaysimexam.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panasiares.amaysimexam.R;
import com.panasiares.amaysimexam.main.HomeMain;
import com.panasiares.amaysimexam.model.HomeModel;
import com.panasiares.amaysimexam.presenter.HomePresenter;
import com.panasiares.amaysimexam.view.fragment.InformationFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeMain.HomeRequiredViewOps {

    private HomePresenter mPresenter;
    private HashMap<String, String> mUserInfoData;
    private HashMap<String, String> mServiceData;
    private HashMap<String, String> mSubscriptionData;
    private HashMap<String, String> mProductionData;

    private TextView mTxtName;
    private TextView mTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMVP();
        initViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        return true;
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
    public void getInformationData() {
        Intent intent = getIntent();
        mUserInfoData = (HashMap<String, String>)intent.getSerializableExtra("userInfo");
        mServiceData = (HashMap<String, String>)intent.getSerializableExtra("services");
        mSubscriptionData = (HashMap<String, String>)intent.getSerializableExtra("subscription");
        mProductionData = (HashMap<String, String>)intent.getSerializableExtra("production");
    }

    @Override
    public void displayProfileName(String fullName) {
        if (mTxtName != null) {
            mTxtName.setText(fullName);
        }
    }

    @Override
    public void displayEmailAdd(String email) {
        if (mTxtEmail != null) {
            mTxtEmail.setText(email);
        }
    }

    @Override
    public void displayProfile() {
        displaySelectedScreen(R.id.header_container);
    }

    @Override
    public void displaySubscription() {
        displaySelectedScreen(R.id.nav_subscription);
    }

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        if (mPresenter == null) {
            // Create the Presenter
            HomePresenter presenter = new HomePresenter(this);
            // Create the Model
            HomeModel model = new HomeModel(presenter);
            // Set Presenter model
            presenter.setModel(model);
            // Set the Presenter as a interface
            mPresenter = presenter;
        } else {
            mPresenter.setView(this);
        }

        mPresenter.OnCreate();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display subscription when the activity is loaded
        mPresenter.navSubscription();

        LinearLayout headerContainer = (LinearLayout) headerLayout.findViewById(R.id.header_container);
        headerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onHeaderContainerClicked();
            }
        });
        mTxtName = (TextView) headerLayout.findViewById(R.id.txt_name);
        mTxtEmail = (TextView) headerLayout.findViewById(R.id.txt_email_add);

        mPresenter.userInfoData(mUserInfoData);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        String title = "";
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_services:
                fragment = InformationFragment.newInstance(mServiceData);
                title = getString(R.string.txt_services);
                break;
            case R.id.nav_subscription:
                fragment = InformationFragment.newInstance(mSubscriptionData);
                title = getString(R.string.txt_subscription);
                break;
            case R.id.nav_products:
                fragment = InformationFragment.newInstance(mProductionData);
                title = getString(R.string.txt_products);
                break;

            case R.id.header_container:
                fragment = InformationFragment.newInstance(mUserInfoData);
                title = getString(R.string.txt_profile);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
