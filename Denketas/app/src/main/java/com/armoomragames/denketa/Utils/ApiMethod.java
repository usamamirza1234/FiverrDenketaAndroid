package com.armoomragames.denketa.Utils;


public interface ApiMethod {

    String artistImage_Videos_baseURL= "https://art-station-bucket.s3.me-south-1.amazonaws.com/uploads_staging/";


    interface GET {

        String fetchFreeDanetkas="fetch/free/danetkas";
    }

    interface POST {
        String signUp = "add/user/email";
        String signIn = "login";
        String addProfile ="add/user/profile" ;
        String fetchUserDanetkas ="fetch/user/danetkas" ;
        String addDanetkas ="add/user/danetkas" ;
        String fetchDanetkas ="fetch/danetkas" ;

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

