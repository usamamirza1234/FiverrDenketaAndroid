package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

public class WhatDenketaFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_what, container, false);



        return frg;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }
}
