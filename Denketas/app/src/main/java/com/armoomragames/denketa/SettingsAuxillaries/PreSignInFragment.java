package com.armoomragames.denketa.SettingsAuxillaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.RulesMianFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsFragment;
import com.armoomragames.denketa.IntroAuxilaries.WhatDenketaFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class PreSignInFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlPlay, rlDenketa, rlRules, rlSettings;
    TextView txvSettings, txvDictionary, txvPlay, txvRules, txvDenketa;
    ImageView imv_master,imv_master_hat;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);

        init();
        bindViews(frg);





        return frg;
    }
    public void onShakeImage() {

    }
    IBadgeUpdateListener mBadgeUpdateListener;

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
        rlPlay = frg.findViewById(R.id.frg_presigin_rlPlay);
        rlDenketa = frg.findViewById(R.id.frg_presigin_rlDenketa);
        rlRules = frg.findViewById(R.id.frg_presigin_rlRules);
        rlSettings = frg.findViewById(R.id.frg_presigin_rlSettings);

        txvSettings = frg.findViewById(R.id.frg_presigin_txvSettings);
        txvDenketa = frg.findViewById(R.id.frg_presigin_txvDenketas);
        txvDictionary = frg.findViewById(R.id.frg_presigin_txvDictionary);
        txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvRules = frg.findViewById(R.id.frg_presigin_txvRules);


        imv_master = frg.findViewById(R.id.imv_master);
        imv_master_hat = frg.findViewById(R.id.imv_master_hat);



        rlPlay.setOnClickListener(this);
        rlDenketa.setOnClickListener(this);
        rlRules.setOnClickListener(this);
        rlSettings.setOnClickListener(this);

        imv_master.setOnClickListener(this);
        imv_master_hat.setOnClickListener(this);

        Typeface tfEng = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aladin_Regular.ttf");

        txvRules.setTypeface(tfEng);
        txvPlay.setTypeface(tfEng);
        txvDictionary.setTypeface(tfEng);
        txvDenketa.setTypeface(tfEng);
        txvSettings.setTypeface(tfEng);
        AppConfig.getInstance().tfAppDefault = txvRules.getTypeface();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_presigin_rlPlay:
                navToPlayFragment();
                break;

            case R.id.frg_presigin_rlDenketa:
                navToDenketaWhatFragment();
                break;

            case R.id.frg_presigin_rlRules:
                navToRulesFragment();
                break;
            case R.id.frg_presigin_rldictionary:
                navToDictionaryFragment();
                break;
            case R.id.frg_presigin_rlSettings:
                navToSettingsFragment();
                break;



                case R.id.imv_master:

                    Animation Upbottom = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);


                    imv_master.startAnimation(Upbottom);
                    imv_master.setVisibility(View.GONE);
                    imv_master_hat.setVisibility(View.VISIBLE);

                    break;


            case R.id.imv_master_hat:

                Animation  bottomUp = AnimationUtils.loadAnimation(getContext(),
                        R.anim.bottom_up);

                imv_master_hat.setVisibility(View.GONE);
                imv_master.setVisibility(View.VISIBLE);
                imv_master_hat.startAnimation(bottomUp);
                break;
        }
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

    private void navToPlayFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new SignInFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
//        ft.hide(this);
//        ft.commit();
    }

    private void navToRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesMianFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesMianFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToDictionaryFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new ForgotPasswordFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
//        ft.hide(this);
//        ft.commit();
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
}
