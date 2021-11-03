package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;

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

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
        }
    }


}