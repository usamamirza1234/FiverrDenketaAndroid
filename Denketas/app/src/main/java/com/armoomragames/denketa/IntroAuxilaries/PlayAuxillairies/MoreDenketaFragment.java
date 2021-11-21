package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomAlertConfirmationInterface;
import com.armoomragames.denketa.Utils.CustomAlertDialog;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MoreDenketaFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener  {
    RelativeLayout rlToolbar, rlBack, rlCross;
    ListView rcvMyDenekta;
    String strID ="0";
    EditText edtSearch;
    ImageView imvSearch;
    CustomAlertDialog customAlertDialog;
    MoreDenketaLsvAdapter adapter;
    Intro_WebHit_Get_All_Danektas intro_webHit_get_all__danektas;
    boolean isAlreadyFetching = false;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    private boolean isLoadingMore = false;


    public static final String clientKey = "AQxyBWkhclOXBj9jlkr3eV_F9PQ2O6yBD5f8i1oO2fJNQ5Xy_Ir6N45881igN7lyfIPvxr59JSGnH0B1";
    public static final int PAYPAL_REQUEST_CODE = 123;

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment. When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);

    private void getPayment(String amountEdt) {

        // Getting the amount from editText
        String amount = amountEdt;

        // Creating a paypal payment on below line.
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR", "Danekta Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("danetkasId", strID.toString());
            requestAddUserDanetkas(jsonObject.toString());
            strID="0";

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");

                        CustomToast.showToastMessage(getActivity(),"Payment " + state + "\n with payment id is " + payID,Toast.LENGTH_LONG);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void requestAddUserDanetkas(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddUserDanetkas intro_webHit_post_addUserDanetkas = new Intro_WebHit_Post_AddUserDanetkas();
        intro_webHit_post_addUserDanetkas.postAddUserDanetkas(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();

                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_more_denketa, container, false);


        init();
        bindViewss(frg);
        requestDenketa();
//        populatePopulationList();


        return frg;
    }

    private Dialog progressDialog;

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


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

        intro_webHit_get_all__danektas = new Intro_WebHit_Get_All_Danektas();
        Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }

    private void bindViewss(View frg)
    {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);
        edtSearch = frg.findViewById(R.id.frg_more_dankta_edt_search);
        imvSearch = frg.findViewById(R.id.frg_more_dankta_imv_search);


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

    public void requestDenketa() {
        isAlreadyFetching = true;
        showProgDialog();

        Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_All_Danektas.responseObject = null;
        intro_webHit_get_all__danektas.getCategory(this,
                Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex);
    }





    private void populateAllDanektasData(boolean isSuccess, String strMsg) {

        dismissProgDialog();

        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {

                if (Intro_WebHit_Get_All_Danektas.responseObject != null &&
                        Intro_WebHit_Get_All_Danektas.responseObject.getData() != null &&
                        Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().size() > 0) {

//                    txvNoData.setVisibility(View.GONE);

                    for (int i = 0; i < Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().size(); i++) {

                        lst_MyDenketa.add(
                                new DModel_MyDenketa(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(i).getName(),
                                Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(i).getId()+"",
                                Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(i).getImage()));


                    }


                    if (adapter == null) {
                        adapter = new MoreDenketaLsvAdapter(new IAdapterCallback() {
                            @Override
                            public void onAdapterEventFired(int eventId, int position) {
                                switch (eventId) {
                                    case EVENT_A:
                                        if (AppConfig.getInstance().mUser.isLoggedIn())
                                        {
                                         strID =   lst_MyDenketa.get(position).getStrId();
                                            showInternetConnectionStableMessage(getContext(),"This is random dialogue to process payment flow");
                                        }
                                        else
                                        {
                                            CustomToast.showToastMessage(getActivity(),"Sign in/ Sign Up to get this Danetka", Toast.LENGTH_LONG);
                                        }
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
                    if (Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        rcvMyDenekta.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex == 1) {
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
        dismissProgDialog();

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_All_Danektas.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
//                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT, false);
            }
    }



    public void showInternetConnectionStableMessage(Context context, String _errorMsg)
    {
        customAlertDialog = new CustomAlertDialog(context, "", _errorMsg, "Allow", "Deny", true, new CustomAlertConfirmationInterface() {
            @Override
            public void callConfirmationDialogPositive() {

                getPayment("213");
                customAlertDialog.dismiss();
            }

            @Override
            public void callConfirmationDialogNegative() {


                customAlertDialog.dismiss();
            }
        });
        customAlertDialog.show();

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
            if (!isLoadingMore && !Intro_WebHit_Get_All_Danektas.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                intro_webHit_get_all__danektas.getCategory(this,
                        Intro_WebHit_Get_All_Danektas.mPaginationInfo.currIndex + 1);
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
