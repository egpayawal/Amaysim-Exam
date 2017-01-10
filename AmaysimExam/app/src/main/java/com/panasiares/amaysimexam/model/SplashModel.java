package com.panasiares.amaysimexam.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.panasiares.amaysimexam.constants.AppConstants;
import com.panasiares.amaysimexam.main.HomeMain;
import com.panasiares.amaysimexam.main.SplashMain;
import com.panasiares.amaysimexam.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SplashModel implements SplashMain.SplashProvidedModelOps {

    private SplashMain.SplashRequiredPresenterOps mPresenter;

    public SplashModel(SplashMain.SplashRequiredPresenterOps presenter) {
        this.mPresenter = presenter;
    }

    private class JsonParserAsyncTask extends AsyncTask<String, String, JSONObject> {

        private Context mContext;

        public JsonParserAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JsonParser jsonParser = new JsonParser();

            // Getting Json from Asset
            JSONObject jsonObject = jsonParser.getJSONFromAsset(mContext, AppConstants.JSON_FILENAME);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                // Account data
                JSONObject jsonData = jsonObject.getJSONObject("data");
                JSONObject jsonUserAttr = jsonData.getJSONObject("attributes");
                mPresenter.userInfoData(addToHashMap(jsonUserAttr));

                /*String paymentType = jsonUserAttr.getString("payment-type");
                String title = jsonUserAttr.getString("title");
                String firstName = jsonUserAttr.getString("first-name");
                String lastName = jsonUserAttr.getString("last-name");
                String dateOfBirth = jsonUserAttr.getString("date-of-birth");
                String contactNumber = jsonUserAttr.getString("contact-number");
                String emailAddress = jsonUserAttr.getString("email-address");

                HashMap<String, String> account = new HashMap<>();
                account.put("paymentType", paymentType);
                account.put("title", title);
                account.put("firstName", firstName);
                account.put("lastName", lastName);
                account.put("dateOfBirth", dateOfBirth);
                account.put("contactNumber", contactNumber);
                account.put("emailAddress", emailAddress);
                mPresenter.userInfoData(account);*/
                // Included data
                JSONArray jsonIncluded = jsonObject.getJSONArray("included");
                // Services
                JSONObject jsonServices = jsonIncluded.getJSONObject(0);
                JSONObject jsonServicesAttr = jsonServices.getJSONObject("attributes");
                mPresenter.serviceData(addToHashMap(jsonServicesAttr));
                // Subscriptions
                JSONObject jsonSubscriptions = jsonIncluded.getJSONObject(1);
                JSONObject jsonSubsAttr = jsonSubscriptions.getJSONObject("attributes");
                mPresenter.subscriptionData(addToHashMap(jsonSubsAttr));
                // Products
                JSONObject jsonProducts = jsonIncluded.getJSONObject(2);
                JSONObject jsonProdAttr = jsonProducts.getJSONObject("attributes");
                mPresenter.productData(addToHashMap(jsonProdAttr));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mPresenter.onFinished();
        }
    }

    private static HashMap<String, String> addToHashMap(JSONObject data) {
        if (data != null) {
            HashMap<String, String> tempMap = new HashMap<>();
            Iterator<String> it = data.keys();
            while (it.hasNext()) {
                String key = it.next();
                try {
                    tempMap.put(key, data.getString(key));
                } catch (Throwable e) {
                    try {
                        Log.e("DEBUG", key + ":" + data.getString(key));
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            return tempMap;
        }
        return null;
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
    public void getUserData(Context context) {
        JsonParserAsyncTask jsonParserAsyncTask = new JsonParserAsyncTask(context);
        jsonParserAsyncTask.execute();
    }
}
