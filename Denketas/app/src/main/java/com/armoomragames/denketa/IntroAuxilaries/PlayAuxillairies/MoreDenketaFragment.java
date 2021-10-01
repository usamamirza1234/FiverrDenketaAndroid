package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All__Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All__Danektas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;

import java.util.ArrayList;

public class MoreDenketaFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener  {

    ListView rcvMyDenekta;

    MoreDenketaLsvAdapter adapter;
    Intro_WebHit_Get_All__Danektas intro_webHit_get_all__danektas;
    boolean isAlreadyFetching = false;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    private boolean isLoadingMore = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_more_denketa, container, false);


        init();
        bindViewss(frg);
        requestDenketa();
//        populatePopulationList();
        return frg;
    }

    private void init()
    {
        lst_MyDenketa = new ArrayList<>();

        nFirstVisibleItem = 0;
        nVisibleItemCount = 0;
        nTotalItemCount = 0;
        nScrollState = 0;
        nErrorMsgShown = 0;
        isLoadingMore = false;

        intro_webHit_get_all__danektas = new Intro_WebHit_Get_All__Danektas();
        Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }

    private void bindViewss(View frg)
    {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);

    }

    public void requestDenketa() {
        isAlreadyFetching = true;


        Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_All__Danektas.responseObject = null;
        intro_webHit_get_all__danektas.getCategory(this,
                Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex);
    }





    private void populateAllDanektasData(boolean isSuccess, String strMsg) {
//        srlHome.setRefreshing(false);
//        llListItemLoader.setVisibility(View.GONE);
//        if (progressDilogue != null) {
//            progressDilogue.stopiOSLoader();
//        }

        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {

                if (Intro_WebHit_Get_All__Danektas.responseObject != null &&
                        Intro_WebHit_Get_All__Danektas.responseObject.getData() != null &&
                        Intro_WebHit_Get_All__Danektas.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_All__Danektas.responseObject.getData().getListing().size() > 0) {

//                    txvNoData.setVisibility(View.GONE);

                    for (int i = 0; i < Intro_WebHit_Get_All__Danektas.responseObject.getData().getListing().size(); i++) {

                        lst_MyDenketa.add(new DModel_MyDenketa(Intro_WebHit_Get_All__Danektas.responseObject.getData().getListing().get(i).getName(), Intro_WebHit_Get_All__Danektas.responseObject.getData().getListing().get(i).getImage()));


                    }


                    if (adapter == null) {
                        adapter = new MoreDenketaLsvAdapter(new IAdapterCallback() {
                            @Override
                            public void onAdapterEventFired(int eventId, int position) {
                                switch (eventId) {

                                }

                            }
                        }, getActivity(), lst_MyDenketa);
                        rcvMyDenekta.setAdapter(adapter);
                        rcvMyDenekta.setOnScrollListener(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        rcvMyDenekta.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex == 1) {
////                    lsvMedicines.setVisibility(View.GONE);
//                    txvNoData.setVisibility(View.VISIBLE);
////                    imvNoData.setVisibility(View.VISIBLE);
                    rcvMyDenekta.setOnScrollListener(null);
                }
            }
    }

    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String errorMsg) {
        isLoadingMore = false;
//        llListItemLoader.setVisibility(View.GONE);
//        if (progressDilogue != null) {
//            progressDilogue.stopiOSLoader();
//        }

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_All__Danektas.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
//                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT, false);
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



    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg) {
        updateDenketaList(isSuccess, isCompleted, strMsg);
    }

    @Override
    public void onWebInitialException(Exception ex) {
        populateAllDanektasData(false, AppConfig.getInstance().getNetworkExceptionMessage(ex.toString()));
    }

    @Override
    public void onWebSuccessiveException(Exception ex) {
        updateDenketaList(false, false, AppConfig.getInstance().getNetworkExceptionMessage(ex.getMessage()));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.nScrollState = i;
        this.isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.nFirstVisibleItem = i;
        this.nVisibleItemCount = i1;
        this.nTotalItemCount = i2;
    }

    private void isScrollCompleted() {
        if (this.nVisibleItemCount > 0 && this.nScrollState == SCROLL_STATE_IDLE &&
                this.nTotalItemCount == (nFirstVisibleItem + nVisibleItemCount)) {
            /*** In this way I detect if there's been a scroll which has completed ***/
            if (!isLoadingMore && !Intro_WebHit_Get_All__Danektas.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                intro_webHit_get_all__danektas.getCategory(this,
                        Intro_WebHit_Get_All__Danektas.mPaginationInfo.currIndex + 1);
            }
        }
    }
}
