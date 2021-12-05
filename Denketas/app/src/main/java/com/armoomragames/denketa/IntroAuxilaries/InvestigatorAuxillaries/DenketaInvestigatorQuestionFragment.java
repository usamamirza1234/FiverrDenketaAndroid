package com.armoomragames.denketa.IntroAuxilaries.InvestigatorAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DenketaAnswerFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameResultsFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.bumptech.glide.Glide;

public class DenketaInvestigatorQuestionFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    LinearLayout llSeeAnswer; RelativeLayout rlMaster;
    LinearLayout llPaynow;
    LinearLayout llBundleDiscount;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    String danetka_name = "";
    String danetka_id = "";
    String danetka_danetkaID = "";
    String danetka_Image = "";
    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    TextView txvDanetkaName;
    ImageView img;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_investigator_question, container, false);

        init();
        bindViews(frg);
        setData();


        return frg;
    }

    private void setData() {
        txvDanetkaName.setText(danetka_name.toUpperCase());
        Log.d("LOG_AS", "Image: " + "http://18.118.228.171:2000/images/" + danetka_id);
        Log.d("LOG_AS", "isMoreDanetka: " + isMoreDanetka);
        Log.d("LOG_AS", "isInvestigator: " +  isInvestigator);
        danetka_Image="http://18.118.228.171:2000/images/" + danetka_id;
        Glide.with(getContext())
                .load(danetka_Image)
                .into(img);

        if (!isInvestigator) {
            if (isMoreDanetka)
            {
                llPaynow.setVisibility(View.VISIBLE);
                llBundleDiscount.setVisibility(View.VISIBLE);
                llSeeAnswer.setVisibility(View.GONE);
            }
            else {
                llPaynow.setVisibility(View.GONE);
                llBundleDiscount.setVisibility(View.GONE);
                llSeeAnswer.setVisibility(View.VISIBLE);
            }

        } else {
            llPaynow.setVisibility(View.VISIBLE);
            llBundleDiscount.setVisibility(View.VISIBLE);
            llSeeAnswer.setVisibility(View.VISIBLE);
        }
    }


    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
            danetka_id = bundle.getString("key_danetka_id");
            danetka_danetkaID = bundle.getString("key_danetka_danetkaID");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);

        }
    }


    private void bindViews(View frg) {

        llSeeAnswer = frg.findViewById(R.id.frg_denketa_question_llSeeAnswer);
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        llPaynow = frg.findViewById(R.id.frg_denketa_question_llBuyNow);
        llBundleDiscount = frg.findViewById(R.id.frg_denketa_question_llBundleDiscount);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        img = frg.findViewById(R.id.img);
//        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
//        rlCross.setOnClickListener(this);

        llSeeAnswer.setOnClickListener(this);
        llPaynow.setOnClickListener(this);
        llBundleDiscount.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity) getActivity()).onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.frg_denketa_question_llSeeAnswer:
                if (!isMoreDanetka && !isInvestigator)
                    openDialog();
                else
                    navToDenketaAnswerFragment();
                break;

            case R.id.frg_denketa_question_llBundleDiscount:
                navToBundleDiscountFragment(danetka_danetkaID);

                break;
            case R.id.frg_denketa_question_llBuyNow:
                navToPaymentDetailFragment(danetka_danetkaID);
                break;

            case R.id.rl_popup_parent:
                dismissProgDialog();
                navToDenketaAnswerFragment(danetka_name.toUpperCase(),danetka_Image,danetka_danetkaID);
                break;

        }
    }
    private void navToDenketaAnswerFragment(String name,String danetka_Image,String danetka_danetkaID) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DenketaAnswerFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putString("key_danetka_danetka_image", danetka_Image);
        bundle.putString("key_danetka_danetkaID", danetka_danetkaID);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navToDenketaAnswerFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new GameResultsFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToBundleDiscountFragment(String danetka_danetkaID) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();

        bundle.putString("key_danetka_danetkaID", danetka_danetkaID);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPaymentDetailFragment(String danetka_danetkaID) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentDetailFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();

        bundle.putString("key_danetka_danetkaID", danetka_danetkaID);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.hide(this);
        ft.commit();
    }
    Dialog progressDialog = null; // Context, this, etc.
    public void openDialog() {


        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_master);
        rlMaster = progressDialog.findViewById(R.id.rl_popup_parent);
        rlMaster.setOnClickListener(this);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
