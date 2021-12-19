package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;

public class PaymentApprovedFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    TextView txvPaymentDescription;
    String credit, total;

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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            credit = bundle.getString("key_danetka_credit");
            total = bundle.getString("key_danetka_total");

        }
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        txvPaymentDescription = frg.findViewById(R.id.txvPaymentDescription);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);



        txvPaymentDescription.setText("You now have "+total+" amount of credits to unlock danetkas. 1 Danetka = 1 Game Credit");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
        }
    }


}
