package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class Intro_WebHit_Post_Add_User_danekta {

    public static ResponseModel responseObject = null;
    public Context mContext;
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void postAddUserDanekta(Context context, final IWebCallback iWebCallback,
                                  final String _signInEntity) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.addDanetkas;
        Log.d("LOG_AS", "postAddUserDanekta: " + myUrl + _signInEntity);
        StringEntity entity = null;
        entity = new StringEntity(_signInEntity, "UTF-8");
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        Log.d("currentLang", AppConfig.getInstance().loadDefLanguage());

        mClient.post(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);

                            responseObject = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:
                                    //Save user login data
//                                    AppConfig.getInstance().mUserData.id = responseObject.getData().getUser().getId();
//                                    AppConfig.getInstance().mUserData.name = responseObject.getData().getUser().getName();
//                                    AppConfig.getInstance().mUserData.email = responseObject.getData().getUser().getEmail();
//                                    AppConfig.getInstance().mUserData.phone = responseObject.getData().getUser().getPhone();
//                                    AppConfig.getInstance().mUserData.mSelectedCity = responseObject.getData().getUser().getCity();
//                                    AppConfig.getInstance().mUserData.residency_city = responseObject.getData().getUser().getCity();
//                                    AppConfig.getInstance().mUserData.DOB = responseObject.getData().getUser().getDOB();
//                                    AppConfig.getInstance().mUserData.device_token = responseObject.getData().getUser().getDevice_token();
//                                    AppConfig.getInstance().mUserData.authToken = responseObject.getData().getAuth_token();
//                                    AppConfig.getInstance().mUserData.isPushAllowed = responseObject.getData().getUser().isAllowPush();
//                                    AppConfig.getInstance().mUserData.isLoggedIn = true;
//                                    AppConfig.getInstance().saveUserProfileData();

//                                    Log.d("LOG_AS", "DOB " + responseObject.getData().getUser().getDOB());
//                                    AppConfig.getInstance().mUser.City = responseObject.getData().getUser().getCity();
//                                    if (responseObject.getData().getType() == AppConstt.UserType.CUSTOMER) {
//                                        AppConfig.getInstance().mUser.Type = AppConstt.UserType.CUSTOMER;
//                                    } else {
//                                        AppConfig.getInstance().mUser.Type = AppConstt.UserType.DRIVER;
//                                    } // "";//responseObject.getData().getImage();
//                                    AppConfig.getInstance().mUser.isPushOn = true;//responseObject.getData().getProfileOnOff().equals("1");
//                                    AppConfig.getInstance().mUser.isLoggedIn = true;
//                                    AppConfig.getInstance().mUser.Authorization = responseObject.getData().getAuth_token();
//
//                                    AppConfig.getInstance().saveUserProfile();

                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                    break;
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            iWebCallback.onWebException(ex);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.FORBIDDEN:
                            case AppConstt.ServerStatus.UNAUTHORIZED:
                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                break;
                        }
                    }
                }

        );
    }


    class ResponseModel {


        public class Data
        {
            private boolean status;

            private int id;

            private int userId;

            private String danetkasId;

            private int updatedTime;

            public void setStatus(boolean status){
                this.status = status;
            }
            public boolean getStatus(){
                return this.status;
            }
            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setUserId(int userId){
                this.userId = userId;
            }
            public int getUserId(){
                return this.userId;
            }
            public void setDanetkasId(String danetkasId){
                this.danetkasId = danetkasId;
            }
            public String getDanetkasId(){
                return this.danetkasId;
            }
            public void setUpdatedTime(int updatedTime){
                this.updatedTime = updatedTime;
            }
            public int getUpdatedTime(){
                return this.updatedTime;
            }
        }


            private int code;

            private String status;

            private String message;

            private Data data;

            public void setCode(int code){
                this.code = code;
            }
            public int getCode(){
                return this.code;
            }
            public void setStatus(String status){
                this.status = status;
            }
            public String getStatus(){
                return this.status;
            }
            public void setMessage(String message){
                this.message = message;
            }
            public String getMessage(){
                return this.message;
            }
            public void setData(Data data){
                this.data = data;
            }
            public Data getData(){
                return this.data;
            }

    }

}
