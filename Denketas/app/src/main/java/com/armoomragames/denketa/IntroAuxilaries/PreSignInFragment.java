package com.armoomragames.denketa.IntroAuxilaries;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.InvestigatorFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_GameCredits;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.BrowserSwitchResult;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.CardClient;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.braintreepayments.api.PayPalVaultRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class PreSignInFragment extends Fragment implements View.OnClickListener {

    private static final int DROP_IN_REQUEST_CODE = 590;
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlPlay, rlDenketa, rlRules, rlSettings, rlDictionary;
    //    TextView txvSettings, txvDictionary, txvPlay, txvRules, txvDenketa;
    ImageView imv_master, imv_master_hat;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;
    String clientToken;
    //    BraintreeGateway gateway = new BraintreeGateway(
//            Environment.SANDBOX,
//            "mybf9tq8g5qv92zw",
//            "vttwyyqt2v7nb2j5",
//            "3d8c81a8fa758c9c395ac81872da64b2"
//    );
    AsyncHttpClient client;
    ProgressBar progressBar;
    int userId;
    CardClient cardClient;
    PayPalClient payPalClient;
    private BraintreeClient braintreeClient;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);


        braintreeClient = new BraintreeClient(getContext(), "sandbox_v2nf5t6c_mybf9tq8g5qv92zw");
        payPalClient = new PayPalClient(braintreeClient);



        init();
        bindViews(frg);
        requestGameCredits();
        return frg;
    }





    //region init
    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

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


        rlToolbar.setVisibility(View.GONE);
//        rlBack.setVisibility(View.GONE);
//        rlCross.setVisibility(View.GONE);
//
//        rlToolbar.setVisibility(View.VISIBLE);
//        rlBack.setVisibility(View.VISIBLE);
//        rlCross.setVisibility(View.VISIBLE);


        rlPlay = frg.findViewById(R.id.frg_presigin_rlPlay);
        rlDenketa = frg.findViewById(R.id.frg_presigin_rlDenketa);
        rlRules = frg.findViewById(R.id.frg_presigin_rlRules);
        rlSettings = frg.findViewById(R.id.frg_presigin_rlSettings);
        rlDictionary = frg.findViewById(R.id.frg_presigin_rldictionary);

//        txvSettings = frg.findViewById(R.id.frg_presigin_txvSettings);
//        txvDenketa = frg.findViewById(R.id.frg_presigin_txvDenketas);
//        txvDictionary = frg.findViewById(R.id.frg_presigin_txvDictionary);
//        txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
//        txvRules = frg.findViewById(R.id.frg_presigin_txvRules);


        imv_master = frg.findViewById(R.id.imv_master);
        imv_master_hat = frg.findViewById(R.id.imv_master_hat);


        rlPlay.setOnClickListener(this);
        rlDenketa.setOnClickListener(this);
        rlRules.setOnClickListener(this);
        rlSettings.setOnClickListener(this);
        rlDictionary.setOnClickListener(this);

        imv_master.setOnClickListener(this);
        imv_master_hat.setOnClickListener(this);

        Typeface tfEng = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aladin_Regular.ttf");


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://your-server/client_token", new TextHttpResponseHandler() {
            private String clientToken;

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                this.clientToken = clientToken;
            }
        });

        cardClient = new CardClient(braintreeClient);

    }


    public void onBraintreeSubmit() {

//        DropInRequest dropInRequest = new DropInRequest();
//        DropInClient dropInClient = new DropInClient(getContext(), "sandbox_f252zhq7_hh4cpc39zq4rgjcg", dropInRequest);
//        dropInClient.launchDropInForResult(getActivity(), DROP_IN_REQUEST_CODE);
    }


//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.d("DROP_IN_REQUEST_CODE","requestCode : " + requestCode);
//
//        if (requestCode == DROP_IN_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentMethodNonce = result.getPaymentMethodNonce().getString();
//                // use the result to update your UI and send the payment method nonce to your server
//
//                Log.d("DROP_IN_REQUEST_CODE","Success : " + paymentMethodNonce);
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//                Log.d("DROP_IN_REQUEST_CODE","RESULT_CANCELED : " + RESULT_CANCELED);
//                // the user canceled
//            } else {
//                // an error occurred, checked the returned exception
//                Exception exception = (Exception) data.getSerializableExtra(DropInResult.EXTRA_ERROR);
//                Log.d("DROP_IN_REQUEST_CODE","EXTRA_ERROR : " + exception.getMessage());
//
//            }
//        }
//    }
    //endregion

    //region Onclicks
    public void openDialoguePlay() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_play_as);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout txvRules = dialog.findViewById(R.id.lay_item_play_txvRules);
        LinearLayout txvMaster = dialog.findViewById(R.id.lay_item_play_txvMaster);
        LinearLayout txvInvestigator = dialog.findViewById(R.id.lay_item_play_txvInvestigator);
        RelativeLayout rlCross = dialog.findViewById(R.id.lay_item_play_rlCross);


        txvInvestigator.setOnClickListener(this);
        txvMaster.setOnClickListener(this);
        txvRules.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_presigin_rlPlay:
