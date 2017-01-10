package com.panasiares.amaysimexam.presenter;

import android.content.Context;
import android.util.Log;

import com.panasiares.amaysimexam.main.HomeMain;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class HomePresenter implements HomeMain.HomeProvidedPresenterOps, HomeMain.HomeRequiredPresenterOps {

    private WeakReference<HomeMain.HomeRequiredViewOps> mView;
    private HomeMain.HomeProvidedModelOps mModel;

    public HomePresenter(HomeMain.HomeRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    public void setModel(HomeMain.HomeProvidedModelOps model) {
        mModel = model;
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link HomeMain.HomeRequiredViewOps}
     * @throws NullPointerException when View is unavailable
     */
    private HomeMain.HomeRequiredViewOps getView() throws NullPointerException {
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
        if (getView() != null) {
            getView().getInformationData();
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
    public void setView(HomeMain.HomeRequiredViewOps view) {
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
        if (data != null && getView() != null) {
            String title = data.get("title");
            String firstName = data.get("first-name");
            String lastName = data.get("last-name");
            String emailAddress = data.get("email-address");
            StringBuilder sb = new StringBuilder();
            sb.append(title).append(".");
            sb.append(firstName).append(" ");
            sb.append(lastName);
            String fullName = sb.toString();
            getView().displayProfileName(fullName);
            getView().displayEmailAdd(emailAddress);
        }
    }

    @Override
    public void onHeaderContainerClicked() {
        if (getView() != null) {
            getView().displayProfile();
        }
    }

    @Override
    public void navSubscription() {
        if (getView() != null) {
            getView().displaySubscription();
        }
    }
}
