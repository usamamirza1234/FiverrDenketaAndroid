package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Guest_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_INVESTIGATOR_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_User_Danektas;
import com.armoomragames.denketa.R;

public class LearnMoreFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlBack, rlCross;
    int position = 0;
    Bundle bundle;
    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    TextView txvLearnmore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_learn_more, container, false);

        init();
        bindViews(frg);
        setData();
        return frg;
    }

    private void setData() {
        try {
            if (!isInvestigator) {
                if (!isMoreDanetka) {
                    if (AppConfig.getInstance().mUser.isLoggedIn())
                        txvLearnmore.setText(Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getLearnMore() + "");
                    else  txvLearnmore.setText(Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getLearnMore() + "");

                } else {
                    txvLearnmore.setText(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getLearnMore() + "");
                }
            } else {
                txvLearnmore.setText(Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getLearnMore() + "");

            }
        } catch (Exception e) {

        }
    }

    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
        }
    }

    private void bindViews(View frg) {
        txvLearnmore = frg.findViewById(R.id.txvLearnmore);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlCross.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
        }
    }
}
