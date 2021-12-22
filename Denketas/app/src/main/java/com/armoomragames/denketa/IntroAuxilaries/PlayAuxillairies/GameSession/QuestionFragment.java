package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentDetailFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Guest_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_INVESTIGATOR_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_User_Danektas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.JustifyTextView;
import com.bumptech.glide.Glide;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    LinearLayout llSeeAnswer;
    RelativeLayout rlMaster;
    LinearLayout llPaynow;
    LinearLayout llBundleDiscount;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    String danetka_Image;
    int position = 0;

    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    TextView txvDanetkaName;
    JustifyTextView txvQuestion;
    ImageView img;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_question, container, false);

        init();
        bindViews(frg);
        setData();


        return frg;
    }

    private void setData() {
        try {
            if (!isInvestigator) {
                if (!isMoreDanetka)
                {
                    if (AppConfig.getInstance().mUser.isLoggedIn())
                    {
                        txvDanetkaName.setText(Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getTitle() + "");
                        txvQuestion.setText(Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getQuestion() + "");
                        danetka_Image = "http://18.118.228.171:2000/images/" + Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getImage();
                        Glide.with(getContext()).load(danetka_Image).into(img);
                    }
                    else
                    {
                        txvDanetkaName.setText(Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getTitle() + "");
                        txvQuestion.setText(Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getQuestion() + "");
                        danetka_Image = "http://18.118.228.171:2000/images/" + Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getImage();
                        Glide.with(getContext()).load(danetka_Image).into(img);
                    }

                    llPaynow.setVisibility(View.GONE);
                    llBundleDiscount.setVisibility(View.GONE);
                    llSeeAnswer.setVisibility(View.VISIBLE);
                } else {

                    txvDanetkaName.setText(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getTitle() + "");
                    txvQuestion.setText(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getQuestion() + "");
                    danetka_Image = "http://18.118.228.171:2000/images/" + Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getImage();
                    Glide.with(getContext()).load(danetka_Image).into(img);

                    llPaynow.setVisibility(View.VISIBLE);
                    llBundleDiscount.setVisibility(View.VISIBLE);
                    llSeeAnswer.setVisibility(View.GONE);
                }
            } else {
                txvDanetkaName.setText(Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getTitle() + "");
                txvQuestion.setText(Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getQuestion() + "");
                danetka_Image = "http://18.118.228.171:2000/images/" + Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getImage();
                Glide.with(getContext()).load(danetka_Image).into(img);

                llPaynow.setVisibility(View.GONE);
                llBundleDiscount.setVisibility(View.GONE);
                llSeeAnswer.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

    }

    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);

        }
    }

    private void bindViews(View frg) {

        llSeeAnswer = frg.findViewById(R.id.frg_denketa_question_llSeeAnswer);
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        txvQuestion = frg.findViewById(R.id.txvQuestion);
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
                navToBundleDiscountFragment();


                break;
            case R.id.frg_denketa_question_llBuyNow:
                navToPaymentDetailFragment();
                break;

            case R.id.rl_popup_parent:
                dismissProgDialog();
                navToDenketaAnswerFragment();
                break;

        }
    }

    private void navToDenketaAnswerFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AnswerFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navToBundleDiscountFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();

        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);

        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPaymentDetailFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentDetailFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();
        bundle.putBoolean("key_is_coming_from_bundle", false);
        bundle.putString("key_danetka_danetkaID", "" + Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getId());
        bundle.putString("key_danetka_sub_total", "1");
        bundle.putString("key_danetka_total", "1.00");
        bundle.putString("key_danetka_number", "1");
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.hide(this);
        ft.commit();
    }

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
    Dialog progressDialog = null; // Context, this, etc.
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
