package com.armoomragames.denketa.RulesAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

public class RulesFragment extends Fragment implements View.OnClickListener {


    TextView txvDesc1, txvDesc2;
    TextView txvGameStart;
    TextView txvInvestigator;


    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new RulesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_rules, container, false);

        bindViews(frg);

        return frg;
    }

    private void bindViews(View frg) {




        txvDesc1 = (TextView) frg.findViewById(R.id.frg_rules_txvDescription1);
        txvDesc2 = (TextView) frg.findViewById(R.id.frg_rules_txvDescription2);
        txvGameStart = (TextView) frg.findViewById(R.id.frg_rules_txvGameStart);
        txvInvestigator = (TextView) frg.findViewById(R.id.frg_rules_txvInvestigator1);



        try {


            String desc = getString(R.string.frg_rules_desc);
            String desc1 = getString(R.string.frg_rules_desc2);
            SpannableString ss = new SpannableString(desc);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 166, 177, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvDesc1.setText(ss);

            ss = new SpannableString(desc1);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 131, 138, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvDesc2.setText(ss);


            String strGame = getString(R.string.frg_rules_game_start);
            ss = new SpannableString(strGame);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 1, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvGameStart.setText(ss);


            String strinvestigator2 = getString(R.string.frg_rules_investigator2);
            ss = new SpannableString(strinvestigator2);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 24, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvInvestigator.setText(ss);
        } catch (Exception e) {

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}
