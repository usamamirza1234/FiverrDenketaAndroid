package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Contactus;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Results;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RateAppFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String[] paths = {"0", "1", "2", "3", "4", "5"};
    Spinner spinner;
    RelativeLayout rlToolbar, rlBack, rlCross,rl_leave_coment,rl_comnt_sent;
    String danetka_id = "";
    TextView edtMaster,edtTime;
    EditText edtInvestigator, frg_make_edtAnswer;
    TextView edtUsed;
    LinearLayout llSaveResult, llSavedResults,lltxv_enter_coment;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    private Dialog progressDialog;
    ImageView imv_send;
    String formattedDate ="";
    String StartTimeDate ="";
    long hours, minutes, seconds;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_rate_app, container, false);
        init();
        bindViews(frg);


        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            formattedDate = df.format(c);
        }
        catch (Exception e){}


        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
            Date dateStartTimeDate = format.parse(StartTimeDate);
            Date dateformattedDate = format.parse(formattedDate);
            long diff = dateformattedDate.getTime() - dateStartTimeDate.getTime();

            long timeInSeconds = diff / 1000;

            hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - (hours * 3600);
            minutes = timeInSeconds / 60;
            timeInSeconds = timeInSeconds - (minutes * 60);
            seconds = timeInSeconds;

            edtTime.setText(""+minutes);
            Toast.makeText(getContext(),"TIme " + seconds,Toast.LENGTH_SHORT);
        } catch (ParseException e) {

            Toast.makeText(getContext(),"TIme " + e.getMessage(),Toast.LENGTH_SHORT);
            e.printStackTrace();
        }

        return frg;
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);
        }

    }

    private void init() {
        setToolbar();
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_id = bundle.getString("key_danetka_id");
            StartTimeDate = bundle.getString("key_danetka_formattedDate");
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    private void bindViews(View frg) {

        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        frg_make_edtAnswer = frg.findViewById(R.id.frg_make_edtAnswer);
        rl_comnt_sent = frg.findViewById(R.id.rl_comnt_sent);

        llSavedResults = frg.findViewById(R.id.frg_my_results_ll_saved_results);
        llSaveResult = frg.findViewById(R.id.frg_my_results_ll_save_results);
        rl_leave_coment = frg.findViewById(R.id.rl_leave_coment);

        edtTime = frg.findViewById(R.id.frg_my_results_edt_time);
        edtMaster = frg.findViewById(R.id.frg_my_results_edt_master);
        edtInvestigator = frg.findViewById(R.id.frg_my_results_edt_invest);
        spinner = frg.findViewById(R.id.frg_signup_cmplt_spinr_gender);
        imv_send = frg.findViewById(R.id.imv_send);

        edtUsed = frg.findViewById(R.id.frg_signup_cmplt_spinr_txvGend);
        lltxv_enter_coment = frg.findViewById(R.id.txv_enter_coment);

        edtMaster.setText(AppConfig.getInstance().mUser.getName());

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        imv_send.setOnClickListener(this);
        llSaveResult.setOnClickListener(this);
        rl_leave_coment.setOnClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    int selected=0;
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selected = position;
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
//            case R.id.imv_send:
//                lltxv_enter_coment.setVisibility(View.GONE);
//                break;


            case R.id.frg_my_results_ll_save_results:

                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!edtInvestigator.getText().toString().equals("") && !edtMaster.getText().toString().equals("")
                        ) {

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = df.format(c);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("investigatorName", edtInvestigator.getText().toString());
                    jsonObject.addProperty("riglettosUsed", selected+"");
                    jsonObject.addProperty("time", minutes+"");
                    jsonObject.addProperty("date", formattedDate);
                    jsonObject.addProperty("masterId", AppConfig.getInstance().mUser.getUser_Id());
                    jsonObject.addProperty("danetkaId", danetka_id);
                    requestContactUs(jsonObject.toString());


                } else
                    CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);
                break;
            case R.id.rl_leave_coment:

                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!frg_make_edtAnswer.getText().toString().equals(""))
                {
                    showProgDialog();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("email", AppConfig.getInstance().mUser.getEmail());
                    jsonObject.addProperty("text","USERS COMMENT ==> : "+ frg_make_edtAnswer.getText().toString());
                    requestComment(jsonObject.toString());
                }
                else CustomToast.showToastMessage(getActivity(),"Please enter your comments", Toast.LENGTH_LONG);
                break;
        }
    }

    private void requestComment(String _signUpEntity) {

        Intro_WebHit_Post_Contactus intro_webHit_post_contactus = new Intro_WebHit_Post_Contactus();
        intro_webHit_post_contactus.postContact(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    rl_comnt_sent.setVisibility(View.VISIBLE);
                    rl_leave_coment.setVisibility(View.GONE);
                    CustomToast.showToastMessage(getActivity(),"Comment sent!", Toast.LENGTH_SHORT);
                    frg_make_edtAnswer.setText("");
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

    private void requestContactUs(String _signUpEntity) {
        llSavedResults.setVisibility(View.VISIBLE);
        llSaveResult.setVisibility(View.GONE);
        showProgDialog();
        Intro_WebHit_Post_Results intro_webHit_post_results = new Intro_WebHit_Post_Results();
        intro_webHit_post_results.postResults(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Success! Result Added", Toast.LENGTH_SHORT);

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

}
