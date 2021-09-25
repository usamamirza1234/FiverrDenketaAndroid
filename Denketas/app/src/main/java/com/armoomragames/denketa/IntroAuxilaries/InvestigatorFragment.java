package com.armoomragames.denketa.IntroAuxilaries;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaQuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.MyDenketaRcvAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class InvestigatorFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcvMyDenekta;
    Dialog dialog;


    ArrayList<DModel_MyDenketa> lst_MyDenketa;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_investigator, container, false);

        init();
        bindViewss(frg);
        populatePopulationList();
        return frg;
    }

    private void init() {
        lst_MyDenketa = new ArrayList<>();
    }

    private void bindViewss(View frg) {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);

    }


    public void onClickDenketaItem(int position) {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_item_rules);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txvRules = dialog.findViewById(R.id.lay_item_play_txvRules);
//        TextView txvMaster = dialog.findViewById(R.id.lay_item_play_txvMaster);
//        TextView txvInvestigator = dialog.findViewById(R.id.lay_item_play_txvInvestigator);
        LinearLayout llOkay = dialog.findViewById(R.id.lay_item_rules_llOkay);


//        txvInvestigator.setOnClickListener(this);
//        txvMaster.setOnClickListener(this);
        txvRules.setOnClickListener(this);
        llOkay.setOnClickListener(this);
//        llOkay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navToDenketaQuestionFragment();
//            }
//        });

        dialog.show();
    }

    private void populatePopulationList() {

        MyDenketaRcvAdapter myDenketaRcvAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (myDenketaRcvAdapter == null) {

            lst_MyDenketa.add(new DModel_MyDenketa("First", ""));
            lst_MyDenketa.add(new DModel_MyDenketa("Second", ""));
            myDenketaRcvAdapter = new MyDenketaRcvAdapter(getActivity(), lst_MyDenketa, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:
                        onClickDenketaItem(position);

                        break;
                }

            });


            rcvMyDenekta.setLayoutManager(linearLayoutManager);
            rcvMyDenekta.setAdapter(myDenketaRcvAdapter);

        } else {
            myDenketaRcvAdapter.notifyDataSetChanged();
        }
    }


    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new InvestigatorFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.lay_item_play_txvRules:

                dialog.dismiss();
                navToRulesFragment();
                break;            case R.id.lay_item_rules_llOkay:

                dialog.dismiss();

                break;
        }
    }

    private void navToRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesMianFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesMianFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        ft.hide(this);
        ft.commit();
    }

    public void navToDenketaQuestionFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaQuestionFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaQuestionFragment);
//        ft.hide(this);
        ft.hide(this);
        ft.commit();

    }
}
