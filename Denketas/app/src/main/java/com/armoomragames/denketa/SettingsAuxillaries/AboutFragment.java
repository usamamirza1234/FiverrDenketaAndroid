package com.armoomragames.denketa.SettingsAuxillaries;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class AboutFragment extends Fragment implements View.OnClickListener {

    TextView txvCreatorDesc;
    RelativeLayout rlBg_overlay;
    LinearLayout llBg;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_about, container, false);


        bindViews(frg);


        return frg;
    }

    private void bindViews(View frg) {
        txvCreatorDesc = frg.findViewById(R.id.frg_about_txv_creator_desc);

        rlBg_overlay = frg.findViewById(R.id.rlBg_overlay);
        llBg = frg.findViewById(R.id.llBg);


        ViewTreeObserver vto = llBg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    llBg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    llBg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    rlBg_overlay.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    rlBg_overlay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                int width = rlBg_overlay.getMeasuredWidth();
                int height =llBg.getMeasuredHeight();

                rlBg_overlay.setLayoutParams(new RelativeLayout.LayoutParams(width,height ));

                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams)rlBg_overlay.getLayoutParams();
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                rlBg_overlay.setLayoutParams(layoutParams);




            }
        });




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txvCreatorDesc.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


}
