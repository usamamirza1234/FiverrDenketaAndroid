package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Contactus;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Results;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddResultsFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    Bundle bundle;
    String danetka_name = "";
    String danetka_id = "";
    TextView txvDanetkaName;
    LinearLayout llDetails, llNewResults, llEditdetails ;

    TextView txvUsed, txvMaster, txvInvestigator, txvTime, txvDate , edtMaster;
    EditText edtUsed, edtInvestigator, edtTime;
    LinearLayout llSaveResult, llSavedResults;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_add_results, container, false);

        init();
        bindViewss(frg);

        setStates();

        return frg;
    }

    private void setStates() {
        txvDanetkaName.setText(danetka_name);
        edtMaster.setText(AppConfig.getInstance().mUser.getName());
        llDetails.setVisibility(View.GONE);
        llEditdetails.setVisibility(View.VISIBLE);

        llNewResults.setVisibility(View.VISIBLE);
    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
            danetka_id = bundle.getString("key_danetka_id");
        }
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);

        llDetails = frg.findViewById(R.id.frg_my_results_ll_details);

        llNewResults = frg.findViewById(R.id.frg_my_results_ll_NewResults);
        llEditdetails = frg.findViewById(R.id.frg_my_results_ll_Editdetails);



        llSavedResults = frg.findViewById(R.id.frg_my_results_ll_saved_results);
        llSaveResult = frg.findViewById(R.id.frg_my_results_ll_save_results);


        txvUsed = frg.findViewById(R.id.frg_my_results_txv_used);
        txvDate = frg.findViewById(R.id.frg_my_results_txv_date);
        txvTime = frg.findViewById(R.id.frg_my_results_txv_time);
        txvMaster = frg.findViewById(R.id.frg_my_results_txv_master);
        txvInvestigator = frg.findViewById(R.id.frg_my_results_txv_invest);


        edtUsed = frg.findViewById(R.id.frg_my_results_edt_used);
        edtTime = frg.findViewById(R.id.frg_my_results_edt_time);
        edtMaster = frg.findViewById(R.id.frg_my_results_edt_master);
        edtInvestigator = frg.findViewById(R.id.frg_my_results_edt_invest);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        llSaveResult.setOnClickListener(this);
        llEditdetails.setOnClickListener(this);

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

            case R.id.frg_my_results_ll_Editdetails:


                llNewResults.setVisibility(View.VISIBLE);
                llSavedResults.setVisibility(View.GONE);
                llSaveResult.setVisibility(View.VISIBLE);
                llDetails.setVisibility(View.GONE);
                break;


            case R.id.frg_my_results_ll_save_results:

                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!edtInvestigator.getText().toString().equals("") && !edtMaster.getText().toString().equals("")
                        && !edtTime.getText().toString().equals("")
                        && !edtUsed.getText().toString().equals("")) {

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = df.format(c);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("investigatorName", edtInvestigator.getText().toString());
                    jsonObject.addProperty("riglettosUsed", edtUsed.getText().toString());
                    jsonObject.addProperty("time", edtTime.getText().toString());
                    jsonObject.addProperty("date", formattedDate.toString());
                    jsonObject.addProperty("masterId", AppConfig.getInstance().mUser.getUser_Id());
                    jsonObject.addProperty("danetkaId", danetka_id);
                    requestContactUs(jsonObject.toString());



                } else
                    CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);
                break;
        }
    }



    private void requestContactUs(String _signUpEntity) {
        llSavedResults.setVisibility(View.VISIBLE);
        llSaveResult.setVisibility(View.GONE);

        Intro_WebHit_Post_Results intro_webHit_post_results = new Intro_WebHit_Post_Results();
        intro_webHit_post_results.postResults(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(),"Success! Result Added", Toast.LENGTH_SHORT);
                    txvMaster.setText(edtMaster.getText().toString());
                    txvInvestigator.setText(edtInvestigator.getText().toString());
                    txvUsed.setText(edtUsed.getText().toString());
                    txvTime.setText(edtTime.getText().toString());
                    txvDate.setText(edtTime.getText().toString());
                    llEditdetails.setVisibility(View.VISIBLE);
                    llDetails.setVisibility(View.VISIBLE);
                    llNewResults.setVisibility(View.GONE);
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
}
