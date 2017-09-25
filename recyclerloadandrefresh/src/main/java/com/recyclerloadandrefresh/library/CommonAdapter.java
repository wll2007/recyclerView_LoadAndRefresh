package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> mLists;
    private Context mContext;
    private int mLayoutid;

    public CommonAdapter(List<T> mLists,Context context,int laytouId) {
        this.mLists = mLists;
        mContext = context;
        mLayoutid = laytouId;
    }

    @Override
    public int getCount() {
        if(mLists!=null&&mLists.size()>0){
            return mLists.size();
        }
        return 0;
    }

    @Override
    public T getItem(int i) {
        if(mLists!=null&&mLists.size()>0){
            return mLists.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseViewHolder viewHolder = BaseViewHolder.get(mContext,view,viewGroup,mLayoutid);
        convert(viewHolder,getItem(i));
        return viewHolder.getmRoot();
    }

    public abstract void convert(BaseViewHolder holder,T data);
}
