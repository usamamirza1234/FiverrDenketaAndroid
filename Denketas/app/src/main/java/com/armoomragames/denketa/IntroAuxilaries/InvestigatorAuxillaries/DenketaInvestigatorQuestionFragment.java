package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.app.Dialog;
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

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaAnswerFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameResultsFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class DenketaInvestigatorQuestionFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    LinearLayout llSeeAnswer;
    LinearLayout llPaynow;
    LinearLayout llBundleDiscount;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    String danetka_name = "";
    TextView txvDanetkaName;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_investigator_question, container, false);

        init();
        bindViews(frg);
        txvDanetkaName.setText(danetka_name.toUpperCase());
        return frg;
    }




    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
        }

    }




    private void bindViews(View frg) {

        llSeeAnswer = frg.findViewById(R.id.frg_denketa_question_llSeeAnswer);
        llPaynow = frg.findViewById(R.id.frg_denketa_question_llBuyNow);
        llBundleDiscount = frg.findViewById(R.id.frg_denketa_question_llBundleDiscount);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
//        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
//        rlCross.setOnClickListener(this);

        llSeeAnswer.setOnClickListener(this);
        llPaynow.setOnClickListener(this);
        llBundleDiscount.setOnClickListener(this);
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
            case R.id.frg_denketa_question_llSeeAnswer:
                navToDenketaAnswerFragment();
                break;

            case R.id.frg_denketa_question_llBundleDiscount:
                navToBundleDiscountFragment();
                break;
            case R.id.frg_denketa_question_llBuyNow:
                navToPaymentFragment();
                break;

        }
    }


    private void navToDenketaAnswerFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new GameResultsFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToBundleDiscountFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPaymentFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFragment);
        ft.hide(this);
        ft.commit();
    }

}
