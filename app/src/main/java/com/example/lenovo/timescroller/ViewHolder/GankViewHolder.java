package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.timescroller.Activity.GankActivity;
import com.example.lenovo.timescroller.Activity.ImageActivity;
import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;
import com.example.lenovo.timescroller.View.RatioImageView;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class GankViewHolder extends BaseRecyclerViewHolder{

    RatioImageView imageView;
    TextView textView;
    Context mContext;

    public GankViewHolder(View itemView , Context context) {
        super(itemView,context);
        this.mContext = context;
        imageView = (RatioImageView) itemView.findViewById(R.id.iv_meizhi);
        textView = (TextView) itemView.findViewById(R.id.tv_title);
    }

    public void setData(Object object) {
        imageView.setOriginalSize(50,50);
        final MeiZhi.ResultsBean bean = (MeiZhi.ResultsBean) object;
        ImageLoaderUtil.loadImage(context.getApplicationContext(),bean.getUrl(),imageView);
        textView.setText(bean.getDesc());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(0,bean);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(1,bean);
            }
        });
    }

    @Override
    protected void onItemClick(int index, Object object) {
        super.onItemClick(index, object);
        MeiZhi.ResultsBean bean = (MeiZhi.ResultsBean) object;
        if (index==0)
            GankActivity.startGankActivity(mContext,bean.getPublishedAt());
        if (index==1){
            //添加系统原生Activity跳转动画
            Intent intent = ImageActivity.startImageActivity(mContext,bean.getUrl(),bean.getDesc());
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity)mContext,imageView,ImageActivity.PICTURE);
            ActivityCompat.startActivity((MainActivity)mContext,intent,compat.toBundle());
        }
        //BaseActivity.startActivity(mContext,ImageActivity.class,((MeiZhi.ResultsBean)object).getUrl());
    }
}
