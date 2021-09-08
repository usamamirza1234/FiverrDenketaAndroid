package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.CustomAlertConfirmationInterface;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class MyDenketaFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcvMyDenekta;

    ArrayList<DModel_MyDenketa> lst_MyDenketa;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_denketa, container, false);

        init();
        bindViewss(frg);
        populatePopulationList();
        return frg;
    }

    private void init() {
        lst_MyDenketa = new ArrayList<>();
    }

    private void bindViewss(View frg) {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);

    }




    private void populatePopulationList() {

        MyDenketaRcvAdapter myDenketaRcvAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (myDenketaRcvAdapter == null) {

            lst_MyDenketa.add(new DModel_MyDenketa("First", ""));
            lst_MyDenketa.add(new DModel_MyDenketa("Second", ""));
            myDenketaRcvAdapter = new MyDenketaRcvAdapter(getActivity(), lst_MyDenketa, (eventId, position) -> {

                switch (eventId)
                {
                    case  EVENT_A:
                        ((IntroActivity)getActivity()).navToDenketaQuestionFragment();
                        break;
                }

            });


            rcvMyDenekta.setLayoutManager(linearLayoutManager);
            rcvMyDenekta.setAdapter(myDenketaRcvAdapter);

        } else {
            myDenketaRcvAdapter.notifyDataSetChanged();
        }
    }


    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new MyDenketaFragment();
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
