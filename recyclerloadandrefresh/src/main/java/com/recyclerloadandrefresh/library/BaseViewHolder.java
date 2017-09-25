package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class BaseViewHolder {
    private SparseArray<View> mArrayViews;
    private static View mRoot;

    public BaseViewHolder(Context context, ViewGroup parent,int layoutid) {
        this.mArrayViews = new SparseArray<>();
        mRoot = LayoutInflater.from(context).inflate(layoutid,parent,false);
        mRoot.setTag(this);
    }

    private  <T extends View> T getView(int resid) {
        View view = mArrayViews.get(resid);
        if(view == null){
            view = mRoot.findViewById(resid);
            mArrayViews.put(resid,view);
        }
        return (T)view;
    }

    public static  BaseViewHolder get(Context context,View view,ViewGroup parent,int layoutid){
        if(view == null){
            return new BaseViewHolder(context,parent,layoutid);
        }
        return  (BaseViewHolder) view.getTag();
    }

    public View getmRoot(){
        return mRoot;
    }


    public BaseViewHolder setClickEnabled(int resid, boolean enabled) {
        getView(resid).setEnabled(enabled);
        return this;
    }

    public BaseViewHolder setClickEnabled(int[] resids, boolean enabled) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setEnabled(enabled);
        }
        return this;
    }

    public BaseViewHolder setOnClickListener(int resid, View.OnClickListener onClickListener) {
        getView(resid).setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewHolder setOnClickListener(int[] resids, View.OnClickListener onClickListener) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setOnClickListener(onClickListener);
        }
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int resid, View.OnLongClickListener longClickListener) {
        getView(resid).setOnLongClickListener(longClickListener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int[] resids, View.OnLongClickListener longClickListener) {
        for (int i = 0; i < resids.length; i++) {
            getView(resids[i]).setOnLongClickListener(longClickListener);
        }
        return this;
    }


    public BaseViewHolder  setText(int resid,CharSequence content){
        TextView view = getView(resid);
        view.setText(content);
        return  this;
    }

    public BaseViewHolder  setText(int resid,CharSequence content,android.widget.TextView.BufferType type){
        TextView view = getView(resid);
        view.setText(content,type);
        return  this;
    }

    public BaseViewHolder setText(int resid,int resourcId){
        TextView view = getView(resid);
        view.setText(resourcId);
        return this;
    }

    public BaseViewHolder setTextColor(int resid,int color){
        TextView view = getView(resid);
        view.setTextColor(color);
        return this;
    }


    public BaseViewHolder setViewVisibility(int resid,int visibility){
        getView(resid).setVisibility(visibility);
        return  this;
    }

    public int getViewVisibility(int resid){
        return getView(resid).getVisibility();
    }


    public BaseViewHolder setImagetSrc(int resid,int imgsrcid){
        ImageView view =  getView(resid);
        view.setImageResource(imgsrcid);
        return  this;
    }

    public BaseViewHolder setImageLevel(int resid,int level){
        ImageView view =  getView(resid);
        view.setImageLevel(level);
        return this;
    }

    public BaseViewHolder setBackgroundResource(int resid,int imgsrcid){
        getView(resid).setBackgroundResource(imgsrcid);
        return this;
    }

    public BaseViewHolder setPadding(int resid,int left, int top, int right, int bottom){
        getView(resid).setPadding(left,top,right,bottom);
        return this;
    }

    public BaseViewHolder setImageByUrl(int resid,BaseViewHolder.HolderImageLoader loader){
        ImageView view =  getView(resid);
        loader.displayImage(view.getContext(),view,loader.getImagepath());
        return this;
    }

    public Drawable getImageDrawable(int resid){
        ImageView  view =  getView(resid);
        return view.getDrawable();
    }

    public BaseViewHolder setLayoutParams(int resid,ViewGroup.LayoutParams params){
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
        mRoot.setOnClickListener(listener);
    }

    public void setItemOnLongClickListener(View.OnLongClickListener listener){
        mRoot.setOnLongClickListener(listener);
    }

    public  void  setItemOnTouchListener(View.OnTouchListener listener){
        mRoot.setOnTouchListener(listener);
    }


    public BaseViewHolder setItemLayoutParams(ViewGroup.LayoutParams params){
        mRoot.setLayoutParams(params);
        return this;
    }

    public BaseViewHolder setItemLayoutParams(int width,int height){
        ViewGroup.LayoutParams params = mRoot.getLayoutParams();
        params.height = height;
        params.width = width;
        mRoot.setLayoutParams(params);
        return this;
    }


}
