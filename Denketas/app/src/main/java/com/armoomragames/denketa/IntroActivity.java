package com.armoomragames.denketa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.DenketaInvestigatorQuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.PaymentApprovedFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.PaymentDetailFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.PaymentFailedFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaQuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.LearnMoreFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.MyResultsFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayMianFragment;
import com.armoomragames.denketa.IntroAuxilaries.PreSignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ChallengeFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ExtraRulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.GamePlayFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.SplashFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.LocaleHelper;
import com.armoomragames.denketa.Utils.RModel_Paypal;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity implements IBadgeUpdateListener, View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    private FragmentManager fm;
    public static final String clientKey = "AQxyBWkhclOXBj9jlkr3eV_F9PQ2O6yBD5f8i1oO2fJNQ5Xy_Ir6N45881igN7lyfIPvxr59JSGnH0B1";
    String danetkaID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppConfig.getInstance().performLangCheck(getWindow());
        AppConfig.getInstance().regulateFontScale(getResources().getConfiguration(), getBaseContext());
        setContentView(R.layout.activity_intro);

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
        fm = getSupportFragmentManager();
        getAppVersion();

        AppConfig.getInstance().loadUserProfile();
        if (AppConfig.getInstance().mLanguage.equalsIgnoreCase(AppConstt.AppLang.LANG_UR)) {
            MyApplication.getInstance().setAppLanguage(AppConstt.AppLang.LANG_UR);
        } else {
            MyApplication.getInstance().setAppLanguage(AppConstt.AppLang.LANG_EN);
        }


        if (AppConfig.getInstance().shouldSkipSplash) {
            AppConfig.getInstance().shouldSkipSplash = false;
            navtoMainActivity();
        } else {
            navToSplash();
        }

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.armoomragames.denketa",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        setMyScreenSize();
//
//        bindViews();
//        Intent intent = new Intent(this, PayPalService.class);
//
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        startService(intent);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", "key  >" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }




    //region App Necessary

    private void bindViews() {
        rlToolbar = findViewById(R.id.act_intro_rl_toolbar);
        rlBack = findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(final Context baseContext) {
        //Handle custom font settings and screen size
        super.attachBaseContext(LocaleHelper.wrap(AppConfig.getInstance().regulateDisplayScale(baseContext),
                new Locale(AppConfig.getInstance().loadDefLanguage())));
    }

    void getAppVersion() {
        try {

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            AppConfig.getInstance().currentAppVersion = String.valueOf(pInfo.versionCode);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConfig.getInstance() != null)//id some view  !=null => activity in initialized
            performActionAgainstFCM();
    }

    private void performActionAgainstFCM() {
        try {
            Intent intent = getIntent();
            SplashFragment.notificationId = intent.getIntExtra(AppConstt.Notifications.PUSH_TYPE, AppConstt.Notifications.TYPE_NIL);
            SplashFragment.orderId = intent.getIntExtra(AppConstt.Notifications.PUSH_ORDER_ID, AppConstt.Notifications.TYPE_NIL);
//            getIntent().removeExtra("notification_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMyScreenSize() {

        //For Full screen Mode
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        AppConfig.getInstance().scrnWidth = metrics.widthPixels;
        AppConfig.getInstance().scrnHeight = metrics.heightPixels - getStatusBarHeight();
        Log.d("Screen Width", "" + AppConfig.getInstance().scrnWidth);
        Log.d("Screen Height", "" + AppConfig.getInstance().scrnHeight);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return (int) (result * this.getResources().getDisplayMetrics().density + 0.5f);
    }

    //endregion

    //region progdialog
    private Dialog progressDialog;

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(this, R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(this).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }
    //endregion

    //region IBadgeUpdateListener

    @Override
    public void setBottomTabVisiblity(int mVisibility) {

    }

    @Override
    public void setToolbarVisiblity(int mVisibility) {

    }

    @Override
    public void setToolbarState(byte mState) {
        switch (mState) {


//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN:
//                rlToolbar.setVisibility(View.GONE);
//                break;
//
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.VISIBLE);
//                rlCross.setVisibility(View.VISIBLE);
//                break;
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.GONE);
//                rlCross.setVisibility(View.VISIBLE);
//                break;
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_CROSS_HIDDEN:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.VISIBLE);
//                rlCross.setVisibility(View.GONE);
//
//                break;

            default:
                break;

        }

    }

    @Override
    public void switchStatusBarColor(boolean isDark) {

    }

    @Override
    public boolean setHeaderTitle(String strAppTitle) {
        return false;
    }

    //endregion

    //region Navigations

    private void navToPayentDisapprovedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFailedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFailedFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }

    private void navToPayentApprovedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentApprovedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentApprovedFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }


    public void navToMyResultsFragment(String name) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyResultsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        frag.setArguments(bundle);
