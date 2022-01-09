package com.armoomragames.denketa.Utils;


public interface ApiMethod {

    String artistImage_Videos_baseURL= "https://art-station-bucket.s3.me-south-1.amazonaws.com/uploads_staging/";


    interface GET {
        String fetchUserDanetkas ="fetch/user/danetkas" ;
        String fetchFreeDanetkas="fetch/free/danetkas";
        String fetchInvestagatorDanetkas="fetch/all/danetkas";
        String fetchResults="find/result/";
        String fetchGameCredits="fetch/user/credit";
    }

    interface POST {
        String signUp = "add/user/email";
        String signIn = "login";
        String addProfile ="add/user/profile" ;
        String addUserCredits ="add/user/credit" ;
        String addDanetkas ="add/user/danetkas" ;
        String addCustomDanetkas ="add/master/danetkas" ;
        String fetchDanetkas ="fetch/danetkas" ;
        String fetchAllDanetkas ="fetch/all/danetkas" ;
        String contactUs ="contactUs" ;
        String verifyAndUpdate ="verifyAndUpdate" ;
        String forgotPassword ="forgotPassword" ;
        String addResults ="add/result" ;

    }


    interface Patch {
        String forgotPassword = "forgot";
        String verifyCode = "verifyCode";
        String resetPassword = "resetPassword";
    }

    interface HEADER {
        String Authorization = "Authorization";
        String Authorization_TOKEN = "token";
        String Lang = "lang";
        String Default_Auth = "mankoosha!and$";
    }

}