//
//                if (AppConfig.getInstance().mUser.isLoggedIn)
//                    openDialoguePlay();
//                else
//                    navtoSigninFragment();

                openDialoguePlay();
                break;

            case R.id.lay_item_play_txvRules:

                dialog.dismiss();
                navToRulesFragment();
                break;

            case R.id.lay_item_play_txvInvestigator:


                dialog.dismiss();
                navToInvestigatorFragment();
                break;
            case R.id.lay_item_play_txvMaster:

                dialog.dismiss();
                navToPlayFragment();
                break;
            case R.id.lay_item_play_rlCross:

                dialog.dismiss();

                break;

            case R.id.frg_presigin_rlDenketa:
                navToDenketaWhatFragment();
                break;

            case R.id.frg_presigin_rlRules:
                navToRulesFragment();
                break;
            case R.id.frg_presigin_rldictionary:
//                navToDictionaryFragment();
                onBraintreeSubmit();
                break;
            case R.id.frg_presigin_rlSettings:
                navToSettingsFragment();
                break;


            case R.id.imv_master:
                hideMaster();

                break;


            case R.id.imv_master_hat:
                showMaster();
                break;

            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
        }
    }

    private void hideMaster() {
        Animation Upbottom = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
        imv_master.startAnimation(Upbottom);
        imv_master.setVisibility(View.GONE);
//        imv_master_hat.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            imv_master_hat.setVisibility(View.VISIBLE);

        }, 1000);
    }

    private void showMaster() {
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        imv_master_hat.setVisibility(View.GONE);
        imv_master.setVisibility(View.VISIBLE);
//        imv_master_hat.startAnimation(bottomUp);


//        imv_master.setOnClickListener(null);

//        final Handler handler = new Handler();
//        handler.postDelayed(() -> {
//            Animation Upbottom = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
//            imv_master.startAnimation(Upbottom);
//            imv_master.setVisibility(View.GONE);
//            imv_master_hat.setVisibility(View.VISIBLE);
////            enableOnclickHat();
//        }, 500);
    }
    //endregion

    //region Navigations
    private void navToInvestigatorFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new InvestigatorFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_InvestigatorFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_InvestigatorFragment);
//        ft.hide(this);
        ft.hide(this);
        ft.commit();

    }

    private void navToDenketaWhatFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new WhatDenketaFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_WhatDenketaFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_WhatDenketaFragment);
        ft.hide(this);
        ft.commit();
    }

    public void navToPlayFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PlayMianFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.PlayMianFragment);
        ft.addToBackStack(AppConstt.FragTag.PlayMianFragment);
//        ft.hide(this);
        ft.hide(this);
        ft.commit();

    }

    private void navToRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToDictionaryFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DictionaryFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DictionaryFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DictionaryFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToSettingsFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SettingsFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SettingsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SettingsFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoSigninFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SiginInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SiginInFragment);
        ft.hide(this);
        ft.commit();
    }
    //endregion


    private void requestGameCredits() {


        Intro_WebHit_Get_GameCredits intro_webHit_get_gameCredits = new Intro_WebHit_Get_GameCredits();
        intro_webHit_get_gameCredits.getGameCredits(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    AppConfig.getInstance().mUser.GameCredits = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getCredits() + "";
                    AppConfig.getInstance().mUser.DanetkaPurchased = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPurchased() + "";
                    AppConfig.getInstance().mUser.DanetkaPlayed = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPlayed() + "";
                    AppConfig.getInstance().mUser.DanetkaTotal = Intro_WebHit_Get_GameCredits.responseObject.getData().getToatalDanetkas() + "";
                    AppConfig.getInstance().saveUserProfile();

                } else {
                }

            }

            @Override
            public void onWebException(Exception ex) {

            }
        });

    }


}
