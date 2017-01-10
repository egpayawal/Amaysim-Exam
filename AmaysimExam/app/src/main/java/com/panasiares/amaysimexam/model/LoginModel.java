package com.panasiares.amaysimexam.model;

import android.os.AsyncTask;

import com.panasiares.amaysimexam.constants.AppConstants;
import com.panasiares.amaysimexam.main.LoginMain;

public class LoginModel implements LoginMain.LoginProvidedModelOps {

    private LoginMain.LoginRequiredPresenterOps mPresenter;

    public LoginModel(LoginMain.LoginRequiredPresenterOps presenter) {
        this.mPresenter = presenter;
    }

    /***********************************************************************************************
     * Override Methods
     * ********************************************************************************************/

    /**
     * Called by Presenter when View is destroyed
     *
     * @param isChangingConfiguration true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
        }
    }

    @Override
    public void getUserAuthentication(final String email, final String password) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                if (email.equals(AppConstants.USERNAME) &&
                        password.equals(AppConstants.PASSWORD)) {
                    return true;
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    mPresenter.onLoginSuccess();
                } else {
                    mPresenter.onLoginFailed();
                }
            }
        }.execute();
    }
}
