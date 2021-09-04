package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ChallengeFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ExtraRulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.GamePlayFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.Utils.AppConstt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext = null;
    private List<String> listTitle; // header titles
    private String strOrderId, strOrderType;
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags;
    private float mRotationY;

    public PlayViewPagerAdapter(Context _context, FragmentManager mgr, List<String> _listTitle) {
        super(mgr);
        this.mContext = _context;
        this.listTitle = _listTitle;
        this.mFragmentManager = mgr;
        this.mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public int getCount() {
        return this.listTitle.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frg = null;

        switch (position) {
            case AppConstt.ViewPagerStatePlay.MY_DENKETA:
                frg = MyDenketaFragment.newInstance(position);
                break;
            case AppConstt.ViewPagerStatePlay.MORE_DENKETA:
                frg = MoreDenketaFragment.newInstance(position);
                break;

            case AppConstt.ViewPagerStatePlay.MAKE_MY_DENKETA:
                frg = MakeDenketaFragment.newInstance(position);
                break;


            default:
                frg = MyDenketaFragment.newInstance(position);
                break;
        }



//        if (AppConfig.getInstance().isComingFromAllReviews) {
//            switch (position) {
//                //Only review list fragment visible
//                case AppConstt.ViewPagerState.AllReviews:
//                    frg = AllReviewFragment.newInstance(position);
//                    break;
//
//
//
//
//
//                default:
//                    frg = AllReviewFragment.newInstance(position);
//                    break;
//            }
//        } else {
//
//        }

        return frg;
    }

    @Override
    public String getPageTitle(int position) {
        return (this.listTitle.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            Fragment currFrg = (Fragment) obj;
            mFragmentTags.put(position, currFrg.getTag());
        }


        return obj;
    }

    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);

        return tag == null ? null : mFragmentManager.findFragmentByTag(tag);
//        if (tag != null)
//            return mFragmentManager.findFragmentByTag(tag);
//        else
//            return null;


    }

}