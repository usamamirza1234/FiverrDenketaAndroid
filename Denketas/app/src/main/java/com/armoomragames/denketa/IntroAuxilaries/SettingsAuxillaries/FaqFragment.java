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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroAuxilaries.DModelDictionary;
import com.armoomragames.denketa.IntroAuxilaries.DictionaryRCVAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class FaqFragment extends Fragment implements View.OnClickListener {


    RecyclerView lsvFaq;
    ArrayList<DModelDictionary> lst_Funds;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_faq, container, false);


        init();
        bindViews(frg);
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
        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvPlay.startAnimation(shake); // starts animation
    }


    private void populateData() {

        FaqListAdapter faqListAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (faqListAdapter == null) {
            for (int i = 0; i < 18; i++) {
                lst_Funds.add(new DModelDictionary("Word " + i, "   " + "Meanining"));

            }

            faqListAdapter = new FaqListAdapter(getActivity(), lst_Funds, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            lsvFaq.setLayoutManager(linearLayoutManager);
            lsvFaq.setAdapter(faqListAdapter);

        } else {
            faqListAdapter.notifyDataSetChanged();
        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_settings_rlMyAccount:


                break;
        }
    }


}
