package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

public class DictionaryFragment extends Fragment implements View.OnClickListener {
    ArrayList<DModelDictionary> lst_Funds;
    ListView lsvDictionary;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_dictionary, container, false);


        init();
        bindViews(frg);
        populateData();
        return frg;
    }

    private void bindViews(View frg) {
        lsvDictionary = frg.findViewById(R.id.frg_lsv_dictionary);
    }

    private void populateData() {
        for (int i = 0; i < 18; i++) {
            lst_Funds.add(new DModelDictionary("Word " + i,"   "+"Meanining"));

        }
        DictionaryListAdapter listAdapter = new DictionaryListAdapter(getContext(), (eventId, position) -> {


        }, lst_Funds);

        lsvDictionary.setAdapter(listAdapter);
    }

    IBadgeUpdateListener mBadgeUpdateListener;

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN);

        }

    }

    void init() {
        lst_Funds = new ArrayList<>();
        setToolbar();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }
}
