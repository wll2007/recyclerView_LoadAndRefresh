package com.recyclerloadandrefresh.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/18 0018.
 */
/**
 *  @作者 Administrator
 *  @日期 2017/8/18 0018
 *  @desc 下拉刷新辅助类
 */
public interface RefreshViewCreator {

    //获取刷新头部view
    View getRefreshView(Context context, ViewGroup parent);

    /**
     *  正在下拉
     * @param currentRefreshHeight    当前下拉的高度
     * @param RefreshViewHeight    下拉View的高度
     * @param status           下拉时的状态
     */
    void onPull(int currentRefreshHeight, int RefreshViewHeight,int status);

    /**
     * 正在刷新
     */
    void onRefreshing();

    /**
     * 停止刷新
     */
    void onStopRefresh();
}
