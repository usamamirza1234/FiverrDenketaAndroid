package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.MyDenketaRcvAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class DictionaryFragment extends Fragment implements View.OnClickListener {
    ArrayList<DModelDictionary> lst_Funds;
    RecyclerView lsvDictionary;
    IBadgeUpdateListener mBadgeUpdateListener;

    ImageView imvSearch;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_dictionary, container, false);


        init();
        bindViews(frg);
//        populateData();
        populatePopulationList();
        return frg;
    }

    private void bindViews(View frg) {


        imvSearch = frg.findViewById(R.id.imv_search);



        lsvDictionary = frg.findViewById(R.id.frg_lsv_dictionary);
    }

//    private void populateData() {
//        for (int i = 0; i < 18; i++) {
//            lst_Funds.add(new DModelDictionary("Word " + i,"   "+"Meanining"));
//
//        }
//        DictionaryListAdapter listAdapter = new DictionaryListAdapter(getContext(), (eventId, position) -> {
//
//
//        }, lst_Funds);
//
//        lsvDictionary.setAdapter(listAdapter);
//    }


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


    private void populatePopulationList() {

        DictionaryListAdapter dictionaryListAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (dictionaryListAdapter == null) {
            for (int i = 0; i < 18; i++) {
                lst_Funds.add(new DModelDictionary("Word " + i, "   " + "Meanining"));

            }

            dictionaryListAdapter = new DictionaryListAdapter(getActivity(), lst_Funds, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            lsvDictionary.setLayoutManager(linearLayoutManager);
            lsvDictionary.setAdapter(dictionaryListAdapter);

        } else {
            dictionaryListAdapter.notifyDataSetChanged();
        }
    }
}
