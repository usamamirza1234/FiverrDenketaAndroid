package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class DictionaryFragment extends Fragment implements View.OnClickListener {
    ArrayList<DModelDictionary> lst_Funds;
    RecyclerView lsvDictionary;
    IBadgeUpdateListener mBadgeUpdateListener;
    DictionaryRCVAdapter dictionaryRCVAdapter = null;
    ImageView imvSearch;
    EditText edt_Search;
    RelativeLayout rlToolbar, rlBack, rlCross;

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
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        imvSearch = frg.findViewById(R.id.imv_search);
        lsvDictionary = frg.findViewById(R.id.frg_lsv_dictionary);
        edt_Search = frg.findViewById(R.id.edt_Search);

        imvSearch.setOnClickListener(this);


        edt_Search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(edt_Search.getText().toString());
            }
        });
    }




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
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
            case R.id.imv_search:
                filter(edt_Search.getText().toString());
                break;
        }
    }


    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (dictionaryRCVAdapter == null) {
            for (int i = 0; i < 18; i++) {
                lst_Funds.add(new DModelDictionary("Word " + i, "   " + "Meanining"));

            }

            dictionaryRCVAdapter = new DictionaryRCVAdapter(getActivity(), lst_Funds, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            lsvDictionary.setLayoutManager(linearLayoutManager);
            lsvDictionary.setAdapter(dictionaryRCVAdapter);

        } else {
            dictionaryRCVAdapter.notifyDataSetChanged();
        }
    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DModelDictionary> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DModelDictionary item : lst_Funds) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getWord().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found for word" + text, Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            dictionaryRCVAdapter.filterList(filteredlist);
//            Toast.makeText(getContext(), "Data Found.." + text, Toast.LENGTH_SHORT).show();
        }
    }
}