//            ft.hide(this);
        hideLastStackFragment(ft);
        ft.commit();

    }

    public void navToDenketaQuestionFragment(String name, String position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaQuestionFragment();

//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaQuestionFragment);
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putString("key_danetka_position", position);
        frag.setArguments(bundle);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();

    }


    public void navToLearnmoreFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LearnMoreFragment();


//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LearnMoreFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LearnMoreFragment);

        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();

    }


    public void navToDenketaInvestigatorQuestionFragment(String name, String id, String danetkaID, boolean isInvestigator, boolean isMoreDanetka) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaInvestigatorQuestionFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putString("key_danetka_id", id);
        bundle.putString("key_danetka_danetkaID", danetkaID);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();
    }

    public void navToRulesFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesFragment);
//        ft.hide(this);
        hideLastStackFragment(ft);
        ft.commit();
    }


    public void navToPreSignInVAFragment() {
        clearMyBackStack();
        PreSignInFragment frg = new PreSignInFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_PreSignInFragment);

        ft.commit();

    }


    public void navtoMainActivity() {
//        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();

        if (AppConfig.getInstance().mUser.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            //   Intent intent = new Intent(this, MainActivityOLD.class);
            startActivity(intent);
            IntroActivity.this.finish();
        } else {
            navToSplash();
        }
    }

    public String returnStackFragmentTag() {
        int index = fm.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = null;
        String tag = "";
        if (index >= 0) {
            backEntry = fm.getBackStackEntryAt(index);
            tag = backEntry.getName();
        }
        return tag;
    }

    public void clearMyBackStack() {
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();

        }
//        setBackButtonVisibility(View.GONE);
//        txvTitle.setText(getResources().getString(R.string.frg_hom_ttl));
    }

    private void navToSplash() {
        Fragment frg = new SplashFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_SplashFragment);
        ft.commit();
    }

    private void navtoSignInFragment() {
        SplashFragment frg = new SplashFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_SplashFragment);
        ft.commit();
    }

    public void navToBundleDiscountFragment(String id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        Bundle bundle = new Bundle();
        bundle.putString("danetkaID", id);
        frag.setArguments(bundle);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }

    public void navToPaymentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentDetailFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }

    public void hideLastStackFragment(FragmentTransaction ft) {
        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentById(R.id.act_intro_content_frg);

        if (frg != null) {
            if (frg instanceof PreSignInFragment && frg.isVisible()) {
                ft.hide(frg);
            }
            if (frg instanceof ChallengeFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof RulesFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof ExtraRulesFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof GamePlayFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof DenketaQuestionFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PlayMianFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof MyResultsFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof InvestigatorFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof LearnMoreFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof BundleDiscountFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentDetailFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentFailedFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentApprovedFragment && frg.isVisible()) {
                ft.hide(frg);
            }


        }

    }
