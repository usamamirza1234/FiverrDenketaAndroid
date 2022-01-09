package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;

    TextView txvDanetkaUnclocked,txvDanetkaLocked,txvDanetkaPlayed,txAvailableCredits,txvEditProfile,txvUsername;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_profile, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {

    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);


        txAvailableCredits = frg.findViewById(R.id.txvDanetkaAvailable);
        txvDanetkaLocked = frg.findViewById(R.id.txvDanetkalocked);
        txvDanetkaUnclocked = frg.findViewById(R.id.txvDanetkaUnclocked);
        txvDanetkaPlayed = frg.findViewById(R.id.txvDanetkaPlayed);
        txvUsername = frg.findViewById(R.id.txvUsername);
        txvEditProfile = frg.findViewById(R.id.txvEditProfile);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        txvEditProfile.setOnClickListener(this);




        txvUsername.setText(AppConfig.getInstance().mUser.getName());
        txAvailableCredits.setText(AppConfig.getInstance().mUser.getGameCredits());
        txvDanetkaLocked.setText(AppConfig.getInstance().mUser.getDanetkaPurchased());
        txvDanetkaUnclocked.setText(AppConfig.getInstance().mUser.getDanetkaPurchased());
        txvDanetkaPlayed.setText(AppConfig.getInstance().mUser.getDanetkaPurchased());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
            case R.id.txvEditProfile:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
        }
    }


}
