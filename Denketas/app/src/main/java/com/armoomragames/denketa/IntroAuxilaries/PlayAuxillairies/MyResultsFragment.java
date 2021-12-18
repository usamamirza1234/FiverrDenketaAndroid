package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.ResultsAdapter;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.ForgotPasswordFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class MyResultsFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    Bundle bundle;
    String danetka_name = "";
    TextView txvDanetkaName;
    LinearLayout llAddResult, llNoResults;
    ImageView imvAddResults;


    ResultsAdapter resultsAdapter = null;
    RecyclerView rcvResults;
    ArrayList<DModelResults> lst_results;

    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_results, container, false);

        init();
        bindViewss(frg);

        setStates();
        populatePopulationList();

        return frg;
    }

    private void setStates() {
        txvDanetkaName.setText(danetka_name);

    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
        }

        lst_results = new ArrayList<>();
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);


        rcvResults = frg.findViewById(R.id.frg_lsv_results);
        llNoResults = frg.findViewById(R.id.frg_my_results_ll_NoResults);

        imvAddResults = frg.findViewById(R.id.frg_my_results_imv_AddResults);
        llAddResult = frg.findViewById(R.id.frg_my_results_ll_AddResults);


        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        imvAddResults.setOnClickListener(this);


    }

    private void populatePopulationList() {

        lst_results.add(new DModelResults("Usama", "Mirza", "10", "10", "10"));
        lst_results.add(new DModelResults("Ali", "Usama", "10", "10", "10"));
        lst_results.add(new DModelResults("Mirza", "Usama", "10", "10", "10"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (resultsAdapter == null) {
            resultsAdapter = new ResultsAdapter(getActivity(), lst_results, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:

                        break;
                }
            });


            rcvResults.setLayoutManager(linearLayoutManager);
            rcvResults.setAdapter(resultsAdapter);

        } else {
            resultsAdapter.notifyDataSetChanged();
        }
        if (lst_results.size() <= 0) {
            llNoResults.setVisibility(View.VISIBLE);
        }

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
            case R.id.frg_my_results_imv_AddResults:
                navToAddResultFragment();
                break;


        }
    }

    private void navToAddResultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AddResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", txvDanetkaName.getText().toString());
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_AddResultFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_AddResultFragment);
        ft.hide(this);
        ft.commit();
    }
}
