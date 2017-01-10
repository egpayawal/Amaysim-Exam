package com.panasiares.amaysimexam.main;

import android.content.Context;

import java.util.HashMap;

public interface SplashMain {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      Presenter to View`
     */
    interface SplashRequiredViewOps {
        Context getAppContext();

        Context getActivityContext();

        void userInfoData(HashMap<String, String> data);

        void serviceData(HashMap<String, String> data);

        void subscriptionData(HashMap<String, String> data);

        void productData(HashMap<String, String> data);

        void displayNextPage();
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface SplashProvidedPresenterOps {
        void onDestroy(boolean isChangingConfiguration);

        void setView(SplashMain.SplashRequiredViewOps view);

        void OnCreate();
    }

    /**
     * Required Presenter methods available to Model.
     *      Model to Presenter
     */
    interface SplashRequiredPresenterOps {
        Context getAppContext();

        Context getActivityContext();

        void userInfoData(HashMap<String, String> data);

        void serviceData(HashMap<String, String> data);

        void subscriptionData(HashMap<String, String> data);

        void productData(HashMap<String, String> data);

        void onFinished();
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface SplashProvidedModelOps {
        void onDestroy(boolean isChangingConfiguration);

        void getUserData(Context context);
    }
}
