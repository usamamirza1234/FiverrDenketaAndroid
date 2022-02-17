package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.MoreDenketaFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.MyDenketaFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PlayViewPagerAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PlayMianFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    ViewPager viewPager;
    TabLayout tabLayout;
    IBadgeUpdateListener mBadgeUpdateListener;
    private ArrayList<String> listTitle;
    PlayViewPagerAdapter playViewPagerAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_play_main, container, false);


        init();
        bindViews(frg);

        tabLayout.setVisibility(View.VISIBLE);

        listTitle = new ArrayList<>();
        listTitle.add(getString(R.string.my_denketa));
        listTitle.add(getString(R.string.more_denketa));
        listTitle.add(getString(R.string.make_deneketa));
        displayFragments();


        return frg;
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN);

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


    private void displayFragments() {
        viewPager.setOffscreenPageLimit(3);
        playViewPagerAdapter=       new PlayViewPagerAdapter(getActivity(), getChildFragmentManager(), listTitle);
        viewPager.setAdapter(playViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 1) {
//                    MyDenketaFragment.newInstance(position);
//                }
//                else {
//                    rlRate.setVisibility(View.VISIBLE);
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        playViewPagerAdapter.notifyDataSetChanged();
    }


    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        rlToolbar.setVisibility(View.VISIBLE);
        rlBack.setVisibility(View.GONE);
        rlCross.setVisibility(View.VISIBLE);
        viewPager = frg.findViewById(R.id.frag_review_view_pgr);
        tabLayout = frg.findViewById(R.id.frag_review_tab_reviewTab);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;

        }
    }




}
