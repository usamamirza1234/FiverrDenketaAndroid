package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Free_Danektas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyDenketaFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener {

    MyDenketaLsvAdapter adapter;
    ListView rcvMyDenekta;
    Dialog dialog;
    Intro_WebHit_Get_Free_Danektas intro_webHit_get_all_user_danektas;
    boolean isAlreadyFetching = false;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    private boolean isLoadingMore = false;





    EditText edtSearch;
    ImageView imvSearch;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_denketa, container, false);

        init();
        bindViewss(frg);
        requestDenketa();

        return frg;
    }



    private void init() {
        lst_MyDenketa = new ArrayList<>();
        nFirstVisibleItem = 0;
        nVisibleItemCount = 0;
        nTotalItemCount = 0;
        nScrollState = 0;
        nErrorMsgShown = 0;
        isLoadingMore = false;

        intro_webHit_get_all_user_danektas = new Intro_WebHit_Get_Free_Danektas();
        Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }


    public void requestDenketa() {
        isAlreadyFetching = true;

        showProgDialog();
        Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_Free_Danektas.responseObject = null;
        intro_webHit_get_all_user_danektas.getMyDanekta(this,
                Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex);
    }

    private Dialog progressDialog;

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_progress);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = (ImageView) progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    private void populateAllDanektasData(boolean isSuccess, String strMsg) {


        dismissProgDialog();

        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {
                if (Intro_WebHit_Get_Free_Danektas.responseObject != null &&
                        Intro_WebHit_Get_Free_Danektas.responseObject.getData() != null &&
                        Intro_WebHit_Get_Free_Danektas.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_Free_Danektas.responseObject.getData().getListing().size() > 0) {

//                    txvNoData.setVisibility(View.GONE);

                    for (int i = 0; i < Intro_WebHit_Get_Free_Danektas.responseObject.getData().getListing().size(); i++) {

                        lst_MyDenketa.add(new DModel_MyDenketa(Intro_WebHit_Get_Free_Danektas.responseObject.getData().getListing().get(i).getName(), Intro_WebHit_Get_Free_Danektas.responseObject.getData().getListing().get(i).getImage()));


                    }

                    if (adapter == null) {
                        adapter = new MyDenketaLsvAdapter(new IAdapterCallback() {
                            @Override
                            public void onAdapterEventFired(int eventId, int position) {
                                switch (eventId) {
                                    case EVENT_A:
                                        if (AppConfig.getInstance().mUser.isLoggedIn())
                                        {
                                            onClickDenketaItem(position);
                                        }
                                        else
                                        {
                                            CustomToast.showToastMessage(getActivity(),"Sign in / Sign Up to PLAY!", Toast.LENGTH_LONG);
                                        }
                                        break;




                                    case EVENT_B:
                                        ((IntroActivity) getActivity()).navToMyResultsFragment();

                                        break;
                                }

                            }
                        }, getActivity(), lst_MyDenketa);
                        rcvMyDenekta.setAdapter(adapter);
                        rcvMyDenekta.setOnScrollListener(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        rcvMyDenekta.setOnScrollListener(null);
                    }
                }
            }  else {
                if (Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex == 1) {
////                    lsvMedicines.setVisibility(View.GONE);
//                    txvNoData.setVisibility(View.VISIBLE);
////                    imvNoData.setVisibility(View.VISIBLE);
                    rcvMyDenekta.setOnScrollListener(null);
                }
            }
    }

    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String errorMsg) {
        isLoadingMore = false;
        dismissProgDialog();
//        llListItemLoader.setVisibility(View.GONE);
//        if (progressDilogue != null) {
//            progressDilogue.stopiOSLoader();
//        }

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_Free_Danektas.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
//                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT, false);
            }
    }


    private void bindViewss(View frg) {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);
        edtSearch = frg.findViewById(R.id.frg_my_dankta_edt_search);
        imvSearch = frg.findViewById(R.id.frg_my_dankta_imv_search);


        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().toString().equalsIgnoreCase(" "))
                    edtSearch.setText("");
                if (!edtSearch.getText().toString().equalsIgnoreCase(""))
                    filter(edtSearch.getText().toString());


            }
        });
    }


    public void onClickDenketaItem(int position) {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_item_master_rules);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LinearLayout llOkay = dialog.findViewById(R.id.lay_item_rules_llOkay);
        llOkay.setOnClickListener(this);
        dialog.show();
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
            case R.id.lay_item_play_txvRules:

                dialog.dismiss();
                ((IntroActivity)getActivity()).navToRulesFragment();
                break;

            case R.id.lay_item_rules_llOkay:
                dialog.dismiss();
//                ((IntroActivity) getActivity()).navToDenketaQuestionFragment();
                break;


        }
    }


    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg) {
//        updateDenketaList(isSuccess, isCompleted, strMsg);
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
            if (!isLoadingMore && !Intro_WebHit_Get_Free_Danektas.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                intro_webHit_get_all_user_danektas.getMyDanekta(this,
                        Intro_WebHit_Get_Free_Danektas.mPaginationInfo.currIndex + 1);
            }
        }
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DModel_MyDenketa> filteredlist = new ArrayList<>();

//        filteredlistByDropDown.clear();
        // running a for loop to compare elements.
        for (DModel_MyDenketa item : lst_MyDenketa) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStrName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(getContext(), "No Data Found for word " + text, Toast.LENGTH_SHORT).show();
            adapter.filterList(filteredlist);
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
//            Toast.makeText(getContext(), "Data Found.." + text, Toast.LENGTH_SHORT).show();
        }
    }


}

