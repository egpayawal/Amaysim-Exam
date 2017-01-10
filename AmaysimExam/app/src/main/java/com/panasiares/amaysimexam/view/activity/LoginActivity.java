package com.panasiares.amaysimexam.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.panasiares.amaysimexam.R;
import com.panasiares.amaysimexam.main.LoginMain;
import com.panasiares.amaysimexam.model.LoginModel;
import com.panasiares.amaysimexam.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginMain.LoginRequiredViewOps {

    private LoginPresenter mPresenter;
    private EditText mTxtEmailAdd;
    private EditText mTxtPassword;
    private Button mBtnLogin;
    private ProgressDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupMVP();
        initViews();
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
    public void checkCredentials() {
        String username = mTxtEmailAdd.getText().toString();
        String password = mTxtPassword.getText().toString();

        if (!mPresenter.isValidCredentials(username, password)) {
            return;
        }

        mPresenter.loginUser(username, password);
    }

    @Override
    public void hideEmailErrorMessage() {
        if (mTxtEmailAdd != null) {
            mTxtEmailAdd.setError(null);
        }
    }

    @Override
    public void showEmailErrorMessage(String errorMessage) {
        if (mTxtEmailAdd != null) {
            mTxtEmailAdd.setError(errorMessage);
        }
    }

    @Override
    public void hidePasswordErrorMessage() {
        if (mTxtPassword != null) {
            mTxtPassword.setError(null);
        }
    }

    @Override
    public void showPasswordErrorMessage(String errorMessage) {
        if (mTxtPassword != null) {
            mTxtPassword.setError(errorMessage);
        }
    }

    @Override
    public void showProgress() {
        if (mLoading == null) {
            mLoading = new ProgressDialog(LoginActivity.this);
            mLoading.setMessage(getString(R.string.txt_dialog_message));
            mLoading.setCancelable(false);
            mLoading.show();
        } else {
            mLoading.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @Override
    public void displayNextPage() {
        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void displayErrorMessage() {
        Toast.makeText(getApplicationContext(),
                getText(R.string.txt_invalid_user), Toast.LENGTH_LONG).show();
    }

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        if (mPresenter == null) {
            // Create the Presenter
            LoginPresenter presenter = new LoginPresenter(this);
            // Create the Model
            LoginModel model = new LoginModel(presenter);
            // Set Presenter model
            presenter.setModel(model);
            // Set the Presenter as a interface
            mPresenter = presenter;
        } else {
            mPresenter.setView(this);
        }
    }

    private void initViews() {
        mTxtEmailAdd = (EditText) findViewById(R.id.txt_email_add);
        mTxtPassword = (EditText) findViewById(R.id.txt_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onLoginClicked();
            }
        });
    }
}
