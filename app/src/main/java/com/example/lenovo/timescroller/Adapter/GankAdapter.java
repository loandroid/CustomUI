package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.GankViewHolder;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class GankAdapter extends BaseRecyclerViewAdapter<MeiZhi.ResultsBean> {

    Context mContext;

    public GankAdapter(Context context) {
       this.mContext = context;
    }

    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_project_item, parent, false);
        GankViewHolder holder = new GankViewHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankViewHolder viewHolder = (GankViewHolder) holder;
        viewHolder.setData(lists.get(position));
    }

}
