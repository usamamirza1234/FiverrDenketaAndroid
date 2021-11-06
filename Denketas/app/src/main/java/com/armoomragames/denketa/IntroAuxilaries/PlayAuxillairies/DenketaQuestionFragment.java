package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class DenketaQuestionFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlMaster;
    LinearLayout llSeeAnswer;
    Bundle bundle;
    String danetka_name = "";
    IBadgeUpdateListener mBadgeUpdateListener;
    Dialog progressDialog = null; // Context, this, etc.
    TextView txvDanetkaName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_question, container, false);

        init();
        bindViews(frg);
        txvDanetkaName.setText(danetka_name.toUpperCase());

        return frg;
    }

    public void openDialog() {


        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_master);
        rlMaster = progressDialog.findViewById(R.id.rl_popup_parent);
        rlMaster.setOnClickListener(this);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
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
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
        }

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
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        rlBack.setOnClickListener(this);

        llSeeAnswer.setOnClickListener(this);
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_popup_parent:
                dismissProgDialog();
                navToDenketaAnswerFragment(danetka_name.toUpperCase());
                break;
            case R.id.frg_denketa_question_llSeeAnswer:
//                rlMaster.setVisibility(View.VISIBLE);
                openDialog();
                break;

            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
        }
    }


    private void navToDenketaAnswerFragment(String name) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaAnswerFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.hide(this);
        ft.commit();
    }


}
