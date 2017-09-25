package com.recyclerloadandrefresh.library;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

//下拉刷新
public class RefreshRecyclerView extends WrapRecyclerView {

    private String TAG = RefreshRecyclerView.class.getName();
    private  RefreshViewCreator mRefreshViewCreator;  //下拉刷新辅助类
    private View mRefreshView;           //下拉刷新View
    private int mRefreshViewHeight;      //头部刷新View的高度
    private int mDownY;                  //手指按下时的Y的位置
    private float mDragIndex = 0.35f;   // 手指拖拽的阻力指数
    private boolean mCurrentDragFlag = false;   //当前是否在拖动
    private int mCurrentRefreshState;      //当前刷新状态
    public final static int REFRESH_NORMAL = 0x001;  //默认状态即正常状态
    public final static int REFRESH_PULLDOWNING = 0x002;  //下拉刷新状态
    public final static int REFRESH_LOOSEN = 0x003;  //松开刷新状态(松开后会执行刷新)
    public final static int REFRESH_REFRESHING = 0x004;  //正在刷新状态
    public final static int REFRESH_COMPLETE = 0x005;

    private onRefreshListener mRefreshListener;  //刷新回调监听(回调到Activiy或者Fragment中去处理刷新加载)

    public RefreshRecyclerView(Context context) {
        super(context);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addRefreshViewCreator(RefreshViewCreator creator){
        mRefreshViewCreator = creator;
        addRefreshview();
    }

    private void addRefreshview() {
        RecyclerView.Adapter  adapter = getAdapter();
        if(adapter!=null&&mRefreshViewCreator!=null){
             View view = mRefreshViewCreator.getRefreshView(this.getContext(),this);
            if(view !=  null){
                mRefreshView = view;
                addHeaderView(mRefreshView);
            }
        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mRefreshView!=null && mRefreshViewHeight<=0){
            mRefreshViewHeight = mRefreshView.getMeasuredHeight();
            if(mRefreshViewHeight>0){
                //隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
                setRefreshViewMargin(1-mRefreshViewHeight);
            }
        }

    }

    @Override
    public void setAdapter(Adapter adapter) {
       super.setAdapter(adapter);
        addRefreshview();
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
                if(canScrollUp()|| mCurrentRefreshState==REFRESH_REFRESHING){
                    return  super.onTouchEvent(e);
                }

                // 解决下拉刷新自动滚动问题
                if (mCurrentDragFlag) {
                    scrollToPosition(0);
                }

                int distancY = (int)((e.getY()-mDownY)*mDragIndex);
                Log.d(TAG,"distancY:"+distancY);
                if(distancY>0){
                    distancY = distancY - mRefreshViewHeight;
                    setRefreshViewMargin(distancY);
                    updateRefreshState(distancY);
                    mCurrentDragFlag = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if(mCurrentDragFlag){
                    resetRefreshView();
                }
                break;
        }
       return super.onTouchEvent(e);
    }

    /**
     * 重置下拉刷新View
     */
    private void resetRefreshView() {

        if(mRefreshView == null){
            return;
        }
        int delay  = 0 ;
        int currentMargin = ((MarginLayoutParams)mRefreshView.getLayoutParams()).topMargin;
        int margin = 1 - mRefreshViewHeight;
        if(mCurrentRefreshState==REFRESH_LOOSEN){
            mCurrentRefreshState = REFRESH_REFRESHING;
            margin = 0;
            if(mRefreshViewCreator != null){
                mRefreshViewCreator.onRefreshing();
            }

            if(mRefreshListener != null){
                mRefreshListener.onRefresh();
            }
        }

        if(mCurrentRefreshState==REFRESH_COMPLETE){
            mCurrentRefreshState = REFRESH_NORMAL;
            delay  = 1000;
        }
        ValueAnimator animator = ObjectAnimator.ofFloat(currentMargin,margin).setDuration(currentMargin-margin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentTopMargin = (float) valueAnimator.getAnimatedValue();
                setRefreshViewMargin((int) currentTopMargin);
            }
        });
        animator.setStartDelay(delay);
        animator.start();
        mCurrentDragFlag = false;
    }

    /**
     * 更新刷新状态
     * @param distancY   滑动距离差
     */
    private void updateRefreshState(int distancY) {
        if(distancY<-mRefreshViewHeight){
            mCurrentRefreshState=REFRESH_NORMAL;
        }else if(distancY<0){
            mCurrentRefreshState=REFRESH_PULLDOWNING;
        }else {
            mCurrentRefreshState = REFRESH_LOOSEN;
        }

        if(mRefreshViewCreator != null){
            mRefreshViewCreator.onPull(distancY,mRefreshViewHeight,mCurrentRefreshState);
        }
    }

    /**
     * 设置下拉刷新View的topMargin布局
     * @param distancY
     */
    private void setRefreshViewMargin(int distancY) {

        if(mRefreshView == null){
            return;
        }
        if(distancY < 1- mRefreshViewHeight){
            distancY = 1 - mRefreshViewHeight;
        }
        MarginLayoutParams parmas= (MarginLayoutParams) mRefreshView.getLayoutParams();
        parmas.topMargin = distancY;
        mRefreshView.setLayoutParams(parmas);
    }

    /**
     * 停止刷新   注意调用时机(如果放到回调监听onRefresh处理完后调用则会有问题)
     */
    public void  onStopRefresh(Object o){
        mCurrentRefreshState = REFRESH_COMPLETE;
        resetRefreshView();
        if(mRefreshViewCreator != null){
            mRefreshViewCreator.onStopRefresh(o);
        }
    }

    /**
     * 判断是否已经滑到最顶部了
     * 从swipeRefreshLayout中拷贝过来的
     * @return
     */
    private boolean canScrollUp(){
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }


    public void setRefreshListener(onRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }


    public interface onRefreshListener{
        void onRefresh();
    }

}
