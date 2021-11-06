package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

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
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class DenketaAnswerFragment extends Fragment implements View.OnClickListener {
    RelativeLayout  rlBack, rlCross;
    Bundle bundle;
    String danetka_name = "";
    TextView txvDanetkaName;
    IBadgeUpdateListener mBadgeUpdateListener;

    LinearLayout llLearnMore;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_answer, container, false);

        init();
        bindViews(frg);
        txvDanetkaName.setText(danetka_name.toUpperCase());
        return frg;
    }
    //region init
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
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);

        llLearnMore = frg.findViewById(R.id.frg_denketa_answer_llLearnmore);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        llLearnMore.setOnClickListener(this);
    }
    //endregion
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();
                break;

            case R.id.frg_denketa_answer_llLearnmore:
              navToLearnmoreFragment();
                break;
        }
    }


    public void navToLearnmoreFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LearnMoreFragment();


//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LearnMoreFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LearnMoreFragment);

//        hideLastStackFragment(ft);
        ft.hide(this);
        ft.commit();

    }
}
