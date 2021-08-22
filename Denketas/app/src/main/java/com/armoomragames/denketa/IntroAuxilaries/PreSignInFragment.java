package com.armoomragames.denketa.IntroAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.Utils.AppConstt;

public class PreSignInFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlPlay, rlDenketa, rlRules,rlSettings;
    TextView txvSettings, txvDictionary, txvPlay,txvRules,txvDenketa;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);


        bindViews(frg);



        return frg;
    }

    private void bindViews(View frg)
    {
        rlPlay = frg.findViewById(R.id.frg_presigin_rlPlay);
        rlDenketa = frg.findViewById(R.id.frg_presigin_rlDenketa);
        rlRules = frg.findViewById(R.id.frg_presigin_rlRules);
        rlSettings = frg.findViewById(R.id.frg_presigin_rlSettings);

        txvSettings = frg.findViewById(R.id.frg_presigin_txvSettings);
        txvDenketa = frg.findViewById(R.id.frg_presigin_txvDenketas);
        txvDictionary = frg.findViewById(R.id.frg_presigin_txvDictionary);
        txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvRules= frg.findViewById(R.id.frg_presigin_txvRules);


        rlPlay.setOnClickListener(this);
        rlDenketa.setOnClickListener(this);
        rlRules.setOnClickListener(this);
        rlSettings.setOnClickListener(this);

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
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SettingsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SettingsFragment);
        ft.hide(this);
        ft.commit();
    }
}
