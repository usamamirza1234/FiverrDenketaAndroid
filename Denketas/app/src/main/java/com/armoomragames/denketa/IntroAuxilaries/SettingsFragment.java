package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.AboutFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.FaqFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.LanguageFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.PrivacyFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.RateAppFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignUpFragment;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    final int amountToMoveDown = 20, amountToMoveRight = 20;

    LinearLayout llParentSettings;
    RelativeLayout rlMyAccount, rlLang, rlRate, rlContact, rlFaq, rlAboutus, rlPrivacy;
    FrameLayout pozadi_motyl;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_settings, container, false);


        bindViews(frg);
        llParentSettings.animate().x(20f).y(50f).setDuration(700);







        init();
        return frg;
    }


//    void animate(View view)
//    {
//        TranslateAnimation anim = new TranslateAnimation(0, amountToMoveRight, 0, amountToMoveDown);
//        anim.setDuration(1000);
//
//        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
//                params.topMargin += amountToMoveDown;
//                params.leftMargin += amountToMoveRight;
//                view.setLayoutParams(params);
//            }
//        });
//
//        view.startAnimation(anim);
//
//    }


    IBadgeUpdateListener mBadgeUpdateListener;

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN);

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

        pozadi_motyl = frg.findViewById(R.id.pozadi0);
        llParentSettings = frg.findViewById(R.id.llParentSettings);
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PrivacyFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PrivacyFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PrivacyFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoAboutFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AboutFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_AboutFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_AboutFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoMyAccountFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyAccountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyAccountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoLanguageFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LanguageFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LanguageFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LanguageFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navtoRateFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RateAppFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RateAppFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RateAppFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoFaqFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new FaqFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_FaqFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_FaqFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoContactFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ContactFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ContactFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ContactFragment);
        ft.hide(this);
        ft.commit();
    }

}
