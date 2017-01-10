package com.panasiares.amaysimexam.main;

import android.content.Context;

public interface LoginMain {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      Presenter to View
     */
    interface LoginRequiredViewOps {
        Context getAppContext();

        Context getActivityContext();

        void checkCredentials();

        void hideEmailErrorMessage();

        void showEmailErrorMessage(String errorMessage);

        void hidePasswordErrorMessage();

        void showPasswordErrorMessage(String errorMessage);

        void showProgress();

        void hideProgress();

        void displayNextPage();

        void displayErrorMessage();
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface LoginProvidedPresenterOps {
        void onDestroy(boolean isChangingConfiguration);

        void setView(LoginRequiredViewOps view);

        void onLoginClicked();

        boolean isValidCredentials(String email, String password);

        void loginUser(String email, String password);
    }

    /**
     * Required Presenter methods available to Model.
     *      Model to Presenter
     */
    interface LoginRequiredPresenterOps {
        Context getAppContext();

        Context getActivityContext();

        void onLoginSuccess();

        void onLoginFailed();
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface LoginProvidedModelOps {
        void onDestroy(boolean isChangingConfiguration);

        void getUserAuthentication(final String email, final String password);
    }
}
