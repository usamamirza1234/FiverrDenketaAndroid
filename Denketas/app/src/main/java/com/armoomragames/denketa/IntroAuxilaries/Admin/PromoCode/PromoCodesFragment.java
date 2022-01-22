package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class PromoCodesFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlDenketaDetails, rlPromo,rlsave;
    EditText edtPromo,edtValidFrom,edtValidTill,edtStarttime,edtEndtime;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_promo_codes, container, false);

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
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

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
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        edtPromo = frg.findViewById(R.id.frg_add_promo_edt_promo);
        edtValidFrom = frg.findViewById(R.id.frg_add_promo_edt_valid_from);
        edtValidTill = frg.findViewById(R.id.frg_add_promo_edt_valid_till);
        edtStarttime = frg.findViewById(R.id.frg_add_promo_edt_start_time);
        edtEndtime = frg.findViewById(R.id.frg_add_promo_edt_end_t);
        rlsave = frg.findViewById(R.id.frg_add_promo_edt_save);

        rlsave.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_add_promo_edt_save:
                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
        }
    }

}
