package com.armoomragames.denketa.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

public class FaqFragment extends Fragment implements View.OnClickListener {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_faq, container, false);


        bindViews(frg);



        return frg;
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
