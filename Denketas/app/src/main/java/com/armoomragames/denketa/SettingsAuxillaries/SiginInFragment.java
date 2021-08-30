package com.armoomragames.denketa.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class SiginInFragment extends Fragment implements View.OnClickListener {

    TextView txvLogin, txvForgot;
    RelativeLayout rlLogin;
    LinearLayout llGoogle, llFB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_signin, container, false);


        bindViews(frg);


        init();
        return frg;
    }

    IBadgeUpdateListener mBadgeUpdateListener;

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

        llFB = frg.findViewById(R.id.fg_signin_llFB);
        llGoogle = frg.findViewById(R.id.fg_signin_llGoogle);
        txvLogin = frg.findViewById(R.id.fg_signin_txvLogin);
        txvForgot = frg.findViewById(R.id.fg_signin_txvforgot);
        rlLogin = frg.findViewById(R.id.fg_signin_rlLogin);

        llFB.setOnClickListener(this);
        llGoogle.setOnClickListener(this);
        txvLogin.setOnClickListener(this);
        rlLogin.setOnClickListener(this);
        txvForgot.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fg_signin_rlLogin:
//                navtoSignUpContFragment();
                break;


            case R.id.fg_signin_txvLogin:
//                navtoSigninFragment();
                break;
            case R.id.fg_signin_txvforgot:
                navToForgotPassword();
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
    private void navToForgotPassword() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ForgotPasswordFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ForgotPasswordFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ForgotPasswordFragment);
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
}
