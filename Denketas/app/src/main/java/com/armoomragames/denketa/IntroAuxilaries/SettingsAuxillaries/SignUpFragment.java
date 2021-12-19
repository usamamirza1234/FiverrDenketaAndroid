package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_LogIn;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_SignUp;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.RModel_Paypal;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    RelativeLayout rlToolbar, rlBack, rlCross;
    GoogleSignInClient mGoogleSignInClient;
    TextView txvLogin;
    RelativeLayout rlRegister;
    LinearLayout llGoogle, llFB;
    GoogleSignInAccount acct, account;
    IBadgeUpdateListener mBadgeUpdateListener;
    TextView txvGuest;
    private static final String EMAIL = "email";

    CallbackManager callbackManager;
    EditText edtPassword, edtEmail, editConfirmPass;
    private Dialog progressDialog;


    private final String TAG = "LoginActivity";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init();
        googleInit();
        bindViews(frg);


        return frg;
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);
        }

    }

    void init() {


        setToolbar();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
//        Animation shake;
//        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
//        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
//        txvPlay.startAnimation(shake); // starts animation

        llFB = frg.findViewById(R.id.frg_my_account_llFB);
        llGoogle = frg.findViewById(R.id.frg_my_account_llGoogle);
        txvLogin = frg.findViewById(R.id.frg_my_account_txvLogin);
        rlRegister = frg.findViewById(R.id.frg_my_account_rlRegister);


        editConfirmPass = frg.findViewById(R.id.frg_signup_edtCnfirmPassword);
        edtEmail = frg.findViewById(R.id.frg_signup_edtEmail);
        edtPassword = frg.findViewById(R.id.frg_signup_edtPassword);
        txvGuest = frg.findViewById(R.id.frg_signup_txv_guest);

        txvGuest.setOnClickListener(this);
        llFB.setOnClickListener(this);
        llGoogle.setOnClickListener(this);
        txvLogin.setOnClickListener(this);
        rlRegister.setOnClickListener(this);
        editTextWatchers();
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    private void requestUserRegister(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_SignUp intro_webHit_post_signUp = new Intro_WebHit_Post_SignUp();
        intro_webHit_post_signUp.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    //Save user login data
                    AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getId();
                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail();

                    AppConfig.getInstance().mUser.setGuest(false);
                    AppConfig.getInstance().mUser.setLoggedIn(true);
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getAccessToken();
                    AppConfig.getInstance().mUser.GameCredits = 0+"";
                    AppConfig.getInstance().saveUserProfile();


//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 1);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 2);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 3);
//                    requestAddUserDanetkas(jsonObject.toString());

                    if (!Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getIsProfileSet())
                        navtoSignUpContFragment();
                    else
                        ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }


    private void requestUserRegisterSocail(String _signUpEntity, String name) {
        showProgDialog();
        Intro_WebHit_Post_SignUp intro_webHit_post_signUp = new Intro_WebHit_Post_SignUp();
        intro_webHit_post_signUp.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    //Save user login data
                    AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getId();
                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail();
                    AppConfig.getInstance().mUser.Name = name;

                    AppConfig.getInstance().mUser.setGuest(false);
                    AppConfig.getInstance().mUser.setLoggedIn(true);
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getAccessToken();

                    AppConfig.getInstance().saveUserProfile();


//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 1);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 2);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 3);
//                    requestAddUserDanetkas(jsonObject.toString());

                    if (!Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getIsProfileSet())
                        navtoSignUpContFragment();
                    else
                        ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                } else {
                    dismissProgDialog();

                    requestUserSiginSocial(_signUpEntity, name);
//                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }

    private void requestUserSiginSocial(String _signUpEntity, String name) {
        showProgDialog();
        Intro_WebHit_Post_LogIn intro_webHit_post_logIn = new Intro_WebHit_Post_LogIn();
        intro_webHit_post_logIn.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();


                    //Save user login data
                    AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_LogIn.responseObject.getData().getId();
                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_LogIn.responseObject.getData().getEmail();


                    if (Intro_WebHit_Post_LogIn.responseObject.getData().getName() != null)
                        AppConfig.getInstance().mUser.Name = Intro_WebHit_Post_LogIn.responseObject.getData().getName();
                    if (Intro_WebHit_Post_LogIn.responseObject.getData().getNationality() != null)
                        AppConfig.getInstance().mUser.Nationality = Intro_WebHit_Post_LogIn.responseObject.getData().getNationality();

                    if (Intro_WebHit_Post_LogIn.responseObject.getData().getGender() != null)
                        AppConfig.getInstance().mUser.Gender = Intro_WebHit_Post_LogIn.responseObject.getData().getGender();

                    if (Intro_WebHit_Post_LogIn.responseObject.getData().getDateOfBirth() != null)
                        AppConfig.getInstance().mUser.DOB = Intro_WebHit_Post_LogIn.responseObject.getData().getDateOfBirth();

                    AppConfig.getInstance().mUser.Name = name;
                    AppConfig.getInstance().mUser.setGuest(false);
                    AppConfig.getInstance().mUser.setLoggedIn(true);
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_LogIn.responseObject.getData().getAccessToken();


//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 1);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 2);
//                    requestAddUserDanetkas(jsonObject.toString());
//                    jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", 3);
//                    requestAddUserDanetkas(jsonObject.toString());

                    AppConfig.getInstance().saveUserProfile();
                    if (!Intro_WebHit_Post_LogIn.responseObject.getData().getIsProfileSet())
                        navtoSignUpContFragment();
                    else
                        ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                } else {

                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Log.d("LOG_AS", "postSignIn: Exception" + ex.getMessage());
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.frg_my_account_rlRegister:
                checkErrorConditions();

//                navtoSignUpContFragment();
                break;


            case R.id.frg_my_account_txvLogin:
                navtoSigninFragment();
                break;
            case R.id.frg_my_account_llGoogle:
                googleSignIn();
                break;
            case R.id.frg_my_account_llFB:
                signUpFaceBook();
                break;
            case R.id.frg_signup_txv_guest:
                AppConfig.getInstance().mUser.setGuest(true);
                AppConfig.getInstance().mUser.setLoggedIn(false);
                AppConfig.getInstance().mUser.setAuthorization("guest");
                AppConfig.getInstance().saveUserProfile();
                CustomToast.showToastMessage(getActivity(), "You are playing as GUEST", Toast.LENGTH_LONG);
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
        }
    }


    private void navtoSigninFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SiginInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SiginInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SiginInFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navtoSignUpContFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpCompleteProfileFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUpCompleteProfileFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignUpCompleteProfileFragment);
        ft.hide(this);
        ft.commit();
    }


    private void requestAddUserDanetkas(String _signUpEntity) {

        Intro_WebHit_Post_AddUserDanetkas intro_webHit_post_addUserDanetkas = new Intro_WebHit_Post_AddUserDanetkas();
        intro_webHit_post_addUserDanetkas.postAddUserDanetkas(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {


                } else {
//
//                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {

//                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }

    //region Google Integration
    private void googleInit() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//
////            String personName = acct.getDisplayName();
////            String personGivenName = acct.getGivenName();
////            String personFamilyName = acct.getFamilyName();
////            String personEmail = acct.getEmail();
////            String personId = acct.getId();
////            Uri personPhoto = acct.getPhotoUrl();
////            CustomToast.showToastMessage(getActivity(), "Signed in successfully with Google " + acct.getDisplayName(), Toast.LENGTH_LONG);
//        }
    }
    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("LoginActivity", "Google SignIn " + data.toString());
            // The Task returned from this call is always completed, no need to attach
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            Log.d("LOG_AS", "Google Obj : " + acct.getId());

            if (acct != null) {

                requestSocial(acct.getEmail(), "");

            }


            //    updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LOG_AS", "signInResult:failed code=" + e.toString());
            CustomToast.showToastMessage(getActivity(), "Sign in to google is FAILED!" + e.toString(), Toast.LENGTH_LONG);
            // updateUI(null);
        }
    }

    public void requestSocial(String EMAIL, String name) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", EMAIL);
        jsonObject.addProperty("userType", "social");
        requestUserRegisterSocail(jsonObject.toString(), name);
    }

    //endregion


    //region Validation
    private void editTextWatchers() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtEmail.setText("");
                }
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtPassword.setText("");
                }
            }
        });
        editConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    editConfirmPass.setText("");
                }
            }
        });

    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());

    }

    private boolean checkEmailErrorCondition() {
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Empty Email Field", Toast.LENGTH_SHORT).show();
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty email field");
            return false;
        } else if (!edtEmail.getText().toString().matches(AppConstt.EMAIL_PATTERN)) {
            Toast.makeText(getActivity(), "Invalid Email Pattern ", Toast.LENGTH_SHORT).show();
//            AppConfig.getInstance().showErrorMessage(getContext(), "Email pattern is incorrect");
            return false;
        } else {
            return true;
        }
    }

    private boolean comaparePassErrorCondition() {
        if (edtPassword.getText().toString().equals(editConfirmPass.getText().toString())) {
            return true;
        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "The passwords entered do not match");
            Toast.makeText(getActivity(), "The passwords entered do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkConfrmPassErrorCondition() {
        if (editConfirmPass.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty confirm password field");
            Toast.makeText(getActivity(), "Empty confirm password field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPassErrorCondition() {
        if (edtPassword.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty set password field");
            Toast.makeText(getActivity(), "Empty set password field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void checkErrorConditions() {


        if (checkEmailErrorCondition() && checkPassErrorCondition() && checkConfrmPassErrorCondition() && comaparePassErrorCondition()) {


            closeKeyboard();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", edtEmail.getText().toString());
            jsonObject.addProperty("password", edtPassword.getText().toString());
            jsonObject.addProperty("userType", "normal");
            requestUserRegister(jsonObject.toString());

//            AppConfig.getInstance().mUser.Email = edtEmail.getText().toString();
//            AppConfig.getInstance().mUser.isLoggedIn = true;
//            AppConfig.getInstance().saveUserProfile();
//            navtoSignUpContFragment();
        }
    }


//
//    private boolean termCndtionErrorCondition() {
//        if (chbTermCondition.isChecked()) {
//            return true;
//        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Terms and conditions are not checked");
//            return false;
//        }
//    }


//    private boolean checkDobErrorCondition() {
//        if (txvDob.getText().toString().equalsIgnoreCase("Date of Birth")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Date of birth is not selected");
//
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean checkCityErrorCheck() {
//        if (txvSelectedCity.getText().toString().equalsIgnoreCase("Select City")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean checkNumberErrorCondition() {
//        if (edtPhoneNum.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty phone number field");
//            return false;
//        } else {
//            return true;
//        }
//    }


    //
//    private boolean checkNameErrorCondition() {
//        if (edtName.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty name field");
////        Toast.makeText(getActivity(), "Empty Name Feild", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private void showCalender() {
////        new SingleDateAndTimePickerDialog.Builder(getContext())
////                .bottomSheet()
////                .curved()
////                .displayMinutes(false)
////                .displayHours(false)
////                .displayDays(false)
////                .displayMonth(true)
////                .displayYears(true).mainColor(Color.BLACK)
////                .displayDaysOfMonth(true).listener(new SingleDateAndTimePickerDialog.Listener() {
////            @Override
////            public void onDateSelected(Date date) {
////                DateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
////                String datee = format.parse(Date.parse( date.toString()));
////
////                txvDob.setText(date.toString());
//////                if (date.after(yesterday()))
//////                {
//////                    goalDate =datee;
//////                    txvCalender.setText(datee);
//////                    btnNext.setEnabled(true);
//////                }
////            }
////        }).defaultDate(calendar.getTime()).display();
//////
//////
//
//        new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date, calendar
//                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }
    //endregion

    private void signUpFaceBook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.i("LoginActivity", "FB Login Success");


                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("LoginActivity Response ", response.toString());
                                        String Name, FEmail;

                                        try {
                                            Name = object.getString("name");
                                            FEmail = object.getString("email");
                                            requestSocial(FEmail, Name);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("LoginActivity", "FB Login Cancel");
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.i("LoginActivity", "FB Login Error");
                        Toast.makeText(getContext(), "error_login", Toast.LENGTH_SHORT).show();
                    }
                });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }


}