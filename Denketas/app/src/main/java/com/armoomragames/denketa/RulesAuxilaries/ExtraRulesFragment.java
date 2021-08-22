package com.armoomragames.denketa.RulesAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;

public class ExtraRulesFragment extends Fragment implements View.OnClickListener {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_extra_rules, container, false);

        bindViews(frg);

        return frg;
    }

    private void bindViews(View frg) {

    }

    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new ExtraRulesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


        }

    }


}
