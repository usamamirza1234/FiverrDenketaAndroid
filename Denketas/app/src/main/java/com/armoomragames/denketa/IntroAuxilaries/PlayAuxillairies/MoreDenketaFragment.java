package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.R;

import java.util.ArrayList;

public class MoreDenketaFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcvMyDenekta;

    ArrayList <DModel_MyDenketa> lst_MyDenketa;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_more_denketa, container, false);


        init();
        bindViewss(frg);
        populatePopulationList();
        return frg;
    }

    private void init()
    {
        lst_MyDenketa = new ArrayList<>();
    }

    private void bindViewss(View frg)
    {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);

    }


    private void populatePopulationList() {

        MoreDenketaRcvAdapter moreDenketaRcvAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (moreDenketaRcvAdapter == null) {

            lst_MyDenketa.add(new DModel_MyDenketa("More First",""));
            lst_MyDenketa.add(new DModel_MyDenketa("More Second",""));
            moreDenketaRcvAdapter = new MoreDenketaRcvAdapter(getActivity(), lst_MyDenketa, (eventId, position) -> {


            });


            rcvMyDenekta.setLayoutManager(linearLayoutManager);
            rcvMyDenekta.setAdapter(moreDenketaRcvAdapter);

        } else {
            moreDenketaRcvAdapter.notifyDataSetChanged();
        }
    }


    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new MoreDenketaFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }
}
