package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.SettingsAuxillaries.SignUpFragment;
import com.armoomragames.denketa.Utils.AppConstt;

public class SettingsFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlMyAccount, rlLang, rlRate, rlContact, rlFaq, rlAboutus, rlPrivacy;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_settings, container, false);


        bindViews(frg);


        return frg;
    }

    private void bindViews(View frg) {

        rlMyAccount = frg.findViewById(R.id.frg_settings_rlMyAccount);
        rlLang = frg.findViewById(R.id.frg_settings_rllang);
        rlRate = frg.findViewById(R.id.frg_settings_rlRate);
        rlContact = frg.findViewById(R.id.frg_settings_rlcontact);
        rlFaq = frg.findViewById(R.id.frg_settings_rlfaq);
        rlAboutus = frg.findViewById(R.id.frg_settings_rlAbout);
        rlPrivacy = frg.findViewById(R.id.frg_settings_rlPrivacy);

        rlMyAccount.setOnClickListener(this);
        rlLang.setOnClickListener(this);
        rlRate.setOnClickListener(this);
        rlContact.setOnClickListener(this);
        rlFaq.setOnClickListener(this);
        rlAboutus.setOnClickListener(this);
        rlPrivacy.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_settings_rlMyAccount:


                navtoMyAccountFragment();

                break;
            case R.id.frg_settings_rllang:


                navtoLanguageFragment();

                break;
            case R.id.frg_settings_rlRate:


                navtoRateFragment();

                break;
            case R.id.frg_settings_rlcontact:


                navtoContactFragment();

                break;
            case R.id.frg_settings_rlfaq:


                navtoFaqFragment();

                break;
            case R.id.frg_settings_rlAbout:


                navtoAboutFragment();

                break;
            case R.id.frg_settings_rlPrivacy:


                
                navtoPrivacyFragment();

                break;
        }
    }

    private void navtoPrivacyFragment() {
    }

    private void navtoAboutFragment() {
    }

    private void navtoMyAccountFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyAccountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyAccountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoLanguageFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LanguageFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LanguageFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LanguageFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navtoRateFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RateAppFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RateAppFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RateAppFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoFaqFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new FaqFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_FaqFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_FaqFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoContactFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ContactFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ContactFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ContactFragment);
        ft.hide(this);
        ft.commit();
    }

}
