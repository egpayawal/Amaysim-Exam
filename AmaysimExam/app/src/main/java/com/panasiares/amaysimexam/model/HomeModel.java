package com.panasiares.amaysimexam.model;

import com.panasiares.amaysimexam.main.HomeMain;

public class HomeModel implements HomeMain.HomeProvidedModelOps {

    private HomeMain.HomeRequiredPresenterOps mPresenter;

    public HomeModel(HomeMain.HomeRequiredPresenterOps presenter) {
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
}
