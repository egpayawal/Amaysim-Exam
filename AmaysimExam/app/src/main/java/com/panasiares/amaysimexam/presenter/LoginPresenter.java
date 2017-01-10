package com.panasiares.amaysimexam.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.panasiares.amaysimexam.constants.AppConstants;
import com.panasiares.amaysimexam.main.LoginMain;
import com.panasiares.amaysimexam.utils.Utils;

import java.lang.ref.WeakReference;

public class LoginPresenter implements LoginMain.LoginProvidedPresenterOps, LoginMain.LoginRequiredPresenterOps {

    private WeakReference<LoginMain.LoginRequiredViewOps> mView;
    private LoginMain.LoginProvidedModelOps mModel;

    public LoginPresenter(LoginMain.LoginRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    public void setModel(LoginMain.LoginProvidedModelOps model) {
        mModel = model;
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link LoginMain.LoginRequiredViewOps}
     * @throws NullPointerException when View is unavailable
     */
    private LoginMain.LoginRequiredViewOps getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /***********************************************************************************************
     * Override Methods
     ********************************************************************************************/
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if (!isChangingConfiguration) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null;
        }
    }

    /**
     * Called by View during the reconstruction events
     *
     * @param view Activity instance
     */
    @Override
    public void setView(LoginMain.LoginRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    /**
     * Retrieve Application Context
     *
     * @return Application context
     */
    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Retrieves Activity context
     *
     * @return Activity context
     */
    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void onLoginClicked() {
        if (getView() != null) {
            getView().checkCredentials();
        }
    }

    @Override
    public boolean isValidCredentials(String email, String password) {
        boolean isValidEmail = false;
        boolean isValidPassword = false;

        if (Utils.isEmailValid(email)) {
            getView().hideEmailErrorMessage();
            isValidEmail = true;
        } else {
            getView().showEmailErrorMessage(AppConstants.INVALID_EMAIL);
        }

        if (!TextUtils.isEmpty(password)) {
            getView().hidePasswordErrorMessage();
            isValidPassword = true;
        } else {
            getView().showPasswordErrorMessage(AppConstants.INVALID_PASSWORD);
        }

        return (isValidEmail && isValidPassword);
    }

    @Override
    public void loginUser(final String email, final String password) {
        getView().showProgress();
        if (mModel != null) {
            mModel.getUserAuthentication(email, password);
        }
    }

    @Override
    public void onLoginSuccess() {
        if (getView() != null) {
            getView().hideProgress();
            getView().displayNextPage();
        }
    }

    @Override
    public void onLoginFailed() {
        if (getView() != null) {
            getView().hideProgress();
            getView().displayErrorMessage();
        }
    }
}
