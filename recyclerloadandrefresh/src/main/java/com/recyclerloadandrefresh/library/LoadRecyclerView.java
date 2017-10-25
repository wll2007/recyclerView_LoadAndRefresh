package com.recyclerloadandrefresh.library;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
/**
 *  @作者 Administrator
 *  @日期 2017/8/21 0021
 *  @desc  上拉加载
 */
public class LoadRecyclerView extends RefreshRecyclerView implements View.OnClickListener {
    private String TAG = LoadRecyclerView.class.getName();
    private  LoadViewCreator mLoadViewCreator;  //上拉刷新辅助类
    private View mLoadView;           //上拉刷新View
    private int mLoadhViewHeight;      //头部刷新View的高度
    private int mDownY;                  //手指按下时的Y的位置
    private float mDragIndex = 0.35f;   // 手指拖拽的阻力指数
    private boolean mCurrentDragFlag = false;   //当前是否在拖动
    private int mCurrentRefreshState;      //当前刷新状态
    public final static int LOAD_NORMAL = 0x101;  //默认状态即正常状态
    public final static int LOAD_PULLUPNING = 0x102;  //上拉加载状态
    public final static int LOAD_LOOSEN = 0x103;  //松开加载状态(松开后会执行加载)
    public final static int LOAD_LOADING = 0x104;  //正在加载状态
    private onLoadListener mLoadListener;  //加载回调监听(回调到Activiy或者Fragment中去处理加载)
    public LoadRecyclerView(Context context) {
        super(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 添加上拉辅助类
     * @param creator
     */
    public void addLoadViewCreator(LoadViewCreator creator){
        mLoadViewCreator = creator;
        addLoadView();
    }

    /**
     * 添加上拉View
     */
    private void addLoadView() {
        RecyclerView.Adapter  adapter = getAdapter();
        if(adapter!=null&&mLoadViewCreator!=null){
            View view = mLoadViewCreator.getLoadView(this.getContext(),this);
            if(view !=  null){
                mLoadView = view;
                addFootView(mLoadView);
                mLoadView.setOnClickListener(this);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mLoadView!=null && mLoadhViewHeight<=0){
            mLoadhViewHeight = mLoadView.getMeasuredHeight();
        }
        Log.d(TAG,"changed: "+";mLoadhViewHeight: "+ mLoadhViewHeight);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        addLoadView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getY();
                Log.d(TAG, "mDownY:" + mDownY);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                //需要判断是否在顶端;是否正在刷新状态
                if(canScrollDown()|| mCurrentRefreshState==LOAD_LOADING){
                    return  super.onTouchEvent(e);
                }

                // 解决下拉刷新自动滚动问题
                if (mCurrentDragFlag) {
                    scrollToPosition(getAdapter().getItemCount()-1);
                }

                int distancY = (int)((e.getY()-mDownY)*mDragIndex);
                Log.d(TAG,"distancY:"+distancY);
                if(distancY<0){
                    setLoadViewMargin(-distancY);
                    updateLoadState(-distancY);
                    mCurrentDragFlag = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if(mCurrentDragFlag){
                    resetLoadView();
                }
                break;
        }
        return super.onTouchEvent(e);
    }


    /**
     * 重置上拉刷新View
     */
    private void resetLoadView() {
        if(mLoadView == null){
            return;
        }
        int currentMargin = ((MarginLayoutParams)mLoadView.getLayoutParams()).bottomMargin;
        int margin = 0;
        if(mCurrentRefreshState==LOAD_LOOSEN){
            mCurrentRefreshState = LOAD_LOADING;

            if(mLoadViewCreator != null){
                mLoadViewCreator.onLoading();
            }

            if(mLoadListener != null){
                mLoadListener.onLoad();
            }
        }

        ValueAnimator animator = ObjectAnimator.ofFloat(currentMargin,margin).setDuration(currentMargin-margin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentTopMargin = (float) valueAnimator.getAnimatedValue();
                setLoadViewMargin((int) currentTopMargin);
            }
        });
        animator.start();
        mCurrentDragFlag = false;
    }


    /**
     * 更新加载状态
     * @param distancY   滑动距离差
     */
    private void updateLoadState(int distancY) {
        if(distancY<=0){
            mCurrentRefreshState=LOAD_NORMAL;
        }else if(distancY<mLoadhViewHeight){
            mCurrentRefreshState=LOAD_PULLUPNING;
        }else {
            mCurrentRefreshState = LOAD_LOOSEN;
        }

        if(mLoadViewCreator != null){
            mLoadViewCreator.onPull(distancY,mLoadhViewHeight,mCurrentRefreshState);
        }
    }

    /**
     * 设置上拉加载View的topMargin布局
     * @param distancY
     */
    private void setLoadViewMargin(int distancY) {
        if(mLoadView == null){
            return;
        }
        MarginLayoutParams parmas= (MarginLayoutParams) mLoadView.getLayoutParams();
        if(distancY < 0){
            distancY = 0;
        }
        parmas.bottomMargin = distancY;
        mLoadView.setLayoutParams(parmas);
    }

    /**
     * 停止刷新   注意调用时机(如果放到回调监听onLoad处理完后调用则会有问题)
     */
    public void  onStopLoad(Object o){
        mCurrentRefreshState = LOAD_NORMAL;
        resetLoadView();
        if(mLoadViewCreator != null){
            mLoadViewCreator.onStopLoad(o);
        }
    }

    /**
     * 判断是否已经滑到最顶部了
     * 从swipeRefreshLayout中拷贝过来的
     * @return
     */
    private boolean canScrollDown(){
        return ViewCompat.canScrollVertically(this, 1);
    }

    public void setLoadListener(LoadRecyclerView.onLoadListener mloadListener) {
        this.mLoadListener = mloadListener;
    }

    @Override
    public void onClick(View view) {
        //设置点击上拉加载View执行加载
        if(mLoadListener != null){
            mLoadListener.onLoad();
        }

        if(mLoadViewCreator != null){
            mLoadViewCreator.onLoading();
        }
    }


    public interface onLoadListener{
        void onLoad();
    }


}
