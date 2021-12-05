package com.armoomragames.denketa.IntroAuxilaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.bumptech.glide.Glide;

public class ContactFragment extends Fragment implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;
    EditText edtContact,edtEmail;
    private Dialog progressDialog;
    LinearLayout llContactSend;
    LinearLayout llContactSent;

    RelativeLayout rlToolbar, rlBack, rlCross;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_contact, container, false);


        bindViews(frg);

//        Animation shake;
//        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
//
//
//        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
//        txvPlay.startAnimation(shake); // starts animation

        init();
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

    void init() {
        setToolbar();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }


    private void bindViews(View frg)
    {
        llContactSend = frg.findViewById(R.id.frg_cont_llsend);
        llContactSent = frg.findViewById(R.id.frg_cont_llsent);
        edtContact=frg.findViewById(R.id.frg_cont_edtConact);
        edtEmail =frg.findViewById(R.id.frg_cont_edt_email);



        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        llContactSend.setOnClickListener(this);


//        edtContact.setScroller(new Scroller(getContext()));
//        edtContact.setMaxLines(1);
//        edtContact.setVerticalScrollBarEnabled(true);
//        edtContact.setMovementMethod(new ScrollingMovementMethod());
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
            case R.id.frg_cont_llsend:
                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!edtEmail.getText().toString().equals("") && !edtContact.getText().toString().equals(""))
                {
                    showProgDialog();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //do something
                            dismissProgDialog();
                            llContactSend.setVisibility(View.GONE);
                            llContactSent.setVisibility(View.VISIBLE);
                        }
                    }, 2000 );//time in milisecond
                }
                else CustomToast.showToastMessage(getActivity(),"Please fill all fields", Toast.LENGTH_LONG);
                break;
        }
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
