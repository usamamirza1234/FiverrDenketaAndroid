package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;

public class MyDenketaLsvAdapter extends BaseAdapter {

    private IAdapterCallback iAdapterCallback;
    private LayoutInflater inflater;
    private ArrayList<DModel_MyDenketa> mData;
    private float cornerRadius;
    private Context context;

    public MyDenketaLsvAdapter(IAdapterCallback iAdapterCallback, Context context, ArrayList<DModel_MyDenketa> mData) {
        this.iAdapterCallback = iAdapterCallback;
        inflater = LayoutInflater.from(context);
        this.mData = mData;
        this.cornerRadius = dpToPx(context, 15);
        this.context = context;
    }
    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        final ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.lay_item_my_denketa, null);

            viewHolder = new ViewHolder();

            viewHolder.txvName  = convertView.findViewById(R.id.lay_item_my_denekta_txvName);
            viewHolder.imvResults = convertView.findViewById(R.id.lay_item_my_denekta_imvResults);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txvName.setText(mData.get(position).getStrName());
//        viewHolder.txvName.setText(mData.get(position).getName());


        return convertView;
    }

    class ViewHolder {
        TextView txvName;
        ImageView imvResults;
    }
}
