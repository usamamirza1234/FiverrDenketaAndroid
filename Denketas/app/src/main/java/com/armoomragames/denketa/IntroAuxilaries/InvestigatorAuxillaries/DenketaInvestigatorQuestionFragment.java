package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaAnswerFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class DenketaInvestigatorQuestionFragment extends Fragment implements View.OnClickListener {


    LinearLayout llSeeAnswer;
    LinearLayout llPaynow;
    LinearLayout llBundleDiscount;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_investigator_question, container, false);

        init();
        bindViews(frg);

        return frg;
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(getContext()); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_master);

        dialog.show();
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

        llSeeAnswer = frg.findViewById(R.id.frg_denketa_question_llSeeAnswer);
        llPaynow = frg.findViewById(R.id.frg_denketa_question_llBuyNow);
        llBundleDiscount = frg.findViewById(R.id.frg_denketa_question_llBundleDiscount);


        llSeeAnswer.setOnClickListener(this);
        llPaynow.setOnClickListener(this);
        llBundleDiscount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
        Fragment frag = new DenketaAnswerFragment();
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
