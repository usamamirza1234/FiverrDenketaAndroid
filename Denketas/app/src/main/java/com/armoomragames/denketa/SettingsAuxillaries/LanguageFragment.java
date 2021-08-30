package com.armoomragames.denketa.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.SettingsAuxillaries.SignUpFragment;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class LanguageFragment extends Fragment implements View.OnClickListener {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_language, container, false);


        bindViews(frg);

        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvPlay.startAnimation(shake); // starts animation

        init();
        return frg;
    }

    IBadgeUpdateListener mBadgeUpdateListener;

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


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_settings_rlMyAccount:



                break;
        }
    }




}
