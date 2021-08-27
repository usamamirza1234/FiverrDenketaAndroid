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

public class SignUpFragment extends Fragment implements View.OnClickListener {


    TextView txvLogin;
    RelativeLayout rlRegister;
    LinearLayout llGoogle, llFB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_account, container, false);


        bindViews(frg);


        return frg;
    }

    private void bindViews(View frg) {

        llFB = frg.findViewById(R.id.frg_my_account_llFB);
        llGoogle = frg.findViewById(R.id.frg_my_account_llGoogle);
        txvLogin = frg.findViewById(R.id.frg_my_account_txvLogin);
        rlRegister = frg.findViewById(R.id.frg_my_account_rlRegister);

        llFB.setOnClickListener(this);
        llGoogle.setOnClickListener(this);
        txvLogin.setOnClickListener(this);
        rlRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_my_account_rlRegister:
                navtoSignUpContFragment();
                break;


            case R.id.frg_my_account_txvLogin:
                navtoSigninFragment();
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
}