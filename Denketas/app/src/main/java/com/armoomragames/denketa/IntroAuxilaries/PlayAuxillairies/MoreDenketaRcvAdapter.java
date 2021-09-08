package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class MoreDenketaRcvAdapter extends RecyclerView.Adapter<MoreDenketaRcvAdapter.ViewHolder> {


    private final ArrayList<DModel_MyDenketa> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;



    public MoreDenketaRcvAdapter(Context mContext, ArrayList<DModel_MyDenketa> mData,
                                 IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_more_denketa, null);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.txvName.setText((position+1) + ". "  +mData.get(position).getStrName()+" ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A,position );
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvName, txvToFund;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvName = itemView.findViewById(R.id.lay_item_my_denekta_txvName);
//            txvToFund = itemView.findViewById(R.id.lay_item_my_denekta_txvName);


        }
    }

}
