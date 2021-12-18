package com.armoomragames.denketa.IntroAuxilaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModelResults;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private static View lastClicked = null;
    private ArrayList<DModelResults> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;
    public ResultsAdapter(Context mContext, ArrayList<DModelResults> mData,
                          IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;
        this.iAdapterCallback = iAdapterCallback;
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_results_item, null);
        return new ResultsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultsAdapter.ViewHolder holder, final int position) {

        holder.txv_master.setText(mData.get(position).getMaster());
        holder.txv_invest.setText(mData.get(position).getInvestigator());
        holder.itemView.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
            if (holder.rlLowExtended.getVisibility() == View.GONE)
                holder.rlLowExtended.setVisibility(View.VISIBLE);
            else holder.rlLowExtended.setVisibility(View.GONE);
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
        TextView txv_master;
        TextView txv_invest;
        LinearLayout rlLowExtended;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_master = itemView.findViewById(R.id.lay_results_txv_master);
            txv_invest = itemView.findViewById(R.id.lay_results_txv_invest);
            rlLowExtended = itemView.findViewById(R.id.lay_results_ll_extended);
        }
    }

}
