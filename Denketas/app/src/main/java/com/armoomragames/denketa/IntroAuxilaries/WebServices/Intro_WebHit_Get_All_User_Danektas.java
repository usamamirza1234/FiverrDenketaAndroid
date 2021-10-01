package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.content.Context;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.DModel_PaginationInfo;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_All_User_Danektas {
    private AsyncHttpClient mClient = new AsyncHttpClient();
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();

    public void getCategory(final IWebPaginationCallback iWebPaginationCallback, final int _index)  {
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.fetchUserDanetkas;

        RequestParams params = new RequestParams();

        params.put("page", _index);
        params.put("per_page", "10");


        Log.d("LOG_AS", "getOutletListing: " + myUrl + params);

//        mClient.addHeader(ApiMethod.HEADER.Lang, AppConfig.getInstance().getCurrentLang());
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, "UTF-8");
                            Log.d("LOG_AS", "onSuccess: " + strResponse);
                            ResponseModel responseObjectLocal = null;

                            responseObjectLocal = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.CREATED:
                                case AppConstt.ServerStatus.OK:
                                    if (_index == mPaginationInfo.currIndex) {
                                        //First page
                                        responseObject = responseObjectLocal;

                                        mPaginationInfo.isCompleted = false;

                                        iWebPaginationCallback.onWebInitialResult(true, responseObject.getMessage());
                                    } else {
//                                    //Subsequent pages
                                        boolean tmpIsDataFetched = (statusCode == AppConstt.ServerStatus.OK);
                                        if (tmpIsDataFetched) {
//                                            for (int i = 0; i < responseObjectLocal.getData().size(); i++)
//                                                responseObject.getData().add(responseObjectLocal.getData().get(i));
                                            responseObject = responseObjectLocal;
                                            mPaginationInfo.currIndex = _index;
                                        }

                                        //No need to save

                                        if (mPaginationInfo != null) {
                                            iWebPaginationCallback.onWebSuccessiveResult(true, !tmpIsDataFetched, responseObjectLocal.getMessage());
                                        }
                                    }
                                    break;

                                default:
                                    //Server error
                                    if (_index == mPaginationInfo.currIndex)
                                        iWebPaginationCallback.onWebInitialResult(false, responseObjectLocal.getMessage());
                                    else
                                        iWebPaginationCallback.onWebSuccessiveResult(false, false, responseObjectLocal.getMessage());
                                    break;
                            }
                        } catch (Exception ex) {
                            if (_index == mPaginationInfo.currIndex)
                                iWebPaginationCallback.onWebInitialException(ex);
                            else
                                iWebPaginationCallback.onWebSuccessiveException(ex);
                            Log.d("LOG_AS", "all medicines exception: " + ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {

                        Log.d("LOG_AS", "onFailure called: " + error.toString() + "   " + statusCode + "");


                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                if (_index == mPaginationInfo.currIndex)
                                    iWebPaginationCallback.onWebInitialResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                else
                                    iWebPaginationCallback.onWebSuccessiveResult(false, false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.UNAUTHORIZED:
                                AppConfig.getInstance().navtoLogin();
                                break;

                            case AppConstt.ServerStatus.DATABASE_NOT_FOUND:
                                if (_index == mPaginationInfo.currIndex) {
                                    //Save orders data
//                                    AppConfig.getInstance().saveHomeOrders("");
                                }
                                AppConfig.getInstance().parsErrorMessage(iWebPaginationCallback, responseBody, _index, mPaginationInfo.currIndex);
                                break;

                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebPaginationCallback, responseBody, _index, mPaginationInfo.currIndex);
                                break;
                        }
                    }
                }
        );
    }


    public class ResponseModel {

        public class Danetkas
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

        public class User
        {
            private int id;

            private String name;

            private String userName;

            private String dateOfBirth;

            private String gender;

            private String nationality;

            private String language;

            private String email;

            private String accessToken;

            private String userType;

            private boolean isProfileSet;

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
            public void setUserName(String userName){
                this.userName = userName;
            }
            public String getUserName(){
                return this.userName;
            }
            public void setDateOfBirth(String dateOfBirth){
                this.dateOfBirth = dateOfBirth;
            }
            public String getDateOfBirth(){
                return this.dateOfBirth;
            }
            public void setGender(String gender){
                this.gender = gender;
            }
            public String getGender(){
                return this.gender;
            }
            public void setNationality(String nationality){
                this.nationality = nationality;
            }
            public String getNationality(){
                return this.nationality;
            }
            public void setLanguage(String language){
                this.language = language;
            }
            public String getLanguage(){
                return this.language;
            }
            public void setEmail(String email){
                this.email = email;
            }
            public String getEmail(){
                return this.email;
            }
            public void setAccessToken(String accessToken){
                this.accessToken = accessToken;
            }
            public String getAccessToken(){
                return this.accessToken;
            }
            public void setUserType(String userType){
                this.userType = userType;
            }
            public String getUserType(){
                return this.userType;
            }
            public void setIsProfileSet(boolean isProfileSet){
                this.isProfileSet = isProfileSet;
            }
            public boolean getIsProfileSet(){
                return this.isProfileSet;
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


        public class Listing
        {
            private int id;

            private int userId;

            private int danetkasId;

            private boolean status;

            private int updatedTime;

            private Danetkas danetkas;

            private User user;

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
            public void setDanetkasId(int danetkasId){
                this.danetkasId = danetkasId;
            }
            public int getDanetkasId(){
                return this.danetkasId;
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
            public void setDanetkas(Danetkas danetkas){
                this.danetkas = danetkas;
            }
            public Danetkas getDanetkas(){
                return this.danetkas;
            }
            public void setUser(User user){
                this.user = user;
            }
            public User getUser(){
                return this.user;
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
