package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    public ViewHolder setClickEnabled(int resid, boolean enabled) {
        getView(resid).setEnabled(enabled);
        return this;
    }

    public ViewHolder setClickEnabled(int[] resids, boolean enabled) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setEnabled(enabled);
        }
        return this;
    }

    public ViewHolder setOnClickListener(int resid, View.OnClickListener onClickListener) {
        getView(resid).setOnClickListener(onClickListener);
        return this;
    }

    public ViewHolder setOnClickListener(int[] resids, View.OnClickListener onClickListener) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setOnClickListener(onClickListener);
        }
        return this;
    }

    public ViewHolder setOnLongClickListener(int resid, View.OnLongClickListener longClickListener) {
        getView(resid).setOnLongClickListener(longClickListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int[] resids, View.OnLongClickListener longClickListener) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setOnLongClickListener(longClickListener);
        }
        return this;
    }

    public ViewHolder  setText(int resid,CharSequence content){
        TextView view = getView(resid);
        view.setText(content);
        return  this;
    }

    public ViewHolder  setText(int resid,CharSequence content,android.widget.TextView.BufferType type){
        TextView view = getView(resid);
        view.setText(content,type);
        return  this;
    }

    public ViewHolder setText(int resid,int resourcId){
        TextView view = getView(resid);
        view.setText(resourcId);
        return this;
    }

    public ViewHolder setTextColor(int resid,int color){
        TextView view = getView(resid);
        view.setTextColor(color);
        return this;
    }

    public  <T extends View> T getView(int resid) {
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

    public int getViewVisibility(int resid){
        return getView(resid).getVisibility();
    }

    public ViewHolder setImagetSrc(int resid,int imgsrcid){
        ImageView view =  getView(resid);
        view.setImageResource(imgsrcid);
        return  this;
    }

    public ViewHolder setImageLevel(int resid,int level){
        ImageView view =  getView(resid);
        view.setImageLevel(level);
        return this;
    }

    public ViewHolder setBackgroundResource(int resid,int imgsrcid){
        getView(resid).setBackgroundResource(imgsrcid);
        return this;
    }

    public ViewHolder setPadding(int resid,int left, int top, int right, int bottom){
        getView(resid).setPadding(left,top,right,bottom);
        return this;
    }

    public ViewHolder setImageByUrl(int resid,HolderImageLoader loader){
        ImageView view =  getView(resid);
        loader.displayImage(view.getContext(),view,loader.getImagepath());
        return this;
    }

    public Drawable getImageDrawable(int resid){
        ImageView  view =  getView(resid);
        return view.getDrawable();
    }

    public ViewHolder setLayoutParams(int resid,ViewGroup.LayoutParams params){
        getView(resid).setLayoutParams(params);
        return this;
    }

    public ViewGroup.LayoutParams getLayoutParams(int resid){
        return getView(resid).getLayoutParams();
    }

    /*
    * 适用于第三方图片加载,例如imageloader,Glide
    * */
    public  static abstract class HolderImageLoader {
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

    public  void  setItemOnTouchListener(View.OnTouchListener listener){
        itemView.setOnTouchListener(listener);
    }

    public ViewHolder setTextTypeface(int resid, Typeface typeface){
        TextView view = getView(resid);
        view.setTypeface(typeface);
        return this;
    }


    public ViewHolder setTextTypeface(int resid, Typeface typeface,int style){
        TextView view = getView(resid);
        view.setTypeface(typeface,style);
        return this;
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
