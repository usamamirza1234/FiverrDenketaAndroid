package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlSubmit;
    RelativeLayout rlEmailsent;

    LinearLayout llBack;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        init();
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

        rlSubmit = frg.findViewById(R.id.frg_frgtPass_rlSubmit);
        rlEmailsent = frg.findViewById(R.id.frg_frgtPass_rlEmailsent);
        llBack = frg.findViewById(R.id.frg_forgetpass_llBack);

        rlSubmit.setOnClickListener(this);
        rlEmailsent.setOnClickListener(this);
        llBack.setOnClickListener(this);

    }

    private void navToResetPassword() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ResetPasswordFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ResetPasswordFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ResetPasswordFragment);
        ft.hide(this);
        ft.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.frg_forgetpass_llBack:
                ((IntroActivity)getActivity()).onBackPressed();
                break;
            case R.id.frg_frgtPass_rlSubmit:
            case R.id.frg_frgtPass_rlEmailsent:
                navToResetPassword();
                break;
        }
    }


}
