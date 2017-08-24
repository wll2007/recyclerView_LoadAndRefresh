package com.recyclerloadandrefresh.library;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public  abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mLayoutId ;
    private List<T> mdatas;
    private MultiTypeSupport<T> multiTypeSupport;  //支持多种布局

    public CommonRecyclerAdapter(Context mContext, int mLayoutId, List<T> mdatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mdatas = mdatas;
    }

    public CommonRecyclerAdapter(Context mContext, MultiTypeSupport<T> support, List<T> mdatas) {
        this(mContext,-1,mdatas);
        this.multiTypeSupport = support;
    }

    @Override
    public int getItemViewType(int position) {
        if(multiTypeSupport!=null){
            return multiTypeSupport.getLayoutId(mdatas.get(position),position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(multiTypeSupport!=null){
            mLayoutId = viewType;
        }
        View rootview = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mItemClickListener != null ){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.OnItemClick(mdatas.get(position),position);
                }
            });
        }

        if(mLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mLongClickListener.OnLongClick(mdatas.get(position),position);
                }
            });
        }


        //瀑布流随机生成高度
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            Random random = new Random(System.currentTimeMillis());
            layoutParams.height = random.nextInt(1000);
            holder.itemView.setLayoutParams(layoutParams);
        }

        convert(holder,mdatas.get(position),position);
    }

    public abstract void convert(ViewHolder holder, T t,int position) ;

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    private OnItemClickListener mItemClickListener;
    private OnLongClickListener mLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }


    public void setOnItemLongClickListener(OnLongClickListener listener){
        mLongClickListener = listener;
    }
}
