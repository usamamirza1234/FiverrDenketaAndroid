package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.RulesMianFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class MyResultsFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_results, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {

    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
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
        }
    }


}
