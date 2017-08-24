package ex.wangll.com.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recyclerloadandrefresh.library.RefreshRecyclerView;
import com.recyclerloadandrefresh.library.RefreshViewCreator;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class DefaultRefreshRecyclerViewCreator implements RefreshViewCreator {
    private  View mRefreshview ;  //头部刷新view
    private ImageView mImageview ;
    private TextView mTextView;
    private ProgressBar mImageLoading;
    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        mRefreshview = LayoutInflater.from(context).inflate(R.layout.refresh,parent,false);
        mImageview = (ImageView)mRefreshview.findViewById(R.id.image_icon);
        mTextView = (TextView)mRefreshview.findViewById(R.id.Txt_view);
        mImageLoading = (ProgressBar)mRefreshview.findViewById(R.id.image_loading);
     return mRefreshview;
    }


    /**
     * 主要为了设置一些动画效果等
     * @param currentRefreshHeight    当前下拉的高度
     * @param RefreshViewHeight    下拉View的高度
     * @param status           下拉时的状态
     */
    @Override
    public void onPull(int currentRefreshHeight, int RefreshViewHeight, int status) {
        switch (status){
            case RefreshRecyclerView.REFRESH_PULLDOWNING:
                mImageview.setImageResource(R.drawable.pullup_icon);
                mTextView.setText(mRefreshview.getResources().getString(R.string.up));
                break;
            case RefreshRecyclerView.REFRESH_LOOSEN:
                mTextView.setText(mRefreshview.getResources().getString(R.string.loosen));
                mImageview.setRotation(180);
                break;
        }

    }

    @Override
    public void onRefreshing() {
        mImageview.setRotation(0);
        mImageLoading.setVisibility(View.VISIBLE);
        mImageview.setVisibility(View.GONE);
        mTextView.setText(mRefreshview.getResources().getString(R.string.refreshing));
    }

    @Override
    public void onStopRefresh() {
        mImageLoading.setVisibility(View.GONE);
        mImageview.setVisibility(View.VISIBLE);
        mImageview.setImageResource(R.drawable.pullup_icon);
        mTextView.setText(mRefreshview.getResources().getString(R.string.up));
    }
}
