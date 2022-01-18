package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Guest_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_INVESTIGATOR_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_User_Danektas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.JustifyTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class AnswerFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlBack, rlCross;
    RecyclerView rcvRegilto;
    TextView txvDanetkaName;
    JustifyTextView txvDetail;
    IBadgeUpdateListener mBadgeUpdateListener;
    ImageView img;
    LinearLayout llLearnMore;
    AnwserRegiltoRCVAdapter anwserRegiltoRCVAdapter;
    String danetka_Image;
    int position = 0;
    Bundle bundle;
    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    String[] lstRegilto;
    String danetka_ID = "1";
    String formattedDate ="";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_answer, container, false);

        init();
        bindViews(frg);
        setData();

        return frg;
    }

    private void setData() {
        try {
            if (!isInvestigator) {
                if (!isMoreDanetka) {

                    if (AppConfig.getInstance().mUser.isLoggedIn()) {
                        danetka_ID = (Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getId()) + "";
                        txvDanetkaName.setText(Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getTitle());
                        txvDetail.setText(Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getAnswer() + "");
                        danetka_Image = "http://18.119.55.236:2000/images/" + Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getAnswerImage();
//                        Glide.with(getContext()).load(danetka_Image).into(img);
                        lstRegilto = (Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(position).getDanetkas().getHint().split("\\s*=\\s*"));
                        populatePopulationList();

                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_logo)
                                .error(R.drawable.ic_logo)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();

                        Glide.with(getContext())
                                .load(danetka_Image)
                                .apply(options)
                                .into(img);
                        
                    } else {

                        danetka_ID = (Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getId()) + "";
                        txvDanetkaName.setText(Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getTitle() + "");
                        txvDetail.setText(Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getAnswer() + "");
                        danetka_Image = "http://18.119.55.236:2000/images/" + Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getAnswerImage();
//                        Glide.with(getContext()).load(danetka_Image).into(img);
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_logo)
                                .error(R.drawable.ic_logo)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();

                        Glide.with(getContext())
                                .load(danetka_Image)
                                .apply(options)
                                .into(img);
                        lstRegilto = (Intro_WebHit_Get_Guest_Danektas.responseObject.getData().getListing().get(position).getHint().split("\\s*=\\s*"));
                        populatePopulationList();
                    }

                } else {
                    danetka_ID = (Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getId()) + "";
                    txvDanetkaName.setText(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getTitle());
                    txvDetail.setText(Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getAnswer() + "");
                    danetka_Image = "http://18.119.55.236:2000/images/" + Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getAnswerImage();
//                    Glide.with(getContext()).load(danetka_Image).into(img);
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH)
                            .dontAnimate()
                            .dontTransform();

                    Glide.with(getContext())
                            .load(danetka_Image)
                            .apply(options)
                            .into(img);
                    lstRegilto = (Intro_WebHit_Get_All_Danektas.responseObject.getData().getListing().get(position).getHint().split("\\s*=\\s*"));
                    populatePopulationList();
                }
            } else {
                danetka_ID = (Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getId()) + "";
                txvDanetkaName.setText(Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getTitle());
                txvDetail.setText(Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getAnswer() + "");
                danetka_Image = "http://18.119.55.236:2000/images/" + Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getAnswerImage();
//                Glide.with(getContext()).load(danetka_Image).into(img);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_logo)
                        .error(R.drawable.ic_logo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getContext())
                        .load(danetka_Image)
                        .apply(options)
                        .into(img);
                lstRegilto = (Intro_WebHit_Get_INVESTIGATOR_Danektas.responseObject.getData().getListing().get(position).getHint().split("\\s*=\\s*"));
                populatePopulationList();
            }

            Log.d("anser","image "+danetka_Image);

        } catch (Exception e) {

        }

    }

    //region init
    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

        }

    }

    void init()  {
        setToolbar();

        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);

        }

        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            formattedDate = df.format(c);
        }
        catch (Exception e){}


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    private void bindViews(View frg) {
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        txvDetail = frg.findViewById(R.id.detail);
        rcvRegilto = frg.findViewById(R.id.frg_make_rcv_regilto);


        img = frg.findViewById(R.id.img);
        llLearnMore = frg.findViewById(R.id.frg_denketa_answer_llLearnmore);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        llLearnMore.setOnClickListener(this);
    }

    //endregion
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:


                if (!isInvestigator)
                    navToRateAppFragment();
                else ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;

            case R.id.frg_denketa_answer_llLearnmore:
                navToLearnmoreFragment();
                break;
        }
    }


    public void navToLearnmoreFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LearnMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LearnMoreFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LearnMoreFragment);
        ft.hide(this);
        ft.commit();

    }


    public void navToRateAppFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RateAppFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_id", danetka_ID);
        bundle.putString("key_danetka_name", txvDanetkaName.getText().toString());
        bundle.putString("key_danetka_formattedDate", formattedDate);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RateAppFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RateAppFragment);
        ft.hide(this);
        ft.commit();

    }

    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (anwserRegiltoRCVAdapter == null) {

            anwserRegiltoRCVAdapter = new AnwserRegiltoRCVAdapter(getActivity(), lstRegilto, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            rcvRegilto.setLayoutManager(linearLayoutManager);
            rcvRegilto.setAdapter(anwserRegiltoRCVAdapter);

        } else {
            anwserRegiltoRCVAdapter.notifyDataSetChanged();
        }
    }
}
