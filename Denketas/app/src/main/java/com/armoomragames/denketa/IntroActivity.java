package com.armoomragames.denketa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries.DenketaInvestigatorQuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.InvestigatorFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaQuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.MyResultsFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayMianFragment;
import com.armoomragames.denketa.IntroAuxilaries.PreSignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ChallengeFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ExtraRulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.GamePlayFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.SplashFragment;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.LocaleHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity implements IBadgeUpdateListener, View.OnClickListener {


    private FragmentManager fm;

    RelativeLayout rlToolbar, rlBack, rlCross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppConfig.getInstance().performLangCheck(getWindow());
        AppConfig.getInstance().regulateFontScale(getResources().getConfiguration(), getBaseContext());
        setContentView(R.layout.activity_intro);

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

        setMyScreenSize();

        bindViews();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash","key  >"+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

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
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_GamePlayFragment))
        {
            navToRulesFragment();
        }
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }


    }


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

    public void navToMyResultsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyResultsFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyResultsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();
    }

    public void navToDenketaQuestionFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaQuestionFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaQuestionFragment);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();

    }
    public void navToDenketaInvestigatorQuestionFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaInvestigatorQuestionFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
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
            }
            else if (frg instanceof DenketaQuestionFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PlayMianFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof MyResultsFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof InvestigatorFragment && frg.isVisible()) {
                ft.hide(frg);
            }


        }

    }


    //endregion

}
