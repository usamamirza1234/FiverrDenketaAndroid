package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

public class PaymentDetailFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlPaypal;
    Bundle bundle;

    String danetkaID = "";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {

            danetkaID = bundle.getString("key_danetka_danetkaID");
        }
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlPaypal = frg.findViewById(R.id.rlPaypal);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlPaypal.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;            case R.id.rlPaypal:
                                            ((IntroActivity)getActivity()). onBuyPressed(danetkaID);


                break;
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
                    navToPayentApprovedFragment();
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
    //region progdialog
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
    //endregion


    private void navToPayentDisapprovedFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFailedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPayentApprovedFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentApprovedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.hide(this);
        ft.commit();
    }

}
