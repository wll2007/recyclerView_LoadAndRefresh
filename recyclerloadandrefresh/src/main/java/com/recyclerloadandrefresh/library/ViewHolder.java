package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View>  mArrayViews;
    private Object object;

    public ViewHolder(View itemView) {
        super(itemView);
        mArrayViews =new SparseArray<View>();
    }

   public ViewHolder setOnClickListener(int resid,View.OnClickListener onClickListener){
       getView(resid).setOnClickListener(onClickListener);
       return  this;
   }

   public ViewHolder setOnLongClickListener(int resid, View.OnLongClickListener longClickListener){
       getView(resid).setOnLongClickListener(longClickListener);
       return  this;
   }


    public ViewHolder  setText(int resid,CharSequence content){
        TextView view = getView(resid);
        view.setText(content);
        return  this;
    }

    public ViewHolder setText(int resid,int resourcId){
        TextView view = getView(resid);
        view.setText(resourcId);
        return this;
    }

    private  <T extends View> T getView(int resid) {
        View view = mArrayViews.get(resid);
        if(view == null){
            view = itemView.findViewById(resid);
            mArrayViews.put(resid,view);
        }
        return (T)view;
    }

    public ViewHolder setViewVisibility(int resid,int visibility){
        getView(resid).setVisibility(visibility);
        return  this;
    }


    public ViewHolder setImagetSrc(int resid,int imgsrcid){
        ImageView view =  getView(resid);
        view.setImageResource(imgsrcid);
        return  this;
    }


    public ViewHolder setImageByUrl(int resid,HolderImageLoader loader){
        ImageView view =  getView(resid);
        loader.displayImage(view.getContext(),view,loader.getImagepath());
        return this;
    }

    /*
    * 适用于第三方图片加载,例如imageloader,Glide
    * */
    private abstract class HolderImageLoader {
        private  String  imagepath;

        public HolderImageLoader(String imagepath) {
            this.imagepath = imagepath;
        }

        public String getImagepath() {
            return imagepath;
        }

        public  abstract void  displayImage(Context content , ImageView view ,String path);
    }

    public void  setItemOnClickListener(View.OnClickListener  listener){
        itemView.setOnClickListener(listener);
    }

    public void setItemOnLongClickListener(View.OnLongClickListener listener){
        itemView.setOnLongClickListener(listener);
    }

    public ViewHolder setAdapter(int resid, BaseAdapter adapter){
        GridView view = getView(resid);
        view.setAdapter(adapter);
        return this;
    }

    public ViewHolder setTag(Object o){
        object = o;
        return this;
    }

    public Object getTag(){
        return object;
    }
}
