package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

public class PaymentApprovedFragment extends Fragment implements View.OnClickListener {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment_approved, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {

    }

    private void bindViewss(View frg) {

    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}
