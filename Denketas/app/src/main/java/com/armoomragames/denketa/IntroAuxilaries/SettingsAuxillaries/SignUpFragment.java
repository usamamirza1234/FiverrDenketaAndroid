package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_SignUp;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

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


    CallbackManager callbackManager;
    EditText edtPassword, edtEmail, editConfirmPass;
    private Dialog progressDialog;

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
        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvPlay.startAnimation(shake); // starts animation

        llFB = frg.findViewById(R.id.frg_my_account_llFB);
        llGoogle = frg.findViewById(R.id.frg_my_account_llGoogle);
        txvLogin = frg.findViewById(R.id.frg_my_account_txvLogin);
        rlRegister = frg.findViewById(R.id.frg_my_account_rlRegister);


        editConfirmPass = frg.findViewById(R.id.frg_signup_edtCnfirmPassword);
        edtEmail = frg.findViewById(R.id.frg_signup_edtEmail);
        edtPassword = frg.findViewById(R.id.frg_signup_edtPassword);

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
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_progress);
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

                    AppConfig.getInstance().mUser.isLoggedIn = true;
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getAccessToken();

                    AppConfig.getInstance().saveUserProfile();
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
                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });


                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

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
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("LOG_AS", "onActivityResult: google sign in " + data.toString());
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
//                googleUserEmail = acct.getEmail();
//                googleSocailID = acct.getId();


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", acct.getEmail());
//                jsonObject.addProperty("password", edtPassword.getText().toString());
                jsonObject.addProperty("userType", "social");
                requestUserRegister(jsonObject.toString());
//
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("name", acct.getDisplayName());
//                jsonObject.addProperty("email", googleUserEmail);
//                jsonObject.addProperty("social_id", googleSocailID);
//                jsonObject.addProperty("social_platform", "google");
//                jsonObject.addProperty("user_type", "user");
//                jsonObject.addProperty("city_id", "1");
//                jsonObject.addProperty("city", "jaddah");
//                jsonObject.addProperty("device_token", AppConfig.getInstance().loadFCMDeviceToken());
//                jsonObject.addProperty("login_type", "social");
//                jsonObject.addProperty("device_type", "android");
//                Log.d("LOG_AS", "Google Sign IN JSON : " + jsonObject.toString());
//
//                requestSignInGoogle(jsonObject.toString());
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

}
