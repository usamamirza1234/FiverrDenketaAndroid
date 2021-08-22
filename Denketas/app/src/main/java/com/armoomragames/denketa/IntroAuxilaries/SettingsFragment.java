package com.armoomragames.denketa.IntroAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;

public class SettingsFragment extends Fragment implements View.OnClickListener {



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_settings, container, false);


        bindViews(frg);



        return frg;
    }

    private void bindViews(View frg)
    {




    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}