//
//    public void sendPayment(PayPalPayment payment) {
//        // Creating Paypal Payment activity intent
//        Intent intent = new Intent(this, PaymentActivity.class);
//        //putting the paypal configuration to the intent
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        // Putting paypal payment to the intent
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//        // Starting the intent activity for result
//        // the request code will be used on the method onActivityResult
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }


    //endregion



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.act_intro_lay_toolbar_rlBack:
                onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                navToPreSignInVAFragment();

                break;


        }
    }

    @Override
    public void onBackPressed() {
//
//        String tag = returnStackFragmentTag();
//
//        AppConfig.getInstance().closeKeyboard(this);
//        {
//            Log.d("whileOnBackPress", " Tag " + tag);
//            if ((tag.equalsIgnoreCase(AppConstt.FragTag.FN_SignInFragment)) ||
//                    (tag.equalsIgnoreCase(AppConstt.FragTag.FN_SignUpFragment))
//                    || (tag.equalsIgnoreCase(AppConstt.FragTag.FN_ForgotPasswordFragment))
//            ) {
//                navToPreSignInVAFragment();
//            } else {
//                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                    getSupportFragmentManager().popBackStack();
//                    Log.d("whileOnBackPress", " 123 " + tag);
//                } else {
//                    Log.d("whileOnBackPress", " 456 " + tag);
//                    super.onBackPressed();
//                }
//            }
//        }

        if (returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_RulesFragment)) {
            navToPreSignInVAFragment();
        } else if (returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_ChallengeFragment) ||
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_ExtraRulesFragment) ||
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_GamePlayFragment)) {
            navToRulesFragment();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }


    }
    //region Navigations
    private void requestAddUserDanetkas(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddUserDanetkas intro_webHit_post_addUserDanetkas = new Intro_WebHit_Post_AddUserDanetkas();
        intro_webHit_post_addUserDanetkas.postAddUserDanetkas(this, new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();

                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(IntroActivity.this, strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(IntroActivity.this, ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.


                    String strResponse = confirm.toJSONObject().toString(4);

                    Log.i("paymentExample", strResponse);
                    CustomToast.showToastMessage(this, "Congragulations! you Paid for Danetka(s). ", Toast.LENGTH_SHORT);


                    Gson gson = new Gson();
                    Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                    AppConfig.getInstance().responseObject = gson.fromJson(strResponse, RModel_Paypal.class);

                    if (
                            AppConfig.getInstance().responseObject != null &&
                                    AppConfig.getInstance().responseObject.getResponse() != null
                    ) {
                        if (AppConfig.getInstance().responseObject.getResponse().getState().equalsIgnoreCase("approved")) {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("danetkasId", danetkaID);
                            jsonObject.addProperty("create_time", AppConfig.getInstance().responseObject.getResponse().getCreate_time());
                            jsonObject.addProperty("id", AppConfig.getInstance().responseObject.getResponse().getId());
                            jsonObject.addProperty("intent", AppConfig.getInstance().responseObject.getResponse().getIntent());
                            jsonObject.addProperty("state", AppConfig.getInstance().responseObject.getResponse().getState());
//                                                    jsonObject.addProperty("response_type", strID.toString());
//                                                    jsonObject.addProperty("environment", strID.toString());
//                                                    jsonObject.addProperty("platform", strID.toString());
                            requestAddUserDanetkas(jsonObject.toString());
                            danetkaID = "0";
                            navToPayentApprovedFragment();
                        }
                    }


                } catch (JSONException e) {
                    navToPayentDisapprovedFragment();
                    CustomToast.showToastMessage(this, "an extremely unlikely failure occurred: " + e, Toast.LENGTH_SHORT);
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
            navToPayentDisapprovedFragment();
            CustomToast.showToastMessage(this, "The user canceled.: ", Toast.LENGTH_SHORT);

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            navToPayentDisapprovedFragment();
            CustomToast.showToastMessage(this, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs. ", Toast.LENGTH_SHORT);
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    public void onBuyPressed(String _danetkaID) {
        danetkaID = _danetkaID;
        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
        // Change PAYMENT_INTENT_SALE to
        //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
        //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
        //     later via calls from your server.

        PayPalPayment payment = new PayPalPayment(new BigDecimal("0.99"), "EUR", "1 Danetka",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    private static final PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId(clientKey).merchantName("Armoomra games")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    //endregion
}
