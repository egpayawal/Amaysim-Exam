package com.panasiares.amaysimexam.presenter;

import android.content.Context;

import com.panasiares.amaysimexam.main.SplashMain;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class SplashPresenter implements SplashMain.SplashProvidedPresenterOps, SplashMain.SplashRequiredPresenterOps {

    private WeakReference<SplashMain.SplashRequiredViewOps> mView;
    private SplashMain.SplashProvidedModelOps mModel;

    public SplashPresenter(SplashMain.SplashRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    public void setModel(SplashMain.SplashProvidedModelOps model) {
        mModel = model;
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link SplashMain.SplashRequiredViewOps}
     * @throws NullPointerException when View is unavailable
     */
    private SplashMain.SplashRequiredViewOps getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /***********************************************************************************************
     * Override Methods
     ********************************************************************************************/

    @Override
    public void OnCreate() {
        if (mModel != null) {
            mModel.getUserData(getAppContext());
        }
    }

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
    public void setView(SplashMain.SplashRequiredViewOps view) {
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
    public void userInfoData(HashMap<String, String> data) {
        if (getView() != null) {
            getView().userInfoData(data);
        }
    }

    @Override
    public void serviceData(HashMap<String, String> data) {
        if (getView() != null) {
            getView().serviceData(data);
        }
    }

    @Override
    public void subscriptionData(HashMap<String, String> data) {
        if (getView() != null) {
            getView().subscriptionData(data);
        }
    }

    @Override
    public void productData(HashMap<String, String> data) {
        if (getView() != null) {
            getView().productData(data);
        }
    }

    @Override
    public void onFinished() {
        if (getView() != null) {
            getView().displayNextPage();
        }
    }
}
