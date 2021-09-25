package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroAuxilaries.DModelDictionary;
import com.armoomragames.denketa.IntroAuxilaries.DictionaryListAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

public class FaqFragment extends Fragment implements View.OnClickListener {


    ListView lsvFaq;
    ArrayList<DModelDictionary> lst_Funds;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_faq, container, false);


        bindViews(frg);

        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvPlay.startAnimation(shake); // starts animation
        init();

        populateData();
        return frg;
    }

    IBadgeUpdateListener mBadgeUpdateListener;

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);

        }

    }

    void init() {
        setToolbar();
        lst_Funds = new ArrayList<>();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }


    private void bindViews(View frg) {

        lsvFaq = frg.findViewById(R.id.frg_lsv_faq);

    }


    private void populateData() {
        for (int i = 0; i < 18; i++) {
            lst_Funds.add(new DModelDictionary("Word " + i, "   " + "Meanining"));

        }
        FaqListAdapter faqListAdapter = new FaqListAdapter(getContext(), (eventId, position) -> {


        }, lst_Funds);

        lsvFaq.setAdapter(faqListAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_settings_rlMyAccount:


                break;
        }
    }


}
