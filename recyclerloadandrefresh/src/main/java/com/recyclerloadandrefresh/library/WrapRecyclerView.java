package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class WrapRecyclerView extends RecyclerView {
    private wrapRecyclerAdapter mWrapRecyclerAdapter;
    private  RecyclerView.Adapter  mAdapter;


    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(mAdapter != null){
            mAdapter.unregisterAdapterDataObserver(mObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;
        if(adapter instanceof wrapRecyclerAdapter){
            mWrapRecyclerAdapter = (wrapRecyclerAdapter)adapter;
        }else {
            mWrapRecyclerAdapter = new wrapRecyclerAdapter(adapter);
        }
        super.setAdapter(mWrapRecyclerAdapter);

        mAdapter.registerAdapterDataObserver(mObserver);

        mWrapRecyclerAdapter.adjustSpanSize(this);
    }


    public void addHeaderView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    public void removeHeaderView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.removeHeadView(view);
        }
    }


    public void addFootView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.addFootView(view);
        }
    }

    public void removeFootView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.removeFootView(view);
        }
    }

    private  AdapterDataObserver mObserver = new AdapterDataObserver(){
        @Override
        public void onChanged() {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyItemChanged(positionStart,payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if(mAdapter == null) return;
            if( mAdapter != mWrapRecyclerAdapter){
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition,toPosition);
            }
        }
    };
}
