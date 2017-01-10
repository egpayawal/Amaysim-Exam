package com.panasiares.amaysimexam.main;

import android.content.Context;

import java.util.HashMap;

public interface HomeMain {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      Presenter to View`
     */
    interface HomeRequiredViewOps {
        Context getAppContext();

        Context getActivityContext();

        void getInformationData();

        void displayProfileName(String fullName);

        void displayEmailAdd(String email);

        void displayProfile();

        void displaySubscription();
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface HomeProvidedPresenterOps {
        void onDestroy(boolean isChangingConfiguration);

        void setView(HomeMain.HomeRequiredViewOps view);

        void OnCreate();

        void userInfoData(HashMap<String, String> data);

        void onHeaderContainerClicked();

        void navSubscription();
    }

    /**
     * Required Presenter methods available to Model.
     *      Model to Presenter
     */
    interface HomeRequiredPresenterOps {
        Context getAppContext();

        Context getActivityContext();
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface HomeProvidedModelOps {
        void onDestroy(boolean isChangingConfiguration);
    }
}
