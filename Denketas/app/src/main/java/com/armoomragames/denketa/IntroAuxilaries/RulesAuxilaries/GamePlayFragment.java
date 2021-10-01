package com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

public class GamePlayFragment extends Fragment implements View.OnClickListener {

    Dialog dialog;
    RelativeLayout frg_rlFalse;
    RelativeLayout frg_rlSepecify;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_game_play, container, false);

        bindViews(frg);

        return frg;
    }

    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new GamePlayFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }
    private void bindViews(View frg) {


        frg_rlFalse = frg.findViewById(R.id.frg_rlFalse);
        frg_rlSepecify = frg.findViewById(R.id.frg_rlSepecify);

        frg_rlFalse.setOnClickListener(this);
        frg_rlSepecify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_rlFalse:
                onClickFalse();
                break;
            case R.id.frg_rlSepecify:

                onClickSpecify();
                break;

        }


    }


    public void onClickSpecify() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_item_specify);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LinearLayout llOkay = dialog.findViewById(R.id.llParent);

        llOkay.setOnClickListener(this);
        llOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public void onClickFalse() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_item_false);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout llOkay = dialog.findViewById(R.id.llParent);
        TextView txv_title = dialog.findViewById(R.id.txv_title);

        String str_firstText = getColoredSpanned(getContext().getString(R.string.frg_popup), "#1A5876");
        String str_coloredText = getColoredSpanned("Does\n he play an instrument?", "#9D3630");
        txv_title.setText(Html.fromHtml(str_firstText + " " + str_coloredText + "”"));

        llOkay.setOnClickListener(this);
        llOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
