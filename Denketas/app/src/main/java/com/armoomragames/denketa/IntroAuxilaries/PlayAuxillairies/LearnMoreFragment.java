package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;

public class LearnMoreFragment extends Fragment implements View.OnClickListener {
    RelativeLayout  rlBack, rlCross;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_learn_more, container, false);

        init();
        bindViews(frg);

        return frg;
    }




    void init() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {

        }
    }


    private void bindViews(View frg) {



        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlCross.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();
                break;


        }
    }








}
