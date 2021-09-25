package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.armoomragames.denketa.IntroAuxilaries.DModelDictionary;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class FaqListAdapter extends BaseAdapter {

    private static View lastClicked = null;
    private final IAdapterCallback iAdapterCallback;
    private final Context mContext;
    private final ArrayList<DModelDictionary> mData;
    private final LayoutInflater mInflater;
    ViewHolder viewHolder;


    public FaqListAdapter(Context _mContext, IAdapterCallback iAdapterCallback, ArrayList<DModelDictionary> _mData) {
        this.iAdapterCallback = iAdapterCallback;
        this.mContext = _mContext;
        this.mData = _mData;
        mInflater = LayoutInflater.from(_mContext);


    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lay_faq_item, null);
            viewHolder = new ViewHolder();

            viewHolder.rlParent = convertView.findViewById(R.id.lay_dictionary_ll_parrent);
            viewHolder.rlBottom = convertView.findViewById(R.id.lay_dictionary_rlBottom);
            viewHolder.imvExtended = convertView.findViewById(R.id.lay_dictionary_ll_extended);
            viewHolder.txv_word = convertView.findViewById(R.id.lay_dictionary_txv_word);
//            viewHolder.txv_meaning = convertView.findViewById(R.id.lay_dictionary_txv_meaning);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.txv_word.setText(mData.get(position).getWord());
//        viewHolder.txv_meaning.setText(mData.get(position).getMeaning());

        viewHolder.imvExtended.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
            View parentRow = (View) v.getParent();
            ViewHolder viewHolder = (ViewHolder) parentRow.getTag();
//
//            if (viewHolder.rlBottom.getVisibility() != View.VISIBLE) {
//                //clear last clicked image
//                if (lastClicked != null) {
//
//                    ViewHolder lastHolder = (ViewHolder) lastClicked.getTag();
//                    lastHolder.rlBottom.setVisibility(View.GONE);
//                }
//                viewHolder.rlBottom.setVisibility(View.VISIBLE);
//                lastClicked = parentRow;
//            }
        });


        return convertView;
    }


    public static class ViewHolder {

        RelativeLayout rlParent;
        RelativeLayout rlBottom;

        ImageView imvExtended;
        TextView txv_word;
//        TextView txv_meaning;


    }
}
