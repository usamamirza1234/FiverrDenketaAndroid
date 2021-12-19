package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.DModelCustomDanetka;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class Intro_WebHit_Post_AddUserCustomsDanetkas {

    public static RModel_SignIn responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postCustomDanetka(Context context, final IWebCallback iWebCallback,
                                  DModelCustomDanetka dModelCustomDanetka) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.addCustomDanetkas ;

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());



        RequestParams params = new RequestParams();
        try {
            params.put("image", dModelCustomDanetka.getImage(), "image/jpeg");
            params.put("answerImage", dModelCustomDanetka.getAnswerImage(), "image/jpeg");
            params.put("title", dModelCustomDanetka.getTitle());
            params.put("answer", dModelCustomDanetka.getAnswer());
            params.put("hint", dModelCustomDanetka.getHint());
            params.put("question", dModelCustomDanetka.getQuestion());
            params.put("learnMore", dModelCustomDanetka.getLearnMore());
            params.put("masterId", dModelCustomDanetka.getMasterId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Log.d("LOG_AS", "postCustomDanetka: " + myUrl +params);

        mClient.post(myUrl, params,  new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;



                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postCustomDanetka: strResponse" + strResponse);
                            responseObject = gson.fromJson(strResponse, RModel_SignIn.class);

                            switch (statusCode)
                            {

                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
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


    public class RModel_SignIn {

        public class Data
        {
            private String masterId;

            private boolean status;

            private int id;

            private String title;

            private String hint;

            private String answer;

            private String question;

            private String image;

            private String answerImage;

            private String learnMore;

            private int updatedTime;

            public void setMasterId(String masterId){
                this.masterId = masterId;
            }
            public String getMasterId(){
                return this.masterId;
            }
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
            public void setTitle(String title){
                this.title = title;
            }
            public String getTitle(){
                return this.title;
            }
            public void setHint(String hint){
                this.hint = hint;
            }
            public String getHint(){
                return this.hint;
            }
            public void setAnswer(String answer){
                this.answer = answer;
            }
            public String getAnswer(){
                return this.answer;
            }
            public void setQuestion(String question){
                this.question = question;
            }
            public String getQuestion(){
                return this.question;
            }
            public void setImage(String image){
                this.image = image;
            }
            public String getImage(){
                return this.image;
            }
            public void setAnswerImage(String answerImage){
                this.answerImage = answerImage;
            }
            public String getAnswerImage(){
                return this.answerImage;
            }
            public void setLearnMore(String learnMore){
                this.learnMore = learnMore;
            }
            public String getLearnMore(){
                return this.learnMore;
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
