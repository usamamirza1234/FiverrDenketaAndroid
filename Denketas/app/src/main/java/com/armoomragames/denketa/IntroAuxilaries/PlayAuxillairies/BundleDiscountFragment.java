package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;

public class BundleDiscountFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlApply;
    EditText edtGameCredits;
    TextView txvTotal, txvSubTotal, txvDiscount;
    TextView txvHowDoes, txvGameCredits;
    int number;
    int sub_total;
    double discount;
    double total;
    LinearLayout llBundle;
    Dialog progressDialog = null; // Context, this, etc.
    private String strID = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_bundle_discount, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            strID = bundle.getString("danetkaID");
        }
    }

    private void bindViewss(View frg) {
        rlApply = frg.findViewById(R.id.apply);
        llBundle = frg.findViewById(R.id.bund);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);


        txvDiscount = frg.findViewById(R.id.frg_bundle_discount_txvdiscount);
        edtGameCredits = frg.findViewById(R.id.frg_bundle_discount_edt_game_credit);
        txvSubTotal = frg.findViewById(R.id.frg_bundle_discount_txvsubTotal);
        txvTotal = frg.findViewById(R.id.frg_bundle_discount_txvTotal);
        txvHowDoes = frg.findViewById(R.id.txvHowDoes);
        txvGameCredits = frg.findViewById(R.id.txvGameCredits);

        txvGameCredits.setOnClickListener(this);
        txvHowDoes.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlApply.setOnClickListener(this);
        llBundle.setOnClickListener(this);
        editTextWatchers();
    }

    private void editTextWatchers() {

        edtGameCredits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtGameCredits.setText("");
                }
                if (!s.toString().equalsIgnoreCase("")) {
                    number = Integer.parseInt(s.toString());
                    if (number > 50) {
                        edtGameCredits.setText("");
                        CustomToast.showToastMessage(getActivity(), "You entered Game Credit more than 50", Toast.LENGTH_SHORT);
                    }
                    if (number == 0) {
                        edtGameCredits.setText("");
                        CustomToast.showToastMessage(getActivity(), "You entered Game Credit less than 1", Toast.LENGTH_SHORT);
                    }

                    sub_total = number;
                    total = number * (1 - (0.01 * number));
                    discount = sub_total - total;


                    txvDiscount.setText(Math.round(discount) + "€");
                    txvSubTotal.setText(sub_total + "€");
                    txvTotal.setText(Math.round(total) + "€");

//                    Toast.makeText(getContext(), "text there afterTextChanged " + (number * (1 - (0.01 * number))), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.apply:
//                navToPayentDisapprovedFragment();
//                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;

            case R.id.bund:
                if (!edtGameCredits.getText().toString().equalsIgnoreCase(""))
                    navToPaymentDetailFragment();
                else
                    CustomToast.showToastMessage(getActivity(), "Please enter Game Credits to buy", Toast.LENGTH_SHORT);
                break;

            case R.id.txvHowDoes:
                openDialog(true);
                break;
            case R.id.txvGameCredits:
                openDialog(false);
                break;
        }
    }

    private void navToPaymentDetailFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_danetkaID", "0");
        bundle.putBoolean("key_is_coming_from_bundle", true);
        bundle.putString("key_danetka_sub_total", sub_total + "");
        bundle.putString("key_danetka_total", String.format("%.2f", total) + "");
        bundle.putString("key_danetka_number", number + "");
        bundle.putString("key_danetka_discount", discount + "");
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.hide(this);
        ft.commit();
    }

    public void openDialog(boolean isDetail) {


        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_text);
        TextView txvDetails = progressDialog.findViewById(R.id.dailog_txvDetails);
        if (isDetail) {
            txvDetails.setText("For each danetka get an \n additional 1% discount up to \n a maximum of 50%. \n (E.g. 20 danetkas - 20% discount)\n" +
                    "------------------------\nThis is automatically \n applied to your purchase. ");
        } else {
            txvDetails.setText("One Game Credit allows\n you to unlock one\n danetka of your choice.");
        }

        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
     
        progressDialog.show();
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
