package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

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

public class BundleDiscountFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlApply;

    LinearLayout llBundle;
    private String strID="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_bundle_discount, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            strID = bundle.getString("danetkaID");
        }
    }

    private void bindViewss(View frg) {
        rlApply = frg.findViewById(R.id.apply);
        llBundle = frg.findViewById(R.id.bund);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlApply.setOnClickListener(this);
        llBundle.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.apply:
//                navToPayentDisapprovedFragment();
//                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;

//            case R.id.bund:
//                navToPayentApprovedFragment();
//                    break;
        }
    }

}
