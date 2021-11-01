package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class ContactFragment extends Fragment implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;
    EditText edtContact;

    RelativeLayout rlToolbar, rlBack, rlCross;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_contact, container, false);


        bindViews(frg);

        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvPlay.startAnimation(shake); // starts animation

        init();
        return frg;
    }



    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);

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


    private void bindViews(View frg)
    {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        edtContact = frg.findViewById(R.id.edtContact);

//        edtContact.setScroller(new Scroller(getContext()));
//        edtContact.setMaxLines(1);
//        edtContact.setVerticalScrollBarEnabled(true);
//        edtContact.setMovementMethod(new ScrollingMovementMethod());
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
            case R.id.frg_settings_rlMyAccount:



                break;
        }
    }




}
