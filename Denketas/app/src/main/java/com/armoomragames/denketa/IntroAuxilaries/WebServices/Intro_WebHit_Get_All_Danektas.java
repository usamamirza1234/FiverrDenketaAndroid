package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.content.Context;
import android.util.Log;


import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.DModel_PaginationInfo;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_All_Danektas {
    private AsyncHttpClient mClient = new AsyncHttpClient();
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();

    public void getCategory(Context context, final IWebCallback iWebCallback, String _cityId)  {
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.fetchDanetkas;
        RequestParams params = new RequestParams();
//        params.put("page",_index );
        params.put("per_page", 20);
        params.put("city_id", _cityId);
        params.put("sort_by", "sort_id");
        params.put("sort_order", "ASC");

        Log.d("LOG_AS", "getCategory: " + myUrl+params.toString());
//        if (AppConfig.getInstance().getGuestLogin()) {
//            mClient.addHeader(ApiMethod.HEADER.Authorization, AppConstt.guestAuth.guestAuth);
//        } else {
//            mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUserData.authToken);
//        }
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        Log.d("currentLang", AppConfig.getInstance().loadDefLanguage());
        mClient.get(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, "UTF-8");
                            responseObject = gson.fromJson(strResponse, ResponseModel.class);
                            switch (statusCode) {
                                case AppConstt.ServerStatus.OK:
                                    //Save user login data
//                                    AppConfig.getInstance().mUser.User_Id = responseObject.getData().getId();
//                                    AppConfig.getInstance().mUser.Name = responseObject.getData().getName();
//                                    AppConfig.getInstance().mUser.Email = responseObject.getData().getEmail();
//                                    AppConfig.getInstance().mUser.Phone = responseObject.getData().getPhone();
//                                    AppConfig.getInstance().mUser.Image = "";//responseObject.getData().getImage();
//                                    if (responseObject.getData().getType() == AppConstt.UserType.CUSTOMER) {
//                                        AppConfig.getInstance().mUser.Type = AppConstt.UserType.CUSTOMER;
//                                    } else {
//                                        AppConfig.getInstance().mUser.Type = AppConstt.UserType.DRIVER;
//                                    } // "";//responseObject.getData().getImage();
//                                    AppConfig.getInstance().mUser.isPushOn = true;//responseObject.getData().getProfileOnOff().equals("1");
//                                    AppConfig.getInstance().mUser.isLoggedIn = true;
//                                    AppConfig.getInstance().mUser.Authorization = responseObject.getData().getAuthorization();
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

                            case AppConstt.ServerStatus.UNAUTHORIZED:
                                AppConfig.getInstance().navtoLogin();
                                break;
                            case AppConstt.ServerStatus.FORBIDDEN:
                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                break;
                        }
                    }
                }

        );

    }

    public class ResponseModel {

        public class Listing
        {
            private int id;

            private String name;

            private String image;

            private boolean status;

            private int updatedTime;

            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setName(String name){
                this.name = name;
            }
            public String getName(){
                return this.name;
            }
            public void setImage(String image){
                this.image = image;
            }
            public String getImage(){
                return this.image;
            }
            public void setStatus(boolean status){
                this.status = status;
            }
            public boolean getStatus(){
                return this.status;
            }
            public void setUpdatedTime(int updatedTime){
                this.updatedTime = updatedTime;
            }
            public int getUpdatedTime(){
                return this.updatedTime;
            }
        }


        public class Pagination
        {
            private int page;

            private int count;

            private int pages;

            private String sortBy;

            private String sortOrder;

            public void setPage(int page){
                this.page = page;
            }
            public int getPage(){
                return this.page;
            }
            public void setCount(int count){
                this.count = count;
            }
            public int getCount(){
                return this.count;
            }
            public void setPages(int pages){
                this.pages = pages;
            }
            public int getPages(){
                return this.pages;
            }
            public void setSortBy(String sortBy){
                this.sortBy = sortBy;
            }
            public String getSortBy(){
                return this.sortBy;
            }
            public void setSortOrder(String sortOrder){
                this.sortOrder = sortOrder;
            }
            public String getSortOrder(){
                return this.sortOrder;
            }
        }


        public class Data
        {
            private List<Listing> listing;

            private Pagination pagination;

            public void setListing(List<Listing> listing){
                this.listing = listing;
            }
            public List<Listing> getListing(){
                return this.listing;
            }
            public void setPagination(Pagination pagination){
                this.pagination = pagination;
            }
            public Pagination getPagination(){
                return this.pagination;
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
