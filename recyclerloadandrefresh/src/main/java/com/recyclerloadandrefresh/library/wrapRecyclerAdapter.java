package com.recyclerloadandrefresh.library;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class wrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String Tag = wrapRecyclerAdapter.class.getName();
    private SparseArray<View> mHeadViews;    //用来存头部
    private SparseArray<View> mFootViews;    //用来存尾部
    private RecyclerView.Adapter mAdapter;   //真正的adapter
    private  static int BASE_HEADER_POS = 1000000;
    private  static int BASE_FOOT_POS = 2000000;
    private  int mHeight;

    /*
    构造函数
    **/
    public wrapRecyclerAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        mHeadViews = new SparseArray<>();
        mFootViews = new SparseArray<>();
    }

    /*
    * 添加头部
    * */
    public void addHeaderView(View view) {
        int pos = mHeadViews.indexOfValue(view);
        if (pos < 0) {
            mHeadViews.put(BASE_HEADER_POS++, view);
        }
        notifyDataSetChanged();
    }


    /*
    * 添加尾部
    * */
    public void addFootView(View view) {
        int pos = mFootViews.indexOfValue(view);
        if (pos < 0) {
            mFootViews.put(BASE_FOOT_POS++, view);
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if(mHeadViews.indexOfKey(viewType)>=0){
            view  = mHeadViews.get(viewType);
            return createHeaderFootViewHolder(view);
        }

        if(mFootViews.indexOfKey(viewType)>=0){
            view  = mFootViews.get(viewType);
            return createHeaderFootViewHolder(view);
        }
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    /*
    * 创建头部或者尾部的ViewHolder
    * */
    private RecyclerView.ViewHolder createHeaderFootViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position<mHeadViews.size() || position >= mHeadViews.size()+mAdapter.getItemCount() ){
            //解决瀑布流布局头部和尾部占一整行的问题
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            mHeight = layoutParams.height;
            Log.d(Tag,""+layoutParams.height);
            if(layoutParams !=null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams  params = new StaggeredGridLayoutManager.LayoutParams(layoutParams.MATCH_PARENT, layoutParams.WRAP_CONTENT);
                params.setFullSpan(true);
                holder.itemView.setLayoutParams(params);
            }
            return;
        }

        mAdapter.onBindViewHolder(holder,position-mHeadViews.size());
    }

    @Override
    public int getItemCount() {
        return mHeadViews.size()+mAdapter.getItemCount()+mFootViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position<mHeadViews.size()){
            return mHeadViews.keyAt(position);
        }
        if(position >= mHeadViews.size()+mAdapter.getItemCount() ) {
            return  mFootViews.keyAt(position-mHeadViews.size()-mAdapter.getItemCount());
        }
        return mAdapter.getItemViewType(position-mHeadViews.size());
    }


    /*
    * 移除头部
    * */
    public void removeHeadView(View view){
        int pos = mHeadViews.indexOfValue(view);
        if (pos >= 0) {
            mHeadViews.removeAt(pos);
        }
        notifyDataSetChanged();
    }


    /*
    * 移除尾部
    * */
    public void removeFootView(View view){
        int pos = mFootViews.indexOfValue(view);
        if (pos >= 0) {
            mFootViews.removeAt(pos);
        }
        notifyDataSetChanged();
    }


    /**
     * 解决GridLayoutManager头部和底部各占一行的问题
     * @param view
     */
    public  void adjustSpanSize(RecyclerView view){
        if(view.getLayoutManager() instanceof GridLayoutManager){
            final GridLayoutManager  layoutManager = (GridLayoutManager) view.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position<mHeadViews.size() || position >= mHeadViews.size()+mAdapter.getItemCount()){
                        return layoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

}
